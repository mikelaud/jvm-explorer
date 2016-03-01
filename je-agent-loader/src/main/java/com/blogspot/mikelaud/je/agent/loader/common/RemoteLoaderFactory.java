package com.blogspot.mikelaud.je.agent.loader.common;

import com.blogspot.mikelaud.je.agent.bios.common.AgentBios;

public interface RemoteLoaderFactory {

	RemoteLoader newRemoteLoaderSsh
	(	AgentBios aAgentBios
	,	String aHost
	,	String aUserName
	,	String aPassword
	); 
	
}
