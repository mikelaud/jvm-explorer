package com.blogspot.mikelaud.je.ssh.common;

public enum ExitStatus {

	NO_DATA(-1),
	SUCCESS(0),
	ERROR(1),
	FATAL_ERROR(2),
	EXCEPTION(666);

	private final int VALUE;

	private ExitStatus(int aValue) {
		VALUE = aValue;
	}

	public int getValue() {
		return VALUE;
	}

	public boolean is(int aExitStatus) {
		return (VALUE == aExitStatus);
	}

}
