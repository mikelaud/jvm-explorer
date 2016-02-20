package com.blogspot.mikelaud.je.ssh.operations;

import com.jcraft.jsch.Session;

public interface SshOperation {

	int execute(Session aSession);

}
