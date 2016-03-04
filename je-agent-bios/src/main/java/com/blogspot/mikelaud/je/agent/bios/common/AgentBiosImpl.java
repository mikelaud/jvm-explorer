package com.blogspot.mikelaud.je.agent.bios.common;

import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.AgentIdentity;
import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.google.inject.Inject;
import com.sun.tools.attach.VirtualMachine;

public class AgentBiosImpl implements AgentBios {

	private void loadAgentHead(AgentIdentity aAgentIdentity) throws Exception {
		VirtualMachine jvm = VirtualMachine.attach(aAgentIdentity.getJvmId());
		jvm.loadAgent(aAgentIdentity.getAgentHeadJar().toString(), aAgentIdentity.getAgentBodyJar().toString());
		jvm.detach();
	}

	private boolean loadAgent(AgentIdentity aAgentIdentity) {
		try {
			Logger.log("Load agent head: " + aAgentIdentity);
			loadAgentHead(aAgentIdentity);
			return true;
		}
		catch (Exception e) {
			Logger.log(e);
			return false;
		}
		finally {
			Logger.log("done.");
		}
	}

	@Inject
	private AgentBiosImpl() {
		// void
	}

	@Override
	public String getJavaHome() {
		return System.getProperty("java.home");
	}

	@Override
	public String getJvmId() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		int splitIndex = name.indexOf('@');
		return name.substring(0, splitIndex > 0 ? splitIndex : 0);
	}

	@Override
	public Stream<JvmIdentity> getJvmList() {
		return VirtualMachine.list().stream().map(JvmIdentity::new);
	}

	@Override
	public boolean loadAgent(Path aAgentHeadJar, Path aAgentBodyJar) {
		Objects.requireNonNull(aAgentHeadJar);
		Objects.requireNonNull(aAgentBodyJar);
		//
		return loadAgentById(getJvmId(), aAgentHeadJar, aAgentBodyJar);
	}

	@Override
	public boolean loadAgentById(String aJvmId, Path aAgentHeadJar, Path aAgentBodyJar) {
		Objects.requireNonNull(aJvmId);
		Objects.requireNonNull(aAgentHeadJar);
		Objects.requireNonNull(aAgentBodyJar);
		//
		Logger.log("Load agent by id: " + aJvmId);
		AgentIdentity agentIdentity = new AgentIdentity(aJvmId, aAgentHeadJar, aAgentBodyJar);
		return loadAgent(agentIdentity);
	}

	@Override
	public boolean loadAgentByName(String aJvmName, Path aAgentHeadJar, Path aAgentBodyJar) {
		Objects.requireNonNull(aJvmName);
		Objects.requireNonNull(aAgentHeadJar);
		Objects.requireNonNull(aAgentBodyJar);
		//
		Logger.log("Load agent by name: " + aJvmName);
		List<JvmIdentity> found = getJvmList().filter(jvm -> jvm.getName().startsWith(aJvmName)).collect(Collectors.toList());
		int jvmCount = found.size();
		switch (jvmCount) {
			case 1:
				String jvmId = found.get(0).getId();
				return loadAgentById(jvmId, aAgentHeadJar, aAgentBodyJar);
			case 0:
				Logger.log(String.format("loadAgentByName(%s) fail: not found", aJvmName));
				break;
			default:
				Logger.log(String.format("loadAgentByName(%s) fail: count = %d", aJvmName, jvmCount));
				break;
		}
		return false;
	}

}
