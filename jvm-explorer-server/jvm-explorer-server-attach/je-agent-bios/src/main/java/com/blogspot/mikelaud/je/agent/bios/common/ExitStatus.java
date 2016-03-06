package com.blogspot.mikelaud.je.agent.bios.common;

public enum ExitStatus {

	SUCCESS(0),
	FAILURE(1);

	private final int STATUS;

	private ExitStatus(int aStatus) {
		STATUS = aStatus;
	}

	public static int get(boolean aSuccess) {
		return (aSuccess ? SUCCESS.get() : FAILURE.get());
	}

	public int get() {
		return STATUS;
	}

	public boolean is(int aStatus) {
		return (STATUS == aStatus);
	}

	public boolean isNot(int aStatus) {
		return ! is(aStatus);
	}

	@Override
	public String toString() {
		return String.format("%s(%d)", name(), get());
	}

}
