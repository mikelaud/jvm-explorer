package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;

public interface AgentLoader {

	Path getHeadJar();
	Path getBodyJar();
	//
	Stream<JvmIdentity> getJvmList();
	//
	boolean loadAgentById(String aJvmId);
	boolean loadAgentByName(String aJvmName);

}
