package com.blogspot.mikelaud.je.ssh.common;

public enum OperationStatus {

	EXIT_SUCCESS(0),
	EXIT_FAILURE(666);

	private final int VALUE;

	private OperationStatus(int aValue) {
		VALUE = aValue;
	}

	public int getValue() {
		return VALUE;
	}

}
