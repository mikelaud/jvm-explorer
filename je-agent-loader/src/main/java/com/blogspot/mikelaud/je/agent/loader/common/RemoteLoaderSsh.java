package com.blogspot.mikelaud.je.agent.loader.common;

import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.common.AgentBios;
import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class RemoteLoaderSsh implements RemoteLoader {

	private final AgentBios AGENT_BIOS;
	
	@Inject
	private RemoteLoaderSsh
	(	@Assisted AgentBios aAgentBios
	,	@Assisted String aHost
	,	@Assisted String aUserName
	,	@Assisted String aPassword
	) {
		AGENT_BIOS = aAgentBios;
	}
	
	@Override
	public Stream<JvmIdentity> getJvmList() {
		return AGENT_BIOS.getJvmList();
	}

	@Override
	public boolean loadAgent(String aJvmId) {
		// TODO Auto-generated method stub
		return false;
	}

}
