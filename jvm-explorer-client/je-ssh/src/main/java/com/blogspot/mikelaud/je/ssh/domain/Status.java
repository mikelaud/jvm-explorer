package com.blogspot.mikelaud.je.ssh.domain;

import java.util.Objects;

public class Status {

	private final int CODE;
	private final String MESSAGE;

	public Status(int aCode, String aMessage) {
		CODE = aCode;
		MESSAGE = Objects.requireNonNull(aMessage);
	}

	public int getCode() { return CODE; }
	public String getMessage() { return MESSAGE; }

	@Override
	public String toString() {
		return MESSAGE;
	}

}
