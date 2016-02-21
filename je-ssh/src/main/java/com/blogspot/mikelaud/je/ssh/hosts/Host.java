package com.blogspot.mikelaud.je.ssh.hosts;

import java.nio.file.Path;

public interface Host {

	String getHostName();
	int getPort();
	String getUserName();
	//
	boolean isOnline();
	//
	boolean login(String aUserName, String aPassword);
	void logout();
	//
	int exec(String aCommand);
	int copy(Path aFileDestination, Path aFileSource);

}
