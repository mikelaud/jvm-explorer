package com.blogspot.mikelaud.je.ssh.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Objects;

import com.blogspot.mikelaud.je.ssh.common.OperationStatus;
import com.blogspot.mikelaud.je.ssh.common.UnixPath;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class CopyOperation extends AbstractOperation {

	private final UnixPath FILE_DESTINATION;
	private final Path FILE_SOURCE;

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

	@Override
	protected int executeOperation(Session aSession) throws Exception {
		String command = "scp -p -t " + FILE_DESTINATION.getFilePath();
		ChannelExec channel = newChannelExec(aSession);
		channel.setCommand(command);
		int rcode = OperationStatus.EXIT_SUCCESS.getValue();
		try (OutputStream out = channel.getOutputStream()) {
			InputStream in = channel.getInputStream();
			channel.connect();
			rcode = checkAck(in);
			if (0 != rcode) {
				channel.disconnect();
				return rcode;
			}
			//
			File srcFile = FILE_SOURCE.toFile();
			String dstTime = "T" + (srcFile.lastModified() / 1000) + " 0";
			// The access time should be sent here, but it is not accessible with JavaAPI
			dstTime += " " + (srcFile.lastModified() / 1000) + " 0" + "\n";
			out.write(dstTime.getBytes());
			out.flush();
			rcode = checkAck(in);
			if (0 != rcode) {
				channel.disconnect();
				return rcode;
			}
			//
			// send "C0644 filesize filename", where filename should not include '/'
			long dstSize = srcFile.length();
			String dstIdentity = "C0644 " + dstSize + " " + FILE_DESTINATION.getFileName() + "\n";
			out.write(dstIdentity.getBytes());
			out.flush();
			rcode = checkAck(in);
			if (0 != rcode) {
				channel.disconnect();
				return rcode;
			}
			//
			// send a content of file
			try (FileInputStream fis = new FileInputStream(srcFile)) {
				byte[] buffer = new byte[1024];
				while (true) {
					int count = fis.read(buffer, 0, buffer.length);
					if (count <= 0) break;
					out.write(buffer, 0, count);
				}
				fis.close();
				// send '\0'
				buffer[0] = 0;
				out.write(buffer, 0, 1);
				out.flush();
				rcode = checkAck(in);
				if (0 != rcode) {
					channel.disconnect();
					return rcode;
				}
				//
				out.close();
				channel.disconnect();
			}
		}
		return rcode;
	}

	public CopyOperation(Path aFileDestination, Path aFileSource) {
		FILE_DESTINATION = Objects.requireNonNull(new UnixPath(aFileDestination));
		FILE_SOURCE = Objects.requireNonNull(aFileSource);
	}

	@Override
	public String toString() {
		return "scp " + FILE_SOURCE + " user@unix:" + FILE_DESTINATION.getFilePath();
	}

}
