package com.blogspot.mikelaud.je.ssh;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Shell {

	public static int exec(Session aSession, String aCommand) {
		int rcode = 0;
		try {
			ChannelExec channel = (ChannelExec)aSession.openChannel("exec");
			channel.setCommand(aCommand);
			channel.setInputStream(null);
			channel.setOutputStream(System.out, true);
			channel.setErrStream(System.out, true);
			channel.connect();
			while (!channel.isEOF()) {
				try { Thread.sleep(100); } catch (Exception e) { e.printStackTrace(); }
			}
			rcode = channel.getExitStatus();
			channel.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
			rcode = 1;
		}
		return rcode;
	}

	private static int checkAck(InputStream aInputStream) throws IOException {
		int rcode = aInputStream.read();
		// rcode may be:
		//		0  for success
		//		1  for error
		//		2  for fatal error
		//		-1
		StringBuffer buffer = new StringBuffer();
		int character = rcode;
		while (character != 0 && character != '\n') {
			character = aInputStream.read();
			buffer.append((char)character);
		}
		if (buffer.length() > 0) {
			System.out.print(buffer.toString());
		}
		return rcode;
	}

	public static int copy(Session aSession, Path aDstFile, Path aSrcFile) {
		int rcode = 0;
		try {
			UnixPath unixFile = new UnixPath(aDstFile);
			String command = "scp -p -t " + unixFile.getFilePath();
			ChannelExec channel = (ChannelExec)aSession.openChannel("exec");
			channel.setCommand(command);
			try (OutputStream out = channel.getOutputStream()) {
				InputStream in = channel.getInputStream();
				channel.connect();
				rcode = checkAck(in);
				if (0 != rcode) {
					channel.disconnect();
					return rcode;
				}
				//
				File srcFile = aSrcFile.toFile();
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
				String dstIdentity = "C0644 " + dstSize + " " + unixFile.getFileName() + "\n";
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
		}
		catch (Exception e) {
			e.printStackTrace();
			rcode = 3;
		}
		return rcode;
	}

	public static void main(String[] arg) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession("root", "192.168.10.101", 22);
			session.setPassword("xxx");
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			System.out.println("exit status: "+ exec(session, "ls -l"));
			System.out.println("exit status: "+ exec(session, "pwd"));
			//
			Path srcFilePath = Paths.get("C:", "Windows", "notepad.exe");
			System.out.println("exit status: "+ copy(session, Paths.get("notepad.exe"), srcFilePath));
			System.out.println("exit status: "+ copy(session, Paths.get("notepad2.exe"), srcFilePath));
			//
			System.out.println("exit status: "+ exec(session, "ls"));
			session.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
