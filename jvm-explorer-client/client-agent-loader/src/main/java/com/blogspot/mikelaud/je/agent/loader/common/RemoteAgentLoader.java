package com.blogspot.mikelaud.je.agent.loader.common;

import com.blogspot.mikelaud.je.common.file_source.FileSource;

public interface RemoteAgentLoader extends AgentLoader {

	FileSource getBiosJar();
	//
	String getHostName();
	String getUserName();
	//
	boolean login(String aUserName, String aPassword);
	void logout();
	//
	boolean isOnline();

}
