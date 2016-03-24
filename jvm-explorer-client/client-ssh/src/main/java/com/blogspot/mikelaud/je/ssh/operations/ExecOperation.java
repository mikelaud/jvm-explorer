package com.blogspot.mikelaud.je.ssh.operations;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class ExecOperation extends AbstractOperation {

	private final Logger LOGGER = LoggerFactory.getLogger(ExecOperation.class);
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
		LOGGER.info(mMessage);
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
