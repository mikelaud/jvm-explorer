package com.blogspot.mikelaud.je.agent.loader.common;

import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;

public interface RemoteLoader {

	Stream<JvmIdentity> getJvmList();
	boolean loadAgent(String aJvmId);

}
