package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.ssh.SshFactory;
import com.blogspot.mikelaud.je.ssh.common.SshConst;
import com.blogspot.mikelaud.je.ssh.domain.Status;
import com.blogspot.mikelaud.je.ssh.hosts.Host;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class RemoteAgentLoaderSsh extends AgentLoaderImpl implements RemoteAgentLoader {

	private final Host SSH;

	@Inject
	private RemoteAgentLoaderSsh
	(	SshFactory aSshFactory
	,	AgentSource aAgentSource
	,	@Assisted String aHostName
	) {
		super(aAgentSource);
		SSH = aSshFactory.newHost(aHostName, SshConst.DEFAULT_PORT);
	}

	private String getToolsJar() {
		return getJavaHome() + "/../lib/tools.jar";
	}

	@Override public String getHostName() { return SSH.getEndpoint().getHost(); }
	@Override public String getUserName() { return SSH.getUserName(); }

	@Override
	public void close() {
		SSH.close();
	}

	@Override
	public boolean open(String aUserName, String aPassword) {
		Objects.requireNonNull(aUserName);
		Objects.requireNonNull(aPassword);
		boolean status = SSH.open(aUserName, aPassword);
		status &= (0 == SSH.copyFromLocal(getHead(), Paths.get(getHead().getFileName())));
		status &= (0 == SSH.copyFromLocal(getBody(), Paths.get(getBody().getFileName())));
		status &= (0 == SSH.copyFromLocal(getBios(), Paths.get(getBios().getFileName())));
		return status;
	}

	@Override
	public boolean isOnline() {
		return SSH.isOnline();
	}

	@Override public String getJavaHome() {
		Status status = SSH.exec("java -jar " + getBios().getFileName() + " --home");
		if (0 != status.getCode()) {
			new RuntimeException(status.toString());
		}
		return status.getMessage().trim();
	}

	@Override
	public Stream<JvmIdentity> getJvmList() { // TODO
		String toolsJar = getToolsJar();
		SSH.exec("java -cp " + toolsJar + ":./" + getBios().getFileName() + " com.blogspot.mikelaud.je.agent.bios.Main" + " --list");
		return Stream.empty();
	}

	@Override
	public boolean loadAgentById(String aJvmId) { // TODO
		String toolsJar = getToolsJar();
		SSH.exec("java -cp " + toolsJar + ":./" + getBios().getFileName() + " com.blogspot.mikelaud.je.agent.bios.Main" + " --id " + aJvmId + " " + getHead().getFileName() + " " + getBody().getFileName());
		return true;
	}

	@Override
	public boolean loadAgentByName(String aJvmName) { // TODO
		String toolsJar = getToolsJar();
		SSH.exec("java -cp " + toolsJar + ":./" + getBios().getFileName() + " com.blogspot.mikelaud.je.agent.bios.Main" + " --name " + aJvmName + " " + getHead().getFileName() + " " + getBody().getFileName());
		return true;
	}

}
