package com.blogspot.mikelaud.je.ssh.common;

public enum ExitStatus {

	SUCCESS(0),
	NO_DATA(-1),
	ERROR(1),
	FATAL_ERROR(2),
	WRONG_DIGEST(100),
	ABORT(200);

	private final int STATUS;

	private ExitStatus(int aStatus) {
		STATUS = aStatus;
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
