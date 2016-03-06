package com.blogspot.mikelaud.je.agent.bios.common;

import java.util.List;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;

public interface AgentBios {

	String getJavaHome();
	String getJvmId();
	List<JvmIdentity> getJvmList();
	//
	boolean loadAgent(String aAgentHeadJar, String aAgentBodyJar);
	boolean loadAgentById(String aJvmId, String aAgentHeadJar, String aAgentBodyJar);
	boolean loadAgentByName(String aJvmName, String aAgentHeadJar, String aAgentBodyJar);

}
