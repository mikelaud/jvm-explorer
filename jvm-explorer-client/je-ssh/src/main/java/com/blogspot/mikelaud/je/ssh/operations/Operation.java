package com.blogspot.mikelaud.je.ssh.operations;

import com.jcraft.jsch.Session;

public interface Operation {

	String getHostName();
	String getUserName();
	//
	int execute(Session aSession);

}
