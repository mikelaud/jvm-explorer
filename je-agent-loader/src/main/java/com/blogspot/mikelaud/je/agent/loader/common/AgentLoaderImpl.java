package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Path;
import java.util.Objects;

public abstract class AgentLoaderImpl implements AgentLoader {

	private final Path AGENT_HEAD_JAR;
	private final Path AGENT_BODY_JAR;

	protected AgentLoaderImpl(Path aAgentHeadJar, Path aAgentBodyJar) {
		AGENT_HEAD_JAR = Objects.requireNonNull(aAgentHeadJar);
		AGENT_BODY_JAR = Objects.requireNonNull(aAgentBodyJar);
	}

	@Override public Path getHeadJar() { return AGENT_HEAD_JAR; }
	@Override public Path getBodyJar() { return AGENT_BODY_JAR; }

}
