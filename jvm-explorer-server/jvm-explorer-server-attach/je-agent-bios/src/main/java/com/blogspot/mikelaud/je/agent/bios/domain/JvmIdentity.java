package com.blogspot.mikelaud.je.agent.bios.domain;

import com.blogspot.mikelaud.je.agent.common.Utils;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class JvmIdentity {

	private final String ID;
	private final String NAME;

	public JvmIdentity(String aId, String aName) {
		ID = Utils.requireNonNull(aId);
		NAME = Utils.requireNonNull(aName);
	}

	public JvmIdentity(VirtualMachineDescriptor aJvmDescriptor) {
		ID = Utils.requireNonNull(aJvmDescriptor).id();
		NAME = aJvmDescriptor.displayName();
	}

	public String getId() { return ID; }
	public String getName() { return NAME; }

	@Override
	public String toString() {
		return String.format("%s %s", ID, NAME);
	}

}
