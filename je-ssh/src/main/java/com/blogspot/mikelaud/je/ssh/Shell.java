package com.blogspot.mikelaud.je.ssh;

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

	public static void main(String[] arg) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession("root", "192.168.10.101", 22);
			session.setPassword("xxx");
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			System.out.println("exit status: "+ exec(session, "ls -l"));
			System.out.println("exit status: "+ exec(session, "ls"));
		    session.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
