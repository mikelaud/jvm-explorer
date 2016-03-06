package com.blogspot.mikelaud.je.agent.bios.common;

public class Logger {

	private Logger() {
		// void
	}

	public static void out(String aMessage) {
		System.out.println(aMessage);
	}

	public static void log(String aMessage) {
		out("[agent-bios] " + aMessage);
	}

	public static void log(Throwable aThrowable) {
		log(aThrowable.toString());
		for (StackTraceElement element : aThrowable.getStackTrace()) {
			log("\t" + element.toString());
		}
	}

}
