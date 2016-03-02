package com.blogspot.mikelaud.je.agent.loader;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.agent.loader.common.AgentLoader;

public interface AgentLoaderFactory {

	AgentLoader newLocalLoader(Path aAgentHeadJar, Path aAgentBodyJar);
	AgentLoader newRemoteLoader(Path aAgentHeadJar, Path aAgentBodyJar);

}
