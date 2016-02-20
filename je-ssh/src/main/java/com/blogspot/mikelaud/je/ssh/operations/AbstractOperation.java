package com.blogspot.mikelaud.je.ssh.operations;

import java.time.Duration;
import java.util.Objects;

import com.blogspot.mikelaud.je.ssh.common.OperationStatus;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public abstract class AbstractOperation implements SshOperation {

	private final String EXEC_CHANNEL_TYPE;
	private final Duration POLL_INTERVAL;

	protected abstract int executeOperation(Session aSession) throws Exception;

	protected AbstractOperation() {
		EXEC_CHANNEL_TYPE = "exec";
		POLL_INTERVAL = Duration.ofMillis(100);
	}

	protected final ChannelExec newChannelExec(Session aSession) throws JSchException {
		Objects.requireNonNull(aSession);
		return ChannelExec.class.cast(aSession.openChannel(EXEC_CHANNEL_TYPE));
	}

	protected final void waitEof(Channel aChannel) {
		while (!aChannel.isEOF()) {
			try {
				Thread.sleep(POLL_INTERVAL.toMillis());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public final int execute(Session aSession) {
		Objects.requireNonNull(aSession);
		int rcode = OperationStatus.EXIT_SUCCESS.getValue();
		try {
			System.out.println("execute: " + toString());
			rcode = executeOperation(aSession);
		}
		catch (Exception e) {
			e.printStackTrace();
			rcode = OperationStatus.EXIT_FAILURE.getValue();
		}
		System.out.println("execution exit status: " + rcode);
		return rcode;
	}

}
