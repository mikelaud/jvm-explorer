package com.blogspot.mikelaud.je.agent.bios.domain;

import java.util.Objects;

import com.sun.tools.attach.VirtualMachineDescriptor;

public class JvmIdentity {

	private final String ID;
	private final String NAME;

	public JvmIdentity(String aId, String aName) {
		ID = Objects.requireNonNull(aId);
		NAME = Objects.requireNonNull(aName);
	}

	public JvmIdentity(VirtualMachineDescriptor aJvmDescriptor) {
		ID = Objects.requireNonNull(aJvmDescriptor).id();
		NAME = aJvmDescriptor.displayName();
	}

	public String getId() { return ID; }
	public String getName() { return NAME; }

	@Override
	public String toString() {
		return String.format("%s %s", ID, NAME);
	}

}
