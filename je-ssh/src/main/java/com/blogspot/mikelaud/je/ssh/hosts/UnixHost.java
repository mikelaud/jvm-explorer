package com.blogspot.mikelaud.je.ssh.hosts;

import java.nio.file.Path;
import java.util.Objects;

import com.blogspot.mikelaud.je.ssh.common.OperationStatus;
import com.blogspot.mikelaud.je.ssh.operations.CopyOperation;
import com.blogspot.mikelaud.je.ssh.operations.ExecOperation;
import com.blogspot.mikelaud.je.ssh.operations.SshOperation;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class UnixHost implements Host {

	private final String HOST_NAME;
	private final int PORT;
	//
	private Session mSession;

	private boolean hasSession() {
		return (null != mSession);
	}

	private int execute(SshOperation aOperation) {
		if (hasSession()) {
			return aOperation.execute(mSession);
		}
		else {
			System.out.println(String.format("[%s]: no ssh session for: %s", toString(), aOperation.toString()));
			return OperationStatus.EXIT_FAILURE.getValue();
		}
	}

	public UnixHost(String aHostName, int aPort) {
		HOST_NAME = Objects.requireNonNull(aHostName);
		PORT = aPort;
		//
		mSession = null;
	}

	@Override
	public String getHostName() {
		return HOST_NAME;
	}

	@Override
	public int getPort() {
		return PORT;
	}

	@Override
	public String getUserName() {
		return (hasSession() ? mSession.getUserName() : "");
	}

	@Override
	public boolean isOnline() {
		return (hasSession() ? mSession.isConnected() : false);
	}

	@Override
	public boolean login(String aUserName, String aPassword) {
		Objects.requireNonNull(aUserName);
		Objects.requireNonNull(aPassword);
		try {
			JSch ssh = new JSch();
			Session session = ssh.getSession(aUserName, HOST_NAME, PORT);
			session.setPassword(aPassword);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			mSession = session.isConnected() ? session : null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return isOnline();
	}

	@Override
	public void logout() {
		if (hasSession()) {
			if (mSession.isConnected()) {
				mSession.disconnect();
			}
			mSession = null;
		}
	}

	@Override
	public int exec(String aCommand) {
		return execute(new ExecOperation(aCommand));
	}

	@Override
	public int copy(Path aFileDestination, Path aFileSource) {
		return execute(new CopyOperation(aFileDestination, aFileSource));
	}

	@Override
	public String toString() {
		return String.format("%s:%d", HOST_NAME, PORT);
	}

}
