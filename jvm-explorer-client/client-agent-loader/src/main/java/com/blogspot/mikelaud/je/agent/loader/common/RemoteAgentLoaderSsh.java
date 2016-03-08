package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.common.file_source.FileSource;
import com.blogspot.mikelaud.je.common.file_source.FileSourceFactory;
import com.blogspot.mikelaud.je.ssh.SshFactory;
import com.blogspot.mikelaud.je.ssh.common.SshConst;
import com.blogspot.mikelaud.je.ssh.domain.Status;
import com.blogspot.mikelaud.je.ssh.hosts.Host;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class RemoteAgentLoaderSsh extends AgentLoaderImpl implements RemoteAgentLoader {

	private final FileSource AGENT_BIOS_JAR;
	private final Host SSH;

	@AssistedInject
	private RemoteAgentLoaderSsh
	(	SshFactory aSshFactory
	,	@Assisted("AgentHeadJar") FileSource aAgentHeadJar
	,	@Assisted("AgentBodyJar") FileSource aAgentBodyJar
	,	@Assisted("AgentBiosJar") FileSource aAgentBiosJar
	,	@Assisted("HostName") String aHostName
	) {
		super(aAgentHeadJar, aAgentBodyJar);
		AGENT_BIOS_JAR = aAgentBiosJar;
		SSH = aSshFactory.newHost(aHostName, SshConst.DEFAULT_PORT);
	}

	@AssistedInject
	private RemoteAgentLoaderSsh
	(	FileSourceFactory aFileSourceFactory
	,	SshFactory aSshFactory
	,	@Assisted("AgentHeadJar") Path aAgentHeadJar
	,	@Assisted("AgentBodyJar") Path aAgentBodyJar
	,	@Assisted("AgentBiosJar") Path aAgentBiosJar
	,	@Assisted("HostName") String aHostName
	) {
		this
		(	aSshFactory
		,	aFileSourceFactory.newFileSource(aAgentHeadJar)
		,	aFileSourceFactory.newFileSource(aAgentBodyJar)
		,	aFileSourceFactory.newFileSource(aAgentBiosJar)
		,	aHostName
		);
	}

	@AssistedInject
	private RemoteAgentLoaderSsh
	(	SshFactory aSshFactory
	,	AgentSource aAgentSource
	,	@Assisted String aHostName
	) {
		this
		(	aSshFactory
		,	aAgentSource.getHead()
		,	aAgentSource.getBody()
		,	aAgentSource.getBios()
		,	aHostName
		);
	}

	private String getToolsJar() {
		return getJavaHome() + "/../lib/tools.jar";
	}

	@Override public FileSource getBiosJar() { return AGENT_BIOS_JAR; }
	//
	@Override public String getHostName() { return SSH.getEndpoint().getHost(); }
	@Override public String getUserName() { return SSH.getUserName(); }

	@Override
	public boolean login(String aUserName, String aPassword) {
		Objects.requireNonNull(aUserName);
		Objects.requireNonNull(aPassword);
		boolean status = SSH.login(aUserName, aPassword);
		status &= (0 == SSH.copyFromLocal(getHeadJar(), Paths.get(getHeadJar().getFileName())));
		status &= (0 == SSH.copyFromLocal(getBodyJar(), Paths.get(getBodyJar().getFileName())));
		status &= (0 == SSH.copyFromLocal(getBiosJar(), Paths.get(getBiosJar().getFileName())));
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

	@Override public String getJavaHome() {
		Status status = SSH.exec("java -jar " + getBiosJar().getFileName() + " --home");
		if (0 != status.getCode()) {
			new RuntimeException(status.toString());
		}
		return status.getMessage().trim();
	}

	@Override
	public Stream<JvmIdentity> getJvmList() { // TODO
		String toolsJar = getToolsJar();
		SSH.exec("java -cp " + toolsJar + ":./" + getBiosJar().getFileName() + " com.blogspot.mikelaud.je.agent.bios.Main" + " --list");
		return Stream.empty();
	}

	@Override
	public boolean loadAgentById(String aJvmId) { // TODO
		String toolsJar = getToolsJar();
		SSH.exec("java -cp " + toolsJar + ":./" + getBiosJar().getFileName() + " com.blogspot.mikelaud.je.agent.bios.Main" + " --id " + aJvmId + " " + getHeadJar().getFileName() + " " + getBodyJar().getFileName());
		return true;
	}

	@Override
	public boolean loadAgentByName(String aJvmName) { // TODO
		String toolsJar = getToolsJar();
		SSH.exec("java -cp " + toolsJar + ":./" + getBiosJar().getFileName() + " com.blogspot.mikelaud.je.agent.bios.Main" + " --name " + aJvmName + " " + getHeadJar().getFileName() + " " + getBodyJar().getFileName());
		return true;
	}

}
