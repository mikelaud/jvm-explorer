package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.ssh.SshFactory;
import com.blogspot.mikelaud.je.ssh.common.SshConst;
import com.blogspot.mikelaud.je.ssh.hosts.Host;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class RemoteAgentLoaderSsh extends AgentLoaderImpl implements RemoteAgentLoader {

	private final Host HOST;

	@Inject
	private RemoteAgentLoaderSsh
	(	SshFactory aSshFactory
	,	@Assisted("AgentHeadJar") Path aAgentHeadJar
	,	@Assisted("AgentBodyJar") Path aAgentBodyJar
	,	@Assisted("HostName") String aHostName
	) {
		super(aAgentHeadJar, aAgentBodyJar);
		HOST = aSshFactory.newHost(aHostName, SshConst.DEFAULT_PORT);
	}

	@Override public String getHostName() { return HOST.getEndpoint().getHost(); }
	@Override public String getUserName() { return HOST.getUserName(); }

	@Override
	public boolean login(String aUserName, String aPassword) {
		Objects.requireNonNull(aUserName);
		Objects.requireNonNull(aPassword);
		return HOST.login(aUserName, aPassword);
	}

	@Override
	public void logout() {
		HOST.logout();
	}

	@Override
	public boolean isOnline() {
		return HOST.isOnline();
	}

	@Override
	public Stream<JvmIdentity> getJvmList() {
		return Stream.empty();
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

}
