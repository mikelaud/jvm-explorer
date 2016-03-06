package com.blogspot.mikelaud.je.agent.bios;

import com.blogspot.mikelaud.je.agent.bios.common.AgentBios;
import com.blogspot.mikelaud.je.agent.bios.common.AgentBiosImpl;

public class AgentBiosFactory {

	private AgentBiosFactory() {
		// void
	}

	public static AgentBios newAgentBios() {
		return new AgentBiosImpl();
	}

}
