package com.blogspot.mikelaud.je.agent.beans;

import java.util.List;

public interface TypesMXBean {

	void echo();
	List<byte[]> getBytecodes();
	
	void addLogger(String aClassName, String aMethodName);
	void removeLogger(String aClassName, String aMethodName);
	
}
