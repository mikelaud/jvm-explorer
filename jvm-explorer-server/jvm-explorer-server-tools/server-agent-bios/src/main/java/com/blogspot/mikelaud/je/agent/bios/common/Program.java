package com.blogspot.mikelaud.je.agent.bios.common;

import com.blogspot.mikelaud.je.agent.bios.AgentBiosFactory;
import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.agent.common.Utils;

public class Program {

	private final String[] ARGUMENTS;
	private final AgentBios AGENT_BIOS;

	private void printHelp() {
		Logger.out("Usage 1: agent-bios --help");
		Logger.out("         (show help)");
		Logger.out("Usage 2: agent-bios --home");
		Logger.out("         (show Java home directory)");
		Logger.out("Usage 3: agent-bios --list");
		Logger.out("         (show JVM list on this host)");
		Logger.out("Usage 4: agent-bios --id <jvmId> <agentHeadJar> <agentBodyJar>");
		Logger.out("         (load agent into JVM with id: <jvmId>)");
		Logger.out("Usage 5: agent-bios --name <jvmName> <agentHeadJar> <agentBodyJar>");
		Logger.out("         (load agent into JVM with name: <jvmName>)");
	}

	private void printJavaHome() {
		Logger.out(AGENT_BIOS.getJavaHome());
	}

	private void printJvmList() {
		for (JvmIdentity jvm : AGENT_BIOS.getJvmList()) {
			Logger.out(jvm.toString());
		}
	}

	private boolean isMatched(String aOption, String... aPatterns) {
		for (String pattern : aPatterns) {
			if (pattern.equals(aOption)) {
				return true;
			}
		}
		return false;
	}

	private boolean printInformation(String aOption) {
		Utils.requireNonNull(aOption);
		//
		for (;;) {
			if (isMatched(aOption, "--help", "-h")) {
				printHelp();
				break;
			}
			else if (isMatched(aOption, "--home", "-o")) {
				printJavaHome();
				break;
			}
			else if (isMatched(aOption, "--list", "-l")) {
				printJvmList();
				break;
			}
			printHelp();
			return false;
		}
		return true;
	}

	private boolean loadAgent(String aOption, String aJvm, String aAgentHeadJar, String aAgentBodyJar) {
		Utils.requireNonNull(aOption);
		Utils.requireNonNull(aJvm);
		Utils.requireNonNull(aAgentHeadJar);
		Utils.requireNonNull(aAgentBodyJar);
		//
		for (;;) {
			if (isMatched(aOption, "--id", "-i")) {
				return AGENT_BIOS.loadAgentById(aJvm, aAgentHeadJar, aAgentBodyJar);
			}
			else if (isMatched(aOption, "--name", "-n")) {
				return AGENT_BIOS.loadAgentByName(aJvm, aAgentHeadJar, aAgentBodyJar);
			}
			printHelp();
			return false;
		}
	}

	private boolean executeCommand() {
		switch (ARGUMENTS.length) {
			case 1:
				return printInformation(ARGUMENTS[0]);
			case 4:
				return loadAgent(ARGUMENTS[0], ARGUMENTS[1], ARGUMENTS[2], ARGUMENTS[3]);
			default:
				printHelp();
				return false;
		}
	}

	public Program(String[] aArguments) {
		ARGUMENTS = Utils.requireNonNull(aArguments);
		AGENT_BIOS = AgentBiosFactory.newAgentBios();
	}

	public int execute() {
		try {
			return ExitStatus.get(executeCommand());
		}
		catch (Throwable t) {
			Logger.log(t);
			return ExitStatus.FAILURE.get();
		}
	}

}
