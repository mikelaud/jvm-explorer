package com.blogspot.mikelaud.je.agent.loader.common;

import java.util.Objects;

import com.blogspot.mikelaud.je.common.file_source.FileSource;

public abstract class AgentLoaderImpl implements AgentLoader {

	private final AgentSource AGENT_SOURCE;

	protected AgentLoaderImpl(AgentSource aAgentSource) {
		AGENT_SOURCE = Objects.requireNonNull(aAgentSource);
	}

	@Override public AgentSource getAgentSource() { return AGENT_SOURCE; }

	@Override public FileSource getHead() { return AGENT_SOURCE.getHead(); }
	@Override public FileSource getBody() { return AGENT_SOURCE.getBody(); }
	@Override public FileSource getBios() { return AGENT_SOURCE.getBios(); }

}
