package com.blogspot.mikelaud.je.agent.bios.common;

import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
	public String getJvmId() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		int splitIndex = name.indexOf('@');
		return name.substring(0, splitIndex > 0 ? splitIndex : 0);
	}

	@Override
	public List<JvmIdentity> getJvmList() {
		return VirtualMachine.list().stream().map(JvmIdentity::new).collect(Collectors.toList());
	}

	@Override
	public boolean loadAgent(Path aAgentHeadJar, Path aAgentBodyJar) {
		return loadAgent(aAgentHeadJar, aAgentBodyJar, getJvmId());
	}

	@Override
	public boolean loadAgent(Path aAgentHeadJar, Path aAgentBodyJar, String aJvmId) {
		Objects.requireNonNull(aAgentHeadJar);
		Objects.requireNonNull(aAgentBodyJar);
		Objects.requireNonNull(aJvmId);
		//
		AgentIdentity agentIdentity = new AgentIdentity(aJvmId, aAgentHeadJar, aAgentBodyJar);
		return loadAgent(agentIdentity);
	}

}
