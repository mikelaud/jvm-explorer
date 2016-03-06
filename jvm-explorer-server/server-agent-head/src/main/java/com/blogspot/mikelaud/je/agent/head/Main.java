package com.blogspot.mikelaud.je.agent.head;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {

	static {
		log("Load head main: " + Main.class + "@" + Main.class.getClassLoader());
	}

	private static void log(String aMessage) {
		System.out.println("[agent-head] " + aMessage);
	}

	private static void log(Throwable aThrowable) {
		log(aThrowable.toString());
		for (StackTraceElement element : aThrowable.getStackTrace()) {
			log("\t" + element.toString());
		}
	}

	private static void loadAgentBody(String aArgs, Instrumentation aInstrumentation) throws Exception {
		URL jarLocation = new File(aArgs).toURI().toURL();
		ClassLoader classLoader = URLClassLoader.newInstance(new URL[] { jarLocation }, null);
		Thread.currentThread().setContextClassLoader(classLoader);
		//
		String className = "com.blogspot.mikelaud.je.agent.body.Main";
		Class<?> clazz = classLoader.loadClass(className);
		Constructor<?> classConstructor = clazz.getConstructor(URL.class, Instrumentation.class);
		Object classInstance = classConstructor.newInstance(jarLocation, aInstrumentation);
		//
		Runnable agentBody = Runnable.class.cast(classInstance);
		agentBody.run();
	}

	public static void agentmain(String aArgs, Instrumentation aInstrumentation) {
		try {
			log("Load agent body: " + aArgs);
			loadAgentBody(aArgs, aInstrumentation);
		}
		catch (Throwable t) {
			log(t);
		}
		finally {
			log("done.");
		}
	}

}
