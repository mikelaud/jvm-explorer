package com.blogspot.mikelaud.je.ssh.hosts;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.ssh.common.Endpoint;
import com.blogspot.mikelaud.je.ssh.common.Status;

public interface Host {

	Endpoint getEndpoint();
	String getUserName();
	//
	boolean login(String aUserName, String aPassword);
	void logout();
	//
	boolean isOnline();
	//
	Status exec(String aCommand);
	int copyFromLocal(Path aFileLocal, Path aFileRemote);
	int copyToLocal(Path aFileRemote, Path aFileLocal);

}
