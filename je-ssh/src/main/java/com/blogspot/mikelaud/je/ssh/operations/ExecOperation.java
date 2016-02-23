package com.blogspot.mikelaud.je.ssh.operations;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import com.blogspot.mikelaud.je.ssh.common.Logger;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class ExecOperation extends AbstractOperation {

	private final String COMMAND;
	private String mMessage;

	@Override
	protected int executeOperation(Session aSession) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ChannelExec channel = newChannelExec(aSession);
		channel.setCommand(COMMAND);
		channel.setInputStream(null);
		channel.setOutputStream(out, true);
		channel.setErrStream(out, true);
		channel.connect();
		waitEof(channel);
		mMessage = out.toString();
		Logger.out(mMessage);
		int status = channel.getExitStatus();
		channel.disconnect();
		return status;
	}

	public ExecOperation(String aCommand) {
		COMMAND = Objects.requireNonNull(aCommand);
		mMessage = "";
	}

	public String getCommand() {
		return COMMAND;
	}

	public String getMessage() {
		return mMessage;
	}

	@Override
	public String toString() {
		return COMMAND;
	}

}
