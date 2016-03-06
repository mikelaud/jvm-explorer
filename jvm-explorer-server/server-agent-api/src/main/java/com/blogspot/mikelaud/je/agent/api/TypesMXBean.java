package com.blogspot.mikelaud.je.agent.api;

public interface TypesMXBean {

	void echo();
	byte[][] getBytecodes();

	void addLogging(int aClassLoaderId, String aClassDesc, String aMethodDesc);
	void removeLogging(int aClassLoaderId, String aClassDesc, String aMethodDesc);

}
