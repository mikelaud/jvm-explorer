package com.blogspot.mikelaud.je.core;

import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.domain.Domain;

public interface Core {

	Domain getDomain();
	void setDefaultTypes();
	String loadAgent(String aHost, String aName);
	//
	void doJvmConnect(String aHost);
	void doJvmDisconnect();
	Stream<JvmIdentity> doJvmList();

}
