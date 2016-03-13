package com.blogspot.mikelaud.je.agent.loader.common;

import java.io.BufferedReader;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.common.ExitStatus;
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
	public Stream<JvmIdentity> getJvmList() {
		String toolsJar = getToolsJar();
		Status status = SSH.exec("java -cp " + toolsJar + ":./" + getBios().getFileName() + " com.blogspot.mikelaud.je.agent.bios.Main" + " --list");
		if (ExitStatus.SUCCESS.isNot(status.getCode())) {
			return Stream.empty();
		}
		ArrayList<JvmIdentity> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new StringReader(status.getMessage()))) {
			for (;;) {
				String string = reader.readLine();
				if (null == string) break;
				if (string.length() <= 0) continue;
				int index = string.indexOf(' ');
				String pid = "";
				String name = "";
				if (index <= 0) {
					pid = string;
				}
				else {
					pid = string.substring(0, index);
					if (index < string.length()) {
						name = string.substring(index + 1);
					}
				}
				list.add(new JvmIdentity(pid, name));
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list.stream();
	}

	@Override
	public Status loadAgentById(String aJvmId) {
		String toolsJar = getToolsJar();
		return SSH.exec("java -cp " + toolsJar + ":./" + getBios().getFileName() + " com.blogspot.mikelaud.je.agent.bios.Main" + " --id " + aJvmId + " " + getHead().getFileName() + " " + getBody().getFileName());
	}

	@Override
	public Status loadAgentByName(String aJvmName) {
		String toolsJar = getToolsJar();
		return SSH.exec("java -cp " + toolsJar + ":./" + getBios().getFileName() + " com.blogspot.mikelaud.je.agent.bios.Main" + " --name " + aJvmName + " " + getHead().getFileName() + " " + getBody().getFileName());
	}

}
