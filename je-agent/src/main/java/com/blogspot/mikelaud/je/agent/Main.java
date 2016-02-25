package com.blogspot.mikelaud.je.agent;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.blogspot.mikelaud.je.agent.beans.Types;

public class Main implements Runnable {

	private final Instrumentation INSTRUMENTATION;

	private void registerMxBean() throws Exception {
		ObjectName beanName = ObjectName.getInstance("JvmExplorer", "type", "Types");
		System.out.println("[agent] register MXBean: \"" + beanName + "\"");
		Types bean = new Types(INSTRUMENTATION);
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
		//new Main(aInstrumentation).run();
		System.out.println("Hello from agent !");
	}

}
