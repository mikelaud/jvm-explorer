package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Path;

public interface AgentLoader {

	boolean loadAgent(Path aAgentHeadJar, Path aAgentBodyJar);
	boolean loadAgent(Path aAgentHeadJar, Path aAgentBodyJar, String aJvmId);
	RemoteLoader loadAgent(Path aAgentHeadJar, Path aAgentBodyJar, String aJvmId, String aHost);

}
