package com.blogspot.mikelaud.je.agent.loader.common;

import java.util.Objects;

import com.blogspot.mikelaud.je.common.file_source.FileSource;

public abstract class AgentLoaderImpl implements AgentLoader {

	private final FileSource AGENT_HEAD_JAR;
	private final FileSource AGENT_BODY_JAR;

	protected AgentLoaderImpl(FileSource aAgentHeadJar, FileSource aAgentBodyJar) {
		AGENT_HEAD_JAR = Objects.requireNonNull(aAgentHeadJar);
		AGENT_BODY_JAR = Objects.requireNonNull(aAgentBodyJar);
	}

	@Override public FileSource getHeadJar() { return AGENT_HEAD_JAR; }
	@Override public FileSource getBodyJar() { return AGENT_BODY_JAR; }

}
