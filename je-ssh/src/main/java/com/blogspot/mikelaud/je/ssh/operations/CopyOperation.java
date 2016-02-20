package com.blogspot.mikelaud.je.ssh.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.BiConsumer;

import com.blogspot.mikelaud.je.ssh.common.OperationStatus;
import com.blogspot.mikelaud.je.ssh.common.UnixPath;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class CopyOperation extends AbstractOperation {

	private final Path INPUT_FILE_DESTINATION;
	private final Path INPUT_FILE_SOURCE;
	//
	private final UnixPath FILE_DESTINATION;
	private final File FILE_SOURCE;
	//
	private final int COPY_BUFFER_SIZE;

	private int checkAck(InputStream aInputStream) throws IOException {
		int rcode = aInputStream.read();
		// rcode may be:
		//		0  for success
		//		1  for error
		//		2  for fatal error
		//		-1
		StringBuilder charactes = new StringBuilder();
		int character = rcode;
		while (character != 0 && character != '\n') {
			character = aInputStream.read();
			charactes.append((char)character);
		}
		if (charactes.length() > 0) {
			System.out.print(charactes.toString());
		}
		return rcode;
	}
	
	private int connect(ChannelExec aChannel, InputStream aIn) throws Exception {
		aChannel.connect();
		return checkAck(aIn);
	}
	
	private int write(OutputStream aOut, InputStream aIn, BiConsumer<OutputStream, InputStream> aWriter) throws Exception {
		aWriter.accept(aOut, aIn);
		aOut.flush();
		return checkAck(aIn);
	}
	
	private void writeTime(OutputStream aOut, InputStream aIn) {
		try {
			// The access time should be sent here, but it is not accessible with JavaAPI
			long lastModified = (FILE_SOURCE.lastModified() / 1000);
			String dstTime = String.format("T%d 0 %d 0\n", lastModified, lastModified);
			aOut.write(dstTime.getBytes());
		}
		catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	private void writeIdentity(OutputStream aOut, InputStream aIn) {
		try {
			// send "C0644 filesize filename", where filename should not include '/'
			long dstSize = FILE_SOURCE.length();
			String dstIdentity = String.format("C0644 %d %s\n", dstSize, FILE_DESTINATION.getFileName());
			aOut.write(dstIdentity.getBytes());
		}
		catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	private void writeContent(OutputStream aOut, InputStream aIn) {
		try {
			// send a content of file
			try (FileInputStream fis = new FileInputStream(FILE_SOURCE)) {
				byte[] buffer = new byte[COPY_BUFFER_SIZE];
				while (true) {
					int count = fis.read(buffer, 0, buffer.length);
					if (count <= 0) break;
					aOut.write(buffer, 0, count);
				}
				// send '\0'
				buffer[0] = 0;
				aOut.write(buffer, 0, 1);
			}
		}
		catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Override
	protected int executeOperation(Session aSession) throws Exception {
		ChannelExec channel = newChannelExec(aSession);
		InputStream in = channel.getInputStream();
		String command = String.format("scp -p -t %s", FILE_DESTINATION.getFilePath());
		channel.setCommand(command);
		int rcode = OperationStatus.EXIT_FAILURE.getValue();
		while (true) {
			try (OutputStream out = channel.getOutputStream()) {
				if (hasError(rcode = connect(channel, in))) break;
				if (hasError(rcode = write(out, in, this::writeTime))) break;
				if (hasError(rcode = write(out, in, this::writeIdentity))) break;
				if (hasError(rcode = write(out, in, this::writeContent))) break;
			}
			break;
		}
		channel.disconnect();
		return rcode;
	}

	public CopyOperation(Path aFileDestination, Path aFileSource) {
		INPUT_FILE_DESTINATION = Objects.requireNonNull(aFileDestination);
		INPUT_FILE_SOURCE = Objects.requireNonNull(aFileSource);
		//
		FILE_DESTINATION = new UnixPath(aFileDestination);
		FILE_SOURCE = aFileSource.toFile();
		//
		COPY_BUFFER_SIZE = 1024;
	}

	public Path getFileDestination() {
		return INPUT_FILE_DESTINATION;
	}
	
	public Path getFileSource() {
		return INPUT_FILE_SOURCE;
	}
	
	@Override
	public String toString() {
		return String.format("scp %s user@unix:%s", FILE_SOURCE, FILE_DESTINATION.getFilePath());
	}

}
