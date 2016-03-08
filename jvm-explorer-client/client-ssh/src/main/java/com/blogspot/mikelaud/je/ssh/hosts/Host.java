package com.blogspot.mikelaud.je.ssh.hosts;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.common.file_source.FileSource;
import com.blogspot.mikelaud.je.ssh.domain.Endpoint;
import com.blogspot.mikelaud.je.ssh.domain.Status;

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
	int copyToLocal(Path aFileRemote, Path aFileLocal);
	int copyFromLocal(Path aFileLocal, Path aFileRemote);
	int copyFromLocal(FileSource aFileLocal, Path aFileRemote);

}
