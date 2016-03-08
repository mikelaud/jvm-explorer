package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.AgentBiosFactory;
import com.blogspot.mikelaud.je.agent.bios.common.AgentBios;
import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.common.file_source.FileSource;
import com.blogspot.mikelaud.je.common.file_source.FileSourceFactory;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class LocalAgentLoaderImpl extends AgentLoaderImpl implements LocalAgentLoader {

	private final AgentBios AGENT_BIOS;

	@AssistedInject
	private LocalAgentLoaderImpl
	(	@Assisted("AgentHeadJar") FileSource aAgentHeadJar
	,	@Assisted("AgentBodyJar") FileSource aAgentBodyJar
	) {
		super(aAgentHeadJar, aAgentBodyJar);
		AGENT_BIOS = AgentBiosFactory.newAgentBios();
	}

	@AssistedInject
	private LocalAgentLoaderImpl
	(	FileSourceFactory aFileSourceFactory
	,	@Assisted("AgentHeadJar") Path aAgentHeadJar
	,	@Assisted("AgentBodyJar") Path aAgentBodyJar
	) {
		this
		(	aFileSourceFactory.newFileSource(aAgentHeadJar)
		,	aFileSourceFactory.newFileSource(aAgentBodyJar)
		);
	}

	@AssistedInject
	private LocalAgentLoaderImpl(AgentSource aAgentSource) {
		this(aAgentSource.getHead(), aAgentSource.getBody());
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
