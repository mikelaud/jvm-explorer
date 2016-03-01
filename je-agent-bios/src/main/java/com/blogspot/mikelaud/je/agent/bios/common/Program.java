package com.blogspot.mikelaud.je.agent.bios.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.blogspot.mikelaud.je.agent.bios.AgentBiosFactory;
import com.blogspot.mikelaud.je.agent.bios.AgentBiosModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Program {

	private String[] mArguments;
	private AgentBios mAgentBios;

	private void printHelp() {
		Logger.out("Usage 1: agent-bios");
		Logger.out("         (to get JVM list on this host)");
		Logger.out("Usage 2: agent-bios --id <jvmId> <agentHeadJar> <agentBodyJar>");
		Logger.out("         (to load agent into JVM with id: <jvmId>)");
		Logger.out("Usage 3: agent-bios --name <jvmName> <agentHeadJar> <agentBodyJar>");
		Logger.out("         (to load agent into JVM with name: <jvmName>)");
	}

	private void printJvmList() {
		mAgentBios.getJvmList().forEach(jvm -> Logger.out(jvm.toString()));
	}

	private boolean loadAgent(String aOption, String aJvm, String aAgentHeadJar, String aAgentBodyJar) {
		Objects.requireNonNull(aOption);
		Objects.requireNonNull(aJvm);
		Objects.requireNonNull(aAgentHeadJar);
		Objects.requireNonNull(aAgentBodyJar);
		//
		Path headJarPath = Paths.get(aAgentHeadJar);
		Path bodyJarPath = Paths.get(aAgentBodyJar);
		switch (aOption) {
			case "--id":
				return mAgentBios.loadAgentById(aJvm, headJarPath, bodyJarPath);
			case "--name":
				return mAgentBios.loadAgentByName(aJvm, headJarPath, bodyJarPath);
			default:
				printHelp();
				return false;
		}

	}

	private int executeCommand() {
		switch (mArguments.length) {
			case 0:
				printJvmList();
				return ExitStatus.SUCCESS;
			case 4:
				boolean status = loadAgent(mArguments[0], mArguments[1], mArguments[2], mArguments[3]);
				return status ? ExitStatus.SUCCESS : ExitStatus.FAILURE;
			default:
				printHelp();
				return ExitStatus.FAILURE;
		}
	}

	public Program() {
		// void
	}

	public int execute(String[] aArguments) {
		int status;
		try {
			mArguments = Objects.requireNonNull(aArguments);
			Injector injector = Guice.createInjector(new AgentBiosModule());
			AgentBiosFactory biosFactory = injector.getInstance(AgentBiosFactory.class);
			mAgentBios = biosFactory.newAgentBios();
			status = executeCommand();
		}
		catch (Throwable t) {
			Logger.log(t);
			status = ExitStatus.FAILURE;
		}
		return status;
	}

}
