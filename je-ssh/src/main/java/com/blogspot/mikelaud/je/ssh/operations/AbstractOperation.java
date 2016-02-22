package com.blogspot.mikelaud.je.ssh.operations;

import java.time.Duration;
import java.util.Objects;

import com.blogspot.mikelaud.je.ssh.common.ExitStatus;
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

	protected boolean hasError(int aStatus) {
		return ! ExitStatus.SUCCESS.is(aStatus);
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
		int status = ExitStatus.SUCCESS.getValue();
		try {
			System.out.println(String.format("[ssh]: %s", toString()));
			status = executeOperation(aSession);
			if (hasError(status)) {
				System.out.println(String.format("[ssh]: ERROR: exit status: %d", status));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			status = ExitStatus.EXCEPTION.getValue();
		}
		return status;
	}

}
