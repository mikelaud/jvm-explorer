package com.blogspot.mikelaud.je.agent.bios.common;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import com.blogspot.mikelaud.je.agent.bios.domain.AgentIdentity;
import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.agent.common.Utils;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

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

	public AgentBiosImpl() {
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
	public List<JvmIdentity> getJvmList() {
		List<JvmIdentity> list = new ArrayList<JvmIdentity>();
		for (VirtualMachineDescriptor vm : VirtualMachine.list()) {
			list.add(new JvmIdentity(vm));
		}
		return list;
	}

	@Override
	public boolean loadAgent(String aAgentHeadJar, String aAgentBodyJar) {
		Utils.requireNonNull(aAgentHeadJar);
		Utils.requireNonNull(aAgentBodyJar);
		//
		return loadAgentById(getJvmId(), aAgentHeadJar, aAgentBodyJar);
	}

	@Override
	public boolean loadAgentById(String aJvmId, String aAgentHeadJar, String aAgentBodyJar) {
		Utils.requireNonNull(aJvmId);
		Utils.requireNonNull(aAgentHeadJar);
		Utils.requireNonNull(aAgentBodyJar);
		//
		Logger.log("Load agent by id: " + aJvmId);
		AgentIdentity agentIdentity = new AgentIdentity(aJvmId, aAgentHeadJar, aAgentBodyJar);
		return loadAgent(agentIdentity);
	}

	@Override
	public boolean loadAgentByName(String aJvmName, String aAgentHeadJar, String aAgentBodyJar) {
		Utils.requireNonNull(aJvmName);
		Utils.requireNonNull(aAgentHeadJar);
		Utils.requireNonNull(aAgentBodyJar);
		//
		Logger.log("Load agent by name: " + aJvmName);
		List<JvmIdentity> foundJvm = new ArrayList<JvmIdentity>();
		for (JvmIdentity jvm : getJvmList()) {
			if (Utils.nvl(jvm.getName()).startsWith(aJvmName)) {
				foundJvm.add(jvm);
			}
		}
		//
		int jvmCount = foundJvm.size();
		switch (jvmCount) {
			case 1:
				String jvmId = foundJvm.get(0).getId();
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
