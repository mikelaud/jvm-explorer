package com.blogspot.mikelaud.je.agent.loader;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.agent.loader.common.LocalAgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteAgentLoader;
import com.google.inject.assistedinject.Assisted;

public interface AgentLoaderFactory {

	LocalAgentLoader newLocalLoader
	(	@Assisted("AgentHeadJar") Path aAgentHeadJar
	,	@Assisted("AgentBodyJar") Path aAgentBodyJar
	);

	RemoteAgentLoader newRemoteLoader
	(	@Assisted("AgentHeadJar") Path aAgentHeadJar
	,	@Assisted("AgentBodyJar") Path aAgentBodyJar
	,	@Assisted("HostName") String aHostName
	);

}
