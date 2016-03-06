package com.blogspot.mikelaud.je.agent.body;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.net.URL;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.blogspot.mikelaud.je.agent.common.Utils;

public class Main implements Runnable {

	private final URL JAR_LOCATION;
	private final Instrumentation INSTRUMENTATION;

	static {
		log("Load body main: " + Main.class + "@" + Main.class.getClassLoader());
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

	private void registerMxBean() throws Exception {
		String id = "id_" + System.currentTimeMillis();
		ObjectName beanName = ObjectName.getInstance("JvmExplorer", id, "Agent");
		log("Register MXBean: \"" + beanName + "\"");
		TypesMXBeanImpl bean = new TypesMXBeanImpl(INSTRUMENTATION, id);
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		beanServer.registerMBean(bean, beanName);
	}

	private void printHelp() {
		log("Help: jconsole => Local Process => com.blogspot.mikelaud.je.main.Main => Connect => MBeans => JvmExplorer => Types => Operations => echo");
	}

	private void exec() throws Exception {
		registerMxBean();
		printHelp();
	}

	public Main(URL aJarLocation, Instrumentation aInstrumentation) {
		JAR_LOCATION = aJarLocation;
		INSTRUMENTATION = aInstrumentation;
		log("Init agent body: " + JAR_LOCATION);
		Utils.requireNonNull(aJarLocation);
		Utils.requireNonNull(aInstrumentation);
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
