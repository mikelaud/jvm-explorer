package com.blogspot.mikelaud.je.ssh.operations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Objects;

import com.blogspot.mikelaud.je.ssh.common.ExitStatus;
import com.blogspot.mikelaud.je.ssh.common.FileIdentity;
import com.blogspot.mikelaud.je.ssh.common.UnixConst;
import com.blogspot.mikelaud.je.ssh.common.UnixPath;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class CopyToLocalOperation extends AbstractOperation {

	private final Path INPUT_FILE_DESTINATION;
	private final Path INPUT_FILE_SOURCE;
	//
	private final File FILE_DESTINATION;
	private final UnixPath FILE_SOURCE;
	//
	private final int COPY_BUFFER_SIZE;
	private final byte[] COPY_BUFFER;

	private FileIdentity readIdentity(InputStream aIn) throws IOException {
		String permissions = readString(aIn, ' '); // read file permissions '0644 '
		long size = readLong(aIn, ' '); // read file size
		String name = readString(aIn, UnixConst.NEW_LINE); // read file name
		return new FileIdentity(permissions, size, name);
	}

	private void writeZero(OutputStream aOut) {
		try { // send '\0'
			COPY_BUFFER[0] = 0;
			aOut.write(COPY_BUFFER, 0, 1);
			aOut.flush();
		}
		catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	@Override
	protected int executeOperation(Session aSession) throws Exception {
		ChannelExec channel = newChannelExec(aSession);
		String command = String.format("scp -f %s", FILE_SOURCE.getFilePath()); // exec 'scp -f rfile' remotely
		channel.setCommand(command);
		int status = ExitStatus.ABORT.get();
		try (OutputStream out = channel.getOutputStream(); InputStream in = channel.getInputStream()) { // get I/O streams for remote scp
			while (true) {
				channel.connect();
				writeZero(out);
				status = checkAck(in);
				if ('C' != status) break;
				//
				FileIdentity srcIdentity = readIdentity(in);
				writeZero(out);
				//
				try (FileOutputStream dstStream = new FileOutputStream(FILE_DESTINATION)) {
					long toReadTotal = srcIdentity.getSize();
					int toReadCount = 0;
					int readCount = 0;
					while (true) {
						toReadCount = COPY_BUFFER_SIZE <= toReadTotal ? COPY_BUFFER_SIZE : (int) toReadTotal;
						if (toReadCount <= 0) break;
						//
						readCount = in.read(COPY_BUFFER, 0, toReadCount);
						if (readCount < 0) break; // error
						//
						dstStream.write(COPY_BUFFER, 0, readCount);
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

	public CopyToLocalOperation(Path aFileDestination, Path aFileSource) {
		INPUT_FILE_DESTINATION = Objects.requireNonNull(aFileDestination);
		INPUT_FILE_SOURCE = Objects.requireNonNull(aFileSource);
		//
		FILE_DESTINATION = aFileDestination.toFile();
		FILE_SOURCE = new UnixPath(aFileSource);
		//
		COPY_BUFFER_SIZE = 1024;
		COPY_BUFFER = new byte[COPY_BUFFER_SIZE];
	}

	public Path getFileDestination() {
		return INPUT_FILE_DESTINATION;
	}

	public Path getFileSource() {
		return INPUT_FILE_SOURCE;
	}

	@Override
	public String toString() {
		return String.format("scp %s@%s:%s %s", getUserName(), getHostName(), FILE_SOURCE.getFilePath(), FILE_DESTINATION);
	}

}
