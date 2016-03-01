package com.blogspot.mikelaud.je.agent.loader;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.agent.loader.common.AgentLoader;

public interface AgentLoaderFactory {

	AgentLoader newAgentLoader(Path aAgentHeadJar, Path aAgentBodyJar);

}
