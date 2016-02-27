package com.blogspot.mikelaud.je.agent.bios;

public class Main {

	static {
		log("Load bios main: " + Main.class + "@" + Main.class.getClassLoader());
	}

	private static void log(String aMessage) {
		System.out.println("[agent-bios] " + aMessage);
	}

	private static void log(Throwable aThrowable) {
		log(aThrowable.toString());
		for (StackTraceElement element : aThrowable.getStackTrace()) {
			log("\t" + element.toString());
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
