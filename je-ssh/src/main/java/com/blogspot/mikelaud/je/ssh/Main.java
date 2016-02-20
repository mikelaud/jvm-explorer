package com.blogspot.mikelaud.je.ssh;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.blogspot.mikelaud.je.ssh.operations.CopyOperation;
import com.blogspot.mikelaud.je.ssh.operations.ExecOperation;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Main {

	public static int exec(Session aSession, String aCommand) {
		return new ExecOperation(aCommand).execute(aSession);
	}

	public static int copy(Session aSession, Path aFileDestination, Path aFileSource) {
		return new CopyOperation(aFileDestination, aFileSource).execute(aSession);
	}

	public static void main(String[] arg) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession("root", "192.168.10.101", 22);
			session.setPassword("xxx");
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			//
			System.out.println("exit status: "+ exec(session, "ls -l"));
			System.out.println("exit status: "+ exec(session, "pwd"));
			//
			Path srcFilePath = Paths.get("C:/Windows/notepad.exe");
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
