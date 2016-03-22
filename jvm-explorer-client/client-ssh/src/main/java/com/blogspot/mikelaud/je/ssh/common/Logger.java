package com.blogspot.mikelaud.je.ssh.common;

import org.slf4j.LoggerFactory;

public class Logger {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Logger.class);

	public static void out(String aMessage) {
		System.out.print(aMessage);
	}

	public static void info(String aMessage) {
		System.out.println("[ssh]: ".concat(aMessage));
		LOGGER.info(aMessage);
	}

	public static void error(String aMessage) {
		System.out.println("[ssh]: ERROR: ".concat(aMessage));
		LOGGER.error(aMessage);
	}

	public static void error(Exception aException) {
		aException.printStackTrace();
	}

}
