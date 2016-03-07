package com.blogspot.mikelaud.je.agent.loader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.blogspot.mikelaud.je.agent.loader.common.RemoteAgentLoader;
import com.blogspot.mikelaud.je.agent.loader.source.FileSource;
import com.blogspot.mikelaud.je.agent.loader.source.FileSourceFactory;
import com.blogspot.mikelaud.je.ssh.SshModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

	public static void main(String[] args) {
		Path userHome = Paths.get(System.getProperty("user.home"));
		Path agentBiosJar = userHome.resolve(".m2/repository/com/blogspot/mikelaud/je/server-agent-bios/1.0.0/server-agent-bios-1.0.0-jar-with-dependencies.jar");
		Path agentHeadJar = userHome.resolve(".m2/repository/com/blogspot/mikelaud/je/server-agent-head/1.0.0/server-agent-head-1.0.0-jar-with-dependencies.jar");
		Path agentBodyJar = userHome.resolve(".m2/repository/com/blogspot/mikelaud/je/server-agent-body/1.0.0/server-agent-body-1.0.0-jar-with-dependencies.jar");
		//
		Injector injector = Guice.createInjector(new SshModule(), new AgentLoaderModule());
		AgentLoaderFactory factory = injector.getInstance(AgentLoaderFactory.class);
		FileSourceFactory fsf = injector.getInstance(FileSourceFactory.class);
		FileSource fs = fsf.newFileSourceJar(Paths.get("main-1.0.0-jar-with-dependencies.jar"), "META-INF/resources/server-agent-head-1.0.0-jar-with-dependencies.jar");
		System.out.println("fs: " + fs);
		System.out.println("c: " + fs.takeContent());
		//
		/*
		LocalAgentLoader localLoader = factory.newLocalLoader(agentHeadJar, agentBodyJar);
		localLoader.loadAgent();
		localLoader.loadAgent();
		*/
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
