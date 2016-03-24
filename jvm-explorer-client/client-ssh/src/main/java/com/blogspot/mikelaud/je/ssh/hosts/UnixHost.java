package com.blogspot.mikelaud.je.ssh.hosts;

import java.nio.file.Path;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.mikelaud.je.common.file_source.FileSource;
import com.blogspot.mikelaud.je.common.file_source.FileSourceFactory;
import com.blogspot.mikelaud.je.ssh.common.ExitStatus;
import com.blogspot.mikelaud.je.ssh.domain.Endpoint;
import com.blogspot.mikelaud.je.ssh.domain.Status;
import com.blogspot.mikelaud.je.ssh.operations.CopyFromLocalOperation;
import com.blogspot.mikelaud.je.ssh.operations.CopyToVoidOperation;
import com.blogspot.mikelaud.je.ssh.operations.CopyToLocalOperation;
import com.blogspot.mikelaud.je.ssh.operations.ExecOperation;
import com.blogspot.mikelaud.je.ssh.operations.Operation;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class UnixHost implements Host {

	private final Logger LOGGER = LoggerFactory.getLogger(UnixHost.class);
	private final FileSourceFactory FILE_SOURCE_FACTORY;
	private final Endpoint ENDPOINT;
	//
	private Session mSession;

	private boolean hasSession() {
		return (null != mSession);
	}

	private int execute(Operation aOperation) {
		if (hasSession()) {
			return aOperation.execute(mSession);
		}
		else {
			LOGGER.error("No ssh session for: {}", aOperation);
			return ExitStatus.ABORT.get();
		}
	}

	@Inject
	private UnixHost
	(	FileSourceFactory aFileSourceFactory
	,	@Assisted String aHostName
	,	@Assisted int aPort
	) {
		FILE_SOURCE_FACTORY = Objects.requireNonNull(aFileSourceFactory);
		ENDPOINT = new Endpoint(Objects.requireNonNull(aHostName), aPort);
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
	public void close() {
		LOGGER.info("close");
		if (hasSession()) {
			if (mSession.isConnected()) {
				mSession.disconnect();
			}
			mSession = null;
		}
	}

	@Override
	public boolean open(String aUserName, String aPassword) {
		Objects.requireNonNull(aUserName);
		Objects.requireNonNull(aPassword);
		try {
			JSch ssh = new JSch();
			Session session = ssh.getSession(aUserName, ENDPOINT.getHost(), ENDPOINT.getPort());
			session.setPassword(aPassword);
			session.setConfig("StrictHostKeyChecking", "no");
			try {
				LOGGER.info("ssh {}@{}", aUserName, ENDPOINT);
				session.connect();
			}
			catch (JSchException e) {
				LOGGER.error(e.getMessage());
			}
			mSession = session.isConnected() ? session : null;
		}
		catch (Exception e) {
			LOGGER.error("{}", e);
		}
		return isOnline();
	}

	@Override
	public boolean isOnline() {
		return (hasSession() ? mSession.isConnected() : false);
	}

	@Override
	public Status exec(String aCommand) {
		ExecOperation exec = new ExecOperation(aCommand);
		int code = execute(exec);
		return new Status(code, exec.getMessage());
	}

	@Override
	public int copyToLocal(Path aFileRemote, Path aFileLocal) {
		return execute(new CopyToLocalOperation(aFileRemote, aFileLocal));
	}

	@Override
	public int copyFromLocal(Path aFileLocal, Path aFileRemote) {
		FileSource fileSource = FILE_SOURCE_FACTORY.newFileSource(aFileLocal);
		return copyFromLocal(fileSource, aFileRemote);
	}

	@Override
	public int copyFromLocal(FileSource aFileLocal, Path aFileRemote) {
		int status = ExitStatus.ABORT.get();
		while (true) {
			CopyFromLocalOperation copyFrom = new CopyFromLocalOperation(aFileLocal, aFileRemote);
			status = execute(copyFrom);
			if (ExitStatus.SUCCESS.isNot(status)) break;
			//
			CopyToVoidOperation copyTo = new CopyToVoidOperation(aFileRemote, aFileLocal);
			status = execute(copyTo);
			if (ExitStatus.SUCCESS.isNot(status)) break;
			//
			if (! copyFrom.getDigest().getBytes().equals(copyTo.getDigest().getBytes())) {
				LOGGER.error("src digest is different: {}", copyFrom.getDigest());
				LOGGER.error("dst digest is different: {}", copyTo.getDigest());
				status = ExitStatus.WRONG_DIGEST.get();
			}
			break;
		}
		return status;
	}

	@Override
	public String toString() {
		return ENDPOINT.toString();
	}

}
