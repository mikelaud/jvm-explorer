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
	//
	private String mHostName;
	private String mUserName;

	protected abstract int executeOperation(Session aSession) throws Exception;

	protected AbstractOperation() {
		EXEC_CHANNEL_TYPE = "exec";
		POLL_INTERVAL = Duration.ofMillis(100);
		//
		mHostName = "<unknown>";
		mUserName = "<unknown>";
	}

	protected boolean hasError(int aRcode) {
		return (OperationStatus.EXIT_SUCCESS.getValue() != aRcode);
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
	public String getHostName() {
		return mHostName;
	}

	@Override
	public String getUserName() {
		return mUserName;
	}

	@Override
	public final int execute(Session aSession) {
		Objects.requireNonNull(aSession);
		mHostName = Objects.requireNonNull(aSession.getHost());
		mUserName = Objects.requireNonNull(aSession.getUserName());
		//
		int rcode = OperationStatus.EXIT_SUCCESS.getValue();
		try {
			System.out.println(String.format("[ssh]: %s", toString()));
			rcode = executeOperation(aSession);
			if (hasError(rcode)) {
				System.out.println(String.format("[ssh]: ERROR: exit status: %d", rcode));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			rcode = OperationStatus.EXIT_FAILURE.getValue();
		}
		return rcode;
	}

}
