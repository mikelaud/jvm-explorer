package com.blogspot.mikelaud.je.agent.bios.domain;

import java.nio.file.Path;
import java.util.Objects;

public class AgentIdentity {

	private final String JVM_ID;
	private final Path AGENT_HEAD_JAR;
	private final Path AGENT_BODY_JAR;

	public AgentIdentity(String aJvmId, Path aAgentHeadJar, Path aAgentBodyJar) {
		JVM_ID = Objects.requireNonNull(aJvmId);
		AGENT_HEAD_JAR = Objects.requireNonNull(aAgentHeadJar);
		AGENT_BODY_JAR = Objects.requireNonNull(aAgentBodyJar);
	}

	public AgentIdentity(JvmIdentity aJvmIdentity, Path aAgentHeadJar, Path aAgentBodyJar) {
		this(Objects.requireNonNull(aJvmIdentity).getId(), aAgentHeadJar, aAgentBodyJar);
	}

	public String getJvmId() { return JVM_ID; }
	public Path getAgentHeadJar() { return AGENT_HEAD_JAR; }
	public Path getAgentBodyJar() { return AGENT_BODY_JAR; }

	@Override
	public String toString() {
		return String.format
		(	"jvmId={%s} agentHeadJar={%s} agentBodyJar={%s}"
		,	JVM_ID
		,	AGENT_HEAD_JAR
		,	AGENT_BODY_JAR
		);
	}

}
