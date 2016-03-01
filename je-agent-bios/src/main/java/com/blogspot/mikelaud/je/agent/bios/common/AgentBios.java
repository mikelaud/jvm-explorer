package com.blogspot.mikelaud.je.agent.bios.common;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;

public interface AgentBios {

	String getJvmId();
	Stream<JvmIdentity> getJvmList();
	//
	boolean loadAgent(Path aAgentHeadJar, Path aAgentBodyJar);
	boolean loadAgentById(String aJvmId, Path aAgentHeadJar, Path aAgentBodyJar);
	boolean loadAgentByName(String aJvmName, Path aAgentHeadJar, Path aAgentBodyJar);

}
