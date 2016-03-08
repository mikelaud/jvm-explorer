package com.blogspot.mikelaud.je.agent.loader.common;

import java.util.Objects;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.AgentBiosFactory;
import com.blogspot.mikelaud.je.agent.bios.common.AgentBios;
import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.google.inject.Inject;

public class LocalAgentLoaderImpl extends AgentLoaderImpl implements LocalAgentLoader {

	private final AgentBios AGENT_BIOS;

	@Inject
	private LocalAgentLoaderImpl(AgentSource aAgentSource) {
		super(aAgentSource.getHead(), aAgentSource.getBody());
		AGENT_BIOS = AgentBiosFactory.newAgentBios();
	}

	@Override public String getJvmId() { return AGENT_BIOS.getJvmId(); }
	@Override public String getJavaHome() { return AGENT_BIOS.getJavaHome(); }
	@Override public Stream<JvmIdentity> getJvmList() { return Stream.of(AGENT_BIOS.getJvmList().toArray(new JvmIdentity[0])); }
	@Override public boolean loadAgent() { return AGENT_BIOS.loadAgent(getHeadJar().toString(), getBodyJar().toString()); }

	@Override
	public boolean loadAgentById(String aJvmId) {
		Objects.requireNonNull(aJvmId);
		return AGENT_BIOS.loadAgentById(aJvmId, getHeadJar().toString(), getBodyJar().toString());
	}

	@Override
	public boolean loadAgentByName(String aJvmName) {
		Objects.requireNonNull(aJvmName);
		return AGENT_BIOS.loadAgentByName(aJvmName, getHeadJar().toString(), getBodyJar().toString());
	}

}
