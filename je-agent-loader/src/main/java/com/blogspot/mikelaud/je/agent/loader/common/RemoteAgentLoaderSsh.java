package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.google.inject.Inject;

public class RemoteAgentLoaderSsh implements RemoteAgentLoader {

	@Inject
	private RemoteAgentLoaderSsh() {
		
	}

	@Override
	public Path getHeadJar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path getBodyJar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<JvmIdentity> getJvmList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loadAgentById(String aJvmId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadAgentByName(String aJvmName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String aUserName, String aPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOnline() {
		// TODO Auto-generated method stub
		return false;
	}

}
