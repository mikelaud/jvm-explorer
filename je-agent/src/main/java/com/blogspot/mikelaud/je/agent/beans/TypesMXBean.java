package com.blogspot.mikelaud.je.agent.beans;

import java.util.List;

public interface TypesMXBean {

	void echo();
	List<byte[]> getBytecodes();
	
	void addLogging(int aClassLoaderId, String aClassDesc, String aMethodDesc);
	void removeLogging(int aClassLoaderId, String aClassDesc, String aMethodDesc);
	
}
