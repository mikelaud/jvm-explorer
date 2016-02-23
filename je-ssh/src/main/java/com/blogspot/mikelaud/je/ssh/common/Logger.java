package com.blogspot.mikelaud.je.ssh.common;

public class Logger {

	public static void out(String aMessage) {
		System.out.print(aMessage);
	}

	public static void info(String aMessage) {
		System.out.println("[ssh]: ".concat(aMessage));
	}

	public static void error(String aMessage) {
		System.out.println("[ssh]: ERROR: ".concat(aMessage));
	}

	public static void error(Exception aException) {
		aException.printStackTrace();
	}

}
