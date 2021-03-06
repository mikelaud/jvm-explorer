package com.blogspot.mikelaud.je.ssh.operations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.mikelaud.je.ssh.common.ExitStatus;
import com.blogspot.mikelaud.je.ssh.common.UnixConst;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public abstract class AbstractOperation implements Operation {

	private final Logger LOGGER = LoggerFactory.getLogger(AbstractOperation.class);
	private final String EXEC_CHANNEL_TYPE;
	private final Duration POLL_INTERVAL;
	private final byte[] ZERO_BUFFER;
	private final int COPY_BUFFER_SIZE;
	//
	private String mHostName;
	private String mUserName;

	protected abstract int executeOperation(Session aSession) throws Exception;

	protected AbstractOperation() {
		EXEC_CHANNEL_TYPE = "exec";
		POLL_INTERVAL = Duration.ofMillis(100);
		ZERO_BUFFER = new byte[1];
		COPY_BUFFER_SIZE = 1024;
		//
		mHostName = "";
		mUserName = "";
	}

	protected final ChannelExec newChannelExec(Session aSession) throws JSchException {
		return ChannelExec.class.cast(aSession.openChannel(EXEC_CHANNEL_TYPE));
	}

	protected final MessageDigest newMessageDigest() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("SHA-256");
	}

	protected final byte[] newCopyBuffer() {
		return new byte[COPY_BUFFER_SIZE];
	}

	protected boolean hasError(int aStatus) {
		return ExitStatus.SUCCESS.isNot(aStatus);
	}

	protected final void waitEof(Channel aChannel) {
		while (!aChannel.isEOF()) {
			try {
				Thread.sleep(POLL_INTERVAL.toMillis());
			}
			catch (InterruptedException e) {
				LOGGER.error("{}", e);
			}
		}
	}

	protected void writeZero(OutputStream aOut) throws IOException {
		ZERO_BUFFER[0] = 0;
		aOut.write(ZERO_BUFFER, 0, 1); // send '\0'
		aOut.flush();
	}

	protected String readString(InputStream aIn, char aTerminator) throws IOException {
		final int END = (int) aTerminator;
		StringBuilder charactes = new StringBuilder();
		int character;
		while (true) {
			character = aIn.read();
			if (ExitStatus.NO_DATA.is(character) || END == character) break;
			charactes.append((char)character);
		}
		return charactes.toString();
	}

	protected long readLong(InputStream aIn, char aTerminator) throws IOException {
		return Long.parseLong(readString(aIn, aTerminator));
	}

	protected int checkAck(InputStream aIn) throws IOException {
		int status = aIn.read();
		// status may be:
		// -1: NO_DATA
		//  0: SUCCESS
		//  1: ERROR
		//  2: FATAL_ERROR
		if (ExitStatus.ERROR.is(status) || ExitStatus.FATAL_ERROR.is(status)) {
			String errorMessage = readString(aIn, UnixConst.NEW_LINE);
			if (! errorMessage.isEmpty()) {
				LOGGER.error(errorMessage);
			}
		}
		return status;
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
		int status = ExitStatus.ABORT.get();
		try {
			LOGGER.info(toString());
			try {
				status = executeOperation(aSession);
			}
			catch (JSchException e) {
				LOGGER.error(e.getMessage());
			}
			if (hasError(status)) {
				LOGGER.error("exit status: {}", status);
			}
		}
		catch (Exception e) {
			LOGGER.error("{}", e);
			status = ExitStatus.ABORT.get();
		}
		return status;
	}

}
