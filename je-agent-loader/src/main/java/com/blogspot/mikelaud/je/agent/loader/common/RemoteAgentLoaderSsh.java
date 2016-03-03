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

	private final Path AGENT_BIOS_JAR;
	private final Host SSH;

	@Inject
	private RemoteAgentLoaderSsh
	(	SshFactory aSshFactory
	,	@Assisted("AgentHeadJar") Path aAgentHeadJar
	,	@Assisted("AgentBodyJar") Path aAgentBodyJar
	,	@Assisted("AgentBiosJar") Path aAgentBiosJar
	,	@Assisted("HostName") String aHostName
	) {
		super(aAgentHeadJar, aAgentBodyJar);
		AGENT_BIOS_JAR = aAgentBiosJar;
		SSH = aSshFactory.newHost(aHostName, SshConst.DEFAULT_PORT);
	}

	public Path getBiosJar() { return AGENT_BIOS_JAR; }

	@Override public String getHostName() { return SSH.getEndpoint().getHost(); }
	@Override public String getUserName() { return SSH.getUserName(); }

	@Override
	public boolean login(String aUserName, String aPassword) {
		Objects.requireNonNull(aUserName);
		Objects.requireNonNull(aPassword);
		boolean status = SSH.login(aUserName, aPassword);
		status &= (0 == SSH.copyFromLocal(getHeadJar(), getHeadJar().getFileName()));
		status &= (0 == SSH.copyFromLocal(getBodyJar(), getBodyJar().getFileName()));
		status &= (0 == SSH.copyFromLocal(getBiosJar(), getBiosJar().getFileName()));
		return status;
	}

	@Override
	public void logout() {
		SSH.logout();
	}

	@Override
	public boolean isOnline() {
		return SSH.isOnline();
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
