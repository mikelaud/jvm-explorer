package com.blogspot.mikelaud.je.agent.body;

import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.Objects;

public class Main implements Runnable {

	private final URL JAR_LOCATION;
	private final Instrumentation INSTRUMENTATION;

	static {
		log("Load agent main: " + Main.class + "@" + Main.class.getClassLoader());
	}

	private static void log(String aMessage) {
		System.out.println("[agent-body] " + aMessage);
	}

	private static void log(Throwable aThrowable) {
		log(aThrowable.toString());
		for (StackTraceElement element : aThrowable.getStackTrace()) {
			log("\t" + element.toString());
		}
	}

	private void exec() {
		// void
	}

	public Main(URL aJarLocation, Instrumentation aInstrumentation) {
		JAR_LOCATION = aJarLocation;
		INSTRUMENTATION = aInstrumentation;
		log("Init agent body: " + JAR_LOCATION);
		Objects.requireNonNull(aJarLocation);
		Objects.requireNonNull(aInstrumentation);
	}

	@Override
	public void run() {
		try {
			log("Exec agent body: " + JAR_LOCATION);
			exec();
		}
		catch (Throwable t) {
			log(t);
		}
		finally {
			log("done.");
		}
	}

}
