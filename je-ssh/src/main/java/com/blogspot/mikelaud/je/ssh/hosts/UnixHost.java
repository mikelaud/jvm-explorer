package com.blogspot.mikelaud.je.ssh.hosts;

import java.nio.file.Path;
import java.util.Objects;

import com.blogspot.mikelaud.je.ssh.common.Endpoint;
import com.blogspot.mikelaud.je.ssh.common.ExitStatus;
import com.blogspot.mikelaud.je.ssh.common.Logger;
import com.blogspot.mikelaud.je.ssh.operations.CopyFromLocalOperation;
import com.blogspot.mikelaud.je.ssh.operations.CopyToLocalOperation;
import com.blogspot.mikelaud.je.ssh.operations.ExecOperation;
import com.blogspot.mikelaud.je.ssh.operations.Operation;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class UnixHost implements Host {

	private final Endpoint ENDPOINT;
	private Session mSession;

	private boolean hasSession() {
		return (null != mSession);
	}

	private int execute(Operation aOperation) {
		if (hasSession()) {
			return aOperation.execute(mSession);
		}
		else {
			Logger.error(String.format("No ssh session for: %s", aOperation));
			return ExitStatus.ABORT.get();
		}
	}

	public UnixHost(String aHostName, int aPort) {
		Objects.requireNonNull(aHostName);
		ENDPOINT = new Endpoint(aHostName, aPort);
		mSession = null;
	}

	@Override
	public Endpoint getEndpoint() {
		return ENDPOINT;
	}

	@Override
	public String getUserName() {
		return (hasSession() ? mSession.getUserName() : "");
	}

	@Override
	public boolean login(String aUserName, String aPassword) {
		Objects.requireNonNull(aUserName);
		Objects.requireNonNull(aPassword);
		try {
			JSch ssh = new JSch();
			Session session = ssh.getSession(aUserName, ENDPOINT.getHost(), ENDPOINT.getPort());
			session.setPassword(aPassword);
			session.setConfig("StrictHostKeyChecking", "no");
			try {
				Logger.info(String.format("ssh %s@%s", aUserName, ENDPOINT));
				session.connect();
			}
			catch (JSchException e) {
				Logger.error(e.getMessage());
			}
			mSession = session.isConnected() ? session : null;
		}
		catch (Exception e) {
			Logger.error(e);
		}
		return isOnline();
	}

	@Override
	public void logout() {
		if (hasSession()) {
			if (mSession.isConnected()) {
				Logger.info("logout");
				mSession.disconnect();
			}
			mSession = null;
		}
	}

	@Override
	public boolean isOnline() {
		return (hasSession() ? mSession.isConnected() : false);
	}

	@Override
	public int exec(String aCommand) {
		return execute(new ExecOperation(aCommand));
	}

	@Override
	public int copyFromLocal(Path aFileLocal, Path aFileRemote) {
		return execute(new CopyFromLocalOperation(aFileLocal, aFileRemote));
	}

	@Override
	public int copyToLocal(Path aFileRemote, Path aFileLocal) {
		return execute(new CopyToLocalOperation(aFileRemote, aFileLocal));
	}

	@Override
	public String toString() {
		return ENDPOINT.toString();
	}

}
