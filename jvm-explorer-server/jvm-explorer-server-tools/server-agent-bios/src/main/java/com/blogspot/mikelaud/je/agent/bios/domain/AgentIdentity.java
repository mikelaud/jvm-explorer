package com.blogspot.mikelaud.je.agent.bios.domain;

import com.blogspot.mikelaud.je.agent.common.Utils;

public class AgentIdentity {

	private final String JVM_ID;
	private final String AGENT_HEAD_JAR;
	private final String AGENT_BODY_JAR;

	public AgentIdentity(String aJvmId, String aAgentHeadJar, String aAgentBodyJar) {
		JVM_ID = Utils.requireNonNull(aJvmId);
		AGENT_HEAD_JAR = Utils.requireNonNull(aAgentHeadJar);
		AGENT_BODY_JAR = Utils.requireNonNull(aAgentBodyJar);
	}

	public AgentIdentity(JvmIdentity aJvmIdentity, String aAgentHeadJar, String aAgentBodyJar) {
		this(Utils.requireNonNull(aJvmIdentity).getId(), aAgentHeadJar, aAgentBodyJar);
	}

	public String getJvmId() { return JVM_ID; }
	public String getAgentHeadJar() { return AGENT_HEAD_JAR; }
	public String getAgentBodyJar() { return AGENT_BODY_JAR; }

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
