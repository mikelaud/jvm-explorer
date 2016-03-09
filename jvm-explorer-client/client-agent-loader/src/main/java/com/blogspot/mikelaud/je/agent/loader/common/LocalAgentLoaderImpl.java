package com.blogspot.mikelaud.je.agent.loader.common;

import java.util.Objects;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.AgentBiosFactory;
import com.blogspot.mikelaud.je.agent.bios.common.AgentBios;
import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.ssh.domain.Status;
import com.google.inject.Inject;

public class LocalAgentLoaderImpl extends AgentLoaderImpl implements LocalAgentLoader {

	private final AgentBios AGENT_BIOS;

	@Inject
	private LocalAgentLoaderImpl(AgentSource aAgentSource) {
		super(aAgentSource);
		AGENT_BIOS = AgentBiosFactory.newAgentBios();
	}

	@Override public String getJvmId() { return AGENT_BIOS.getJvmId(); }
	@Override public String getJavaHome() { return AGENT_BIOS.getJavaHome(); }

	@Override
	public Stream<JvmIdentity> getJvmList() {
		return Stream.of(AGENT_BIOS.getJvmList().toArray(new JvmIdentity[0]));
	}

	@Override
	public boolean loadAgent() {
		return AGENT_BIOS.loadAgent
		(	getHead().getFilePath()
		,	getBody().getFilePath()
		);
	}

	@Override
	public Status loadAgentById(String aJvmId) {
		Objects.requireNonNull(aJvmId);
		return new Status(AGENT_BIOS.loadAgentById
		(	aJvmId
		,	getHead().getFilePath()
		,	getBody().getFilePath()
		), "done.");
	}

	@Override
	public Status loadAgentByName(String aJvmName) {
		Objects.requireNonNull(aJvmName);
		return new Status(AGENT_BIOS.loadAgentByName
		(	aJvmName
		,	getHead().getFilePath()
		,	getBody().getFilePath()
		), "done.");
	}

}
