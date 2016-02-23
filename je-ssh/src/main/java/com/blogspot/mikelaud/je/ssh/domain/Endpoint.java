package com.blogspot.mikelaud.je.ssh.domain;

import java.util.Objects;

public class Endpoint {

	private final String HOST;
	private final int PORT;

	public Endpoint(String aHost, int aPort) {
		HOST = Objects.requireNonNull(aHost);
		PORT = aPort;
	}

	public String getHost() { return HOST; }
	public int getPort() { return PORT; }

	@Override
	public String toString() {
		return String.format("%s:%d", HOST, PORT);
	}

}
