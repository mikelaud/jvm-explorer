package com.blogspot.mikelaud.je.agent.loader;

import com.blogspot.mikelaud.je.agent.loader.common.LocalAgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteAgentLoader;

public interface AgentLoaderFactory {

	LocalAgentLoader newLocalLoader();
	RemoteAgentLoader newRemoteLoader(String aHostName);

}
