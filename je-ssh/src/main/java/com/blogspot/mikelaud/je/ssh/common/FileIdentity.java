package com.blogspot.mikelaud.je.ssh.common;

import java.util.Objects;

public class FileIdentity {

	private final String PERMISSIONS;
	private final long SIZE;
	private final String NAME;

	public FileIdentity(String aPermissions, long aSize, String aName) {
		PERMISSIONS = Objects.requireNonNull(aPermissions);
		SIZE = aSize;
		NAME = Objects.requireNonNull(aName);
	}

	public String getPermissions() { return PERMISSIONS; }
	public long getSize() { return SIZE; }
	public String getName() { return NAME; }

	@Override
	public String toString() {
		return String.format("%s %d %s", PERMISSIONS, SIZE, NAME);
	}

}
