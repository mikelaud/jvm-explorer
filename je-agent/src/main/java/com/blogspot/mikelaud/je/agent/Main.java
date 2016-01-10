package com.blogspot.mikelaud.je.agent;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.blogspot.mikelaud.je.agent.beans.Types;

public class Main {

	private static void registerMxBean() throws Exception {
		ObjectName beanName = new ObjectName("JvmExplorer:type=" + Types.class.getSimpleName());
		System.out.println("[agent] register MXBean: \"" + beanName + "\"");
		Types bean = new Types();
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		beanServer.registerMBean(bean, beanName);
	}
	
	private static void printHelp() {
		System.out.println("[agent] help: jconsole => Local Process => com.blogspot.mikelaud.je.main.Main => Connect => MBeans => JvmExplorer => Types => Operations => echo");		
	}
	
	public static void agentmain(String aArgs, Instrumentation aInstrumentation) {
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
	
}
