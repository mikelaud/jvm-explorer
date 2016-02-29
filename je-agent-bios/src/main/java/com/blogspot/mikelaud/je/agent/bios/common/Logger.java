package com.blogspot.mikelaud.je.agent.bios.common;

public interface Logger {

	static void out(String aMessage) {
		System.out.println(aMessage);
	}

	static void log(String aMessage) {
		out("[agent-bios] " + aMessage);
	}

	static void log(Throwable aThrowable) {
		log(aThrowable.toString());
		for (StackTraceElement element : aThrowable.getStackTrace()) {
			log("\t" + element.toString());
		}
	}

}
