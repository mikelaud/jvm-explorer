package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.common.AgentBios;
import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class AgentLoaderImpl implements AgentLoader {

	private final AgentBios AGENT_BIOS;
	//
	private final Path AGENT_HEAD_JAR;
	private final Path AGENT_BODY_JAR;

	@Inject
	private AgentLoaderImpl
	(	AgentBios aAgentBios
	,	@Assisted Path aAgentHeadJar
	,	@Assisted Path aAgentBodyJar
	) {
		AGENT_BIOS = aAgentBios;
		//
		AGENT_HEAD_JAR = Objects.requireNonNull(aAgentHeadJar);
		AGENT_BODY_JAR = Objects.requireNonNull(aAgentBodyJar);
	}

	@Override
	public Path getHeadJar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path getBodyJar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<JvmIdentity> getJvmList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loadAgentById(String aJvmId) {
		return AGENT_BIOS.loadAgentById(aJvmId, AGENT_HEAD_JAR, AGENT_BODY_JAR);
	}

	@Override
	public boolean loadAgentByName(String aJvmName) {
		// TODO Auto-generated method stub
		return false;
	}


}
