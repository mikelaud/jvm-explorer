package com.blogspot.mikelaud.je.ssh.hosts;

import java.nio.file.Path;

public interface Host {

	String getHostName();
	int getPort();
	String getUserName();
	//
	boolean login(String aUserName, String aPassword);
	void logout();
	//
	boolean isOnline();
	//
	int exec(String aCommand);
	int copyFromLocal(Path aFileDestination, Path aFileSource);
	int copyToLocal(Path aFileDestination, Path aFileSource);

}
