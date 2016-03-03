package com.blogspot.mikelaud.je.agent.loader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.blogspot.mikelaud.je.agent.bios.AgentBiosModule;
import com.blogspot.mikelaud.je.agent.loader.common.LocalAgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteAgentLoader;
import com.blogspot.mikelaud.je.ssh.SshModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

	public static void main(String[] args) {
		Path userHome = Paths.get(System.getProperty("user.home"));
		Path agentHeadJar = userHome.resolve(".m2/repository/com/blogspot/mikelaud/je/je-agent-head/1.0.0/je-agent-head-1.0.0-jar-with-dependencies.jar");
		Path agentBodyJar = userHome.resolve(".m2/repository/com/blogspot/mikelaud/je/je-agent-body/1.0.0/je-agent-body-1.0.0-jar-with-dependencies.jar");
		//
		Injector injector = Guice.createInjector(new AgentBiosModule(), new SshModule(), new AgentLoaderModule());
		AgentLoaderFactory factory = injector.getInstance(AgentLoaderFactory.class);
		//
		LocalAgentLoader localLoader = factory.newLocalLoader(agentHeadJar, agentBodyJar);
		localLoader.loadAgent();
		localLoader.loadAgent();
		//
		RemoteAgentLoader remoteLoader = factory.newRemoteLoader(agentHeadJar, agentBodyJar, "192.168.10.101");
		remoteLoader.login("root", "1q2w3e");
		remoteLoader.getJvmList().forEach(System.out::println);
		//
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Press \"ENTER\" to continue...");
			scanner.nextLine();
			remoteLoader.logout();
		}
	}

}
