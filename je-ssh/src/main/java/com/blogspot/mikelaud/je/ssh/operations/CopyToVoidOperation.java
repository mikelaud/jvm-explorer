package com.blogspot.mikelaud.je.ssh.operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.security.DigestOutputStream;
import java.util.Objects;

import com.blogspot.mikelaud.je.ssh.common.ExitStatus;
import com.blogspot.mikelaud.je.ssh.common.UnixConst;
import com.blogspot.mikelaud.je.ssh.common.UnixPath;
import com.blogspot.mikelaud.je.ssh.common.VoidOutputStream;
import com.blogspot.mikelaud.je.ssh.domain.Digest;
import com.blogspot.mikelaud.je.ssh.domain.FileIdentity;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class CopyToVoidOperation extends AbstractOperation {

	private final Path INPUT_FILE_REMOTE;
	private final Path INPUT_FILE_LOCAL;
	//
	protected final UnixPath FILE_REMOTE;
	protected final File FILE_LOCAL;
	//
	private Digest mDigest;

	private File resolveLocalFile(FileIdentity aFileIdentity) {
		File localFile = FILE_LOCAL;
		if (FILE_LOCAL.isDirectory()) {
			localFile = INPUT_FILE_LOCAL.resolve(aFileIdentity.getName()).toFile();
		}
		return localFile;
	}

	private FileIdentity readIdentity(InputStream aIn) throws IOException {
		String permissions = readString(aIn, ' '); // file permissions '0644 '
		long size = readLong(aIn, ' '); // file size
		String name = readString(aIn, UnixConst.NEW_LINE); // file name
		return new FileIdentity(permissions, size, name);
	}

	protected OutputStream newFileOutputStream(File aFile) throws FileNotFoundException {
		return new VoidOutputStream();
	}

	@Override
	protected int executeOperation(Session aSession) throws Exception {
		ChannelExec channel = newChannelExec(aSession);
		String command = String.format("scp -f %s", FILE_REMOTE.getFilePath());
		channel.setCommand(command);
		int status = ExitStatus.ABORT.get();
		try (OutputStream out = channel.getOutputStream(); InputStream in = channel.getInputStream()) { // get I/O streams for remote scp
			while (true) {
				channel.connect();
				writeZero(out);
				status = checkAck(in);
				if ('C' != status) break;
				//
				FileIdentity remoteIdentity = readIdentity(in);
				writeZero(out);
				//
				mDigest = new Digest();
				File localFile = resolveLocalFile(remoteIdentity);
				try (DigestOutputStream fos = new DigestOutputStream(newFileOutputStream(localFile), newMessageDigest())) {
					long toReadTotal = remoteIdentity.getSize();
					int toReadCount = 0;
					int readCount = 0;
					byte[] buffer = newCopyBuffer();
					final int bufferSize = buffer.length;
					while (true) {
						toReadCount = bufferSize <= toReadTotal ? bufferSize : (int) toReadTotal;
						if (toReadCount <= 0) break;
						//
						readCount = in.read(buffer, 0, toReadCount);
						if (readCount < 0) break; // error
						//
						fos.write(buffer, 0, readCount);
						toReadTotal -= readCount;
					}
					mDigest = new Digest(fos.getMessageDigest());
				}
				status = checkAck(in);
				if (hasError(status)) break;
				//
				writeZero(out);
				break;
			}
		}
		finally {
			channel.disconnect();
		}
		return status;
	}

	public CopyToVoidOperation(Path aFileRemote, Path aFileLocal) {
		INPUT_FILE_REMOTE = Objects.requireNonNull(aFileRemote);
		INPUT_FILE_LOCAL = Objects.requireNonNull(aFileLocal);
		//
		FILE_REMOTE = new UnixPath(INPUT_FILE_REMOTE);
		FILE_LOCAL = INPUT_FILE_LOCAL.toFile();
		//
		mDigest = new Digest();
	}

	public Path getFileRemote() {
		return INPUT_FILE_REMOTE;
	}

	public Path getFileLocal() {
		return INPUT_FILE_LOCAL;
	}

	public Digest getDigest() {
		return mDigest;
	}

	@Override
	public String toString() {
		return String.format("check: %s@%s:%s", getUserName(), getHostName(), FILE_REMOTE.getFilePath());
	}

}
