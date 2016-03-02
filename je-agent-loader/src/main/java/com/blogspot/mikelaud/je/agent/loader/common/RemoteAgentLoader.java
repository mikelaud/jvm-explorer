package com.blogspot.mikelaud.je.agent.loader.common;

public interface RemoteAgentLoader extends AgentLoader {

	String getHost();
	String getUserName();
	//
	boolean login(String aUserName, String aPassword);
	void logout();
	//
	boolean isOnline();
	
}
