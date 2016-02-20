package com.blogspot.mikelaud.je.ssh.operations;

import java.util.Objects;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class ExecOperation extends AbstractOperation {

	private final String COMMAND;

	@Override
	protected int executeOperation(Session aSession) throws Exception {
		Objects.requireNonNull(aSession);
		ChannelExec channel = newChannelExec(aSession);
		channel.setCommand(COMMAND);
		channel.setInputStream(null);
		channel.setOutputStream(System.out, true);
		channel.setErrStream(System.out, true);
		channel.connect();
		waitEof(channel);
		int rcode = channel.getExitStatus();
		channel.disconnect();
		return rcode;
	}

	public ExecOperation(String aCommand) {
		COMMAND = Objects.requireNonNull(aCommand);
	}

	public String getCommand() {
		return COMMAND;
	}

	@Override
	public String toString() {
		return COMMAND;
	}

}
