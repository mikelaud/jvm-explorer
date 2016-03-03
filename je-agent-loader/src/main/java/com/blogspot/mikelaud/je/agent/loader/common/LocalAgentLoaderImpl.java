package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.AgentBiosFactory;
import com.blogspot.mikelaud.je.agent.bios.common.AgentBios;
import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class LocalAgentLoaderImpl extends AgentLoaderImpl implements LocalAgentLoader {

	private final AgentBios AGENT_BIOS;

	@Inject
	private LocalAgentLoaderImpl
	(	AgentBiosFactory aAgentBiosFactory
	,	@Assisted("AgentHeadJar") Path aAgentHeadJar
	,	@Assisted("AgentBodyJar") Path aAgentBodyJar
	) {
		super(aAgentHeadJar, aAgentBodyJar);
		AGENT_BIOS = aAgentBiosFactory.newAgentBios();
	}

	@Override public String getJvmId() { return AGENT_BIOS.getJvmId(); }
	@Override public Stream<JvmIdentity> getJvmList() { return AGENT_BIOS.getJvmList(); }
	@Override public boolean loadAgent() { return AGENT_BIOS.loadAgent(getHeadJar(), getBodyJar()); }

	@Override
	public boolean loadAgentById(String aJvmId) {
		Objects.requireNonNull(aJvmId);
		return AGENT_BIOS.loadAgentById(aJvmId, getHeadJar(), getBodyJar());
	}

	@Override
	public boolean loadAgentByName(String aJvmName) {
		Objects.requireNonNull(aJvmName);
		return AGENT_BIOS.loadAgentByName(aJvmName, getHeadJar(), getBodyJar());
	}

}
