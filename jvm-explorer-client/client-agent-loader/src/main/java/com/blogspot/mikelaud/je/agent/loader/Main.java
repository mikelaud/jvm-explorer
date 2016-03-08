package com.blogspot.mikelaud.je.agent.loader;

import java.nio.file.Paths;
import java.util.Scanner;

import com.blogspot.mikelaud.je.agent.loader.common.AgentSource;
import com.blogspot.mikelaud.je.agent.loader.common.LocalAgentLoader;
import com.blogspot.mikelaud.je.common.CommonModule;
import com.blogspot.mikelaud.je.common.file_source.FileSource;
import com.blogspot.mikelaud.je.ssh.SshModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new CommonModule(), new SshModule(), new AgentLoaderModule());
		AgentSource as = injector.getInstance(AgentSource.class);
		FileSource fs = as.getBios();
		System.out.println("agent source: " + as);
		System.out.println("bios source : " + fs);
		System.out.println("bios content: " + fs.takeContent());
		//
		AgentLoaderFactory factory = injector.getInstance(AgentLoaderFactory.class);
		LocalAgentLoader localLoader = factory.newLocalLoader(Paths.get(""), Paths.get(""));
		localLoader.loadAgent();
		localLoader.loadAgent();
		//
		/*
		RemoteAgentLoader remoteLoader = factory.newRemoteLoader(agentHeadJar, agentBodyJar, agentBiosJar, "192.168.10.101");
		remoteLoader.login("root", "1q2w3e");
		remoteLoader.getJvmList().forEach(System.out::println);
		remoteLoader.loadAgentByName("com.blogspot.mikelaud.je.agent.bios.Main");
		remoteLoader.loadAgentByName("com.blogspot.mikelaud.je.agent.bios.Main");
		*/
		//
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Press \"ENTER\" to continue...");
			scanner.nextLine();
			//remoteLoader.logout();
		}
	}

}
