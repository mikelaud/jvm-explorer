package com.blogspot.mikelaud.je.ssh.operations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Objects;

import com.blogspot.mikelaud.je.ssh.common.ExitStatus;
import com.blogspot.mikelaud.je.ssh.common.UnixConst;
import com.blogspot.mikelaud.je.ssh.common.UnixPath;
import com.blogspot.mikelaud.je.ssh.domain.FileIdentity;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class CopyToLocalOperation extends AbstractOperation {

	private final Path INPUT_FILE_REMOTE;
	private final Path INPUT_FILE_LOCAL;
	//
	private final UnixPath FILE_REMOTE;
	private final File FILE_LOCAL;
	//
	private final int COPY_BUFFER_SIZE;

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
				File localFile = resolveLocalFile(remoteIdentity);
				try (FileOutputStream localStream = new FileOutputStream(localFile)) {
					long toReadTotal = remoteIdentity.getSize();
					int toReadCount = 0;
					int readCount = 0;
					byte[] buffer = new byte[COPY_BUFFER_SIZE];
					while (true) {
						toReadCount = COPY_BUFFER_SIZE <= toReadTotal ? COPY_BUFFER_SIZE : (int) toReadTotal;
						if (toReadCount <= 0) break;
						//
						readCount = in.read(buffer, 0, toReadCount);
						if (readCount < 0) break; // error
						//
						localStream.write(buffer, 0, readCount);
						toReadTotal -= readCount;
					}
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

	public CopyToLocalOperation(Path aFileRemote, Path aFileLocal) {
		INPUT_FILE_REMOTE = Objects.requireNonNull(aFileRemote);
		INPUT_FILE_LOCAL = Objects.requireNonNull(aFileLocal);
		//
		FILE_REMOTE = new UnixPath(INPUT_FILE_REMOTE);
		FILE_LOCAL = INPUT_FILE_LOCAL.toFile();
		//
		COPY_BUFFER_SIZE = 1024;
	}

	public Path getFileRemote() {
		return INPUT_FILE_REMOTE;
	}

	public Path getFileLocal() {
		return INPUT_FILE_LOCAL;
	}

	@Override
	public String toString() {
		return String.format("scp %s@%s:%s %s", getUserName(), getHostName(), FILE_REMOTE.getFilePath(), FILE_LOCAL);
	}

}
