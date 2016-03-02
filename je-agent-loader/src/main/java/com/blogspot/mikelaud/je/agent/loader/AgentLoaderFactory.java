package com.blogspot.mikelaud.je.agent.loader;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.agent.loader.common.AgentLoader;

public interface AgentLoaderFactory {

	AgentLoader newLocalAgentLoader(Path aAgentHeadJar, Path aAgentBodyJar);
	AgentLoader newRemoteAgentLoader(Path aAgentHeadJar, Path aAgentBodyJar, String aHost);

}
