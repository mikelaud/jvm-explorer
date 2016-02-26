package com.blogspot.mikelaud.je.agent;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.blogspot.mikelaud.je.agent.beans.Types;

public class Main implements Runnable {

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
			//
			Path userHome = Paths.get(System.getProperty("user.home"));
			Path agentPath = userHome.resolve(".m2/repository/com/blogspot/mikelaud/je/je-agent/1.0.0/je-agent-1.0.0-jar-with-dependencies.jar");
			URL agentUrl = agentPath.toUri().toURL();
			//
			ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
			ClassLoader newClassLoader = URLClassLoader.newInstance(new URL[] { agentUrl }, oldClassLoader);
			Thread.currentThread().setContextClassLoader(newClassLoader);
			//
			System.out.println("[agent] classloader: " + Thread.currentThread().getContextClassLoader());
			new Main(aInstrumentation).run();
			System.out.println("[agent] <<< load end");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("[agent] !!! load fail");
		}
	}

}
