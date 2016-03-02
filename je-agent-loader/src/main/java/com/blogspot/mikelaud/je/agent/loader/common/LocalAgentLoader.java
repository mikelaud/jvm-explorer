package com.blogspot.mikelaud.je.agent.loader.common;

public interface LocalAgentLoader extends AgentLoader {

	String getJvmId();
	boolean loadAgent();
	
}
