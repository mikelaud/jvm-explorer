package com.blogspot.mikelaud.je.agent.loader;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.agent.loader.common.LocalAgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteAgentLoader;
import com.blogspot.mikelaud.je.common.file_source.FileSource;
import com.google.inject.assistedinject.Assisted;

public interface AgentLoaderFactory {

	LocalAgentLoader newLocalLoader
	(	@Assisted("AgentHeadJar") FileSource aAgentHeadJar
	,	@Assisted("AgentBodyJar") FileSource aAgentBodyJar
	);

	LocalAgentLoader newLocalLoader
	(	@Assisted("AgentHeadJar") Path aAgentHeadJar
	,	@Assisted("AgentBodyJar") Path aAgentBodyJar
	);

	LocalAgentLoader newLocalLoader();

	//------------------------------------------------------------------------

	RemoteAgentLoader newRemoteLoader
	(	@Assisted("AgentHeadJar") FileSource aAgentHeadJar
	,	@Assisted("AgentBodyJar") FileSource aAgentBodyJar
	,	@Assisted("AgentBiosJar") FileSource aAgentBiosJar
	,	@Assisted("HostName") String aHostName
	);

	RemoteAgentLoader newRemoteLoader
	(	@Assisted("AgentHeadJar") Path aAgentHeadJar
	,	@Assisted("AgentBodyJar") Path aAgentBodyJar
	,	@Assisted("AgentBiosJar") Path aAgentBiosJar
	,	@Assisted("HostName") String aHostName
	);

	RemoteAgentLoader newRemoteLoader(String aHostName);

}
