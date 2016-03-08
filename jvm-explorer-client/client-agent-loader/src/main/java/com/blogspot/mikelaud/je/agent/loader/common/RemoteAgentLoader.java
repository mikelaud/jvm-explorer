package com.blogspot.mikelaud.je.agent.loader.common;

public interface RemoteAgentLoader extends AgentLoader, AutoCloseable {

	String getHostName();
	String getUserName();
	//
	@Override
	void close();
	boolean open(String aUserName, String aPassword);
	//
	boolean isOnline();

}
