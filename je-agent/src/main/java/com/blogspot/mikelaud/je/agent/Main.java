package com.blogspot.mikelaud.je.agent;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.blogspot.mikelaud.je.agent.beans.Types;

public class Main implements Runnable {

	static {
		System.out.println(">>>>>>>>> Init Main.");
	}

	private final Instrumentation INSTRUMENTATION;

	private void registerMxBean() throws Exception {
		String id = "id_" + System.currentTimeMillis();
		ObjectName beanName = ObjectName.getInstance("JvmExplorer", id, "Agent");
		System.out.println("[agent] register MXBean: \"" + beanName + "\"");
		Types bean = new Types(INSTRUMENTATION, id);
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		beanServer.registerMBean(bean, beanName);
	}

	private void printHelp() {
		System.out.println("[agent] help: jconsole => Local Process => com.blogspot.mikelaud.je.main.Main => Connect => MBeans => JvmExplorer => Types => Operations => echo");
	}

	@Override
	public void run() {
		System.out.println("[agent] main: begin.");
		try {
			registerMxBean();
			printHelp();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("[agent] main: end.");
	}

	public Main(Instrumentation aInstrumentation) {
		INSTRUMENTATION = aInstrumentation;
	}

	public static void agentmain(String aArgs, Instrumentation aInstrumentation) {
		try {
			System.out.println("[agent] >>> load begin");
			System.out.println("[agent] arg: " + aArgs);
			//
			URL agentUrl = new URL(aArgs);
			ClassLoader newClassLoader = URLClassLoader.newInstance(new URL[] { agentUrl }, null);
			Thread.currentThread().setContextClassLoader(newClassLoader);
			//
			System.out.println("[agent] classloader: " + Thread.currentThread().getContextClassLoader());
			//Main m = new Main(aInstrumentation);
			Class<?> clazz = newClassLoader.loadClass("com.blogspot.mikelaud.je.agent.Main");
			Constructor<?> constructor = clazz.getConstructor(Instrumentation.class);
			Runnable instance = Runnable.class.cast(constructor.newInstance(aInstrumentation));
			instance.run();
			System.out.println("[agent] <<< load end: " + instance);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("[agent] !!! load fail");
		}
	}

}
