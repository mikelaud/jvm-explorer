package com.blogspot.mikelaud.je.ssh;

import com.blogspot.mikelaud.je.ssh.hosts.Host;

public interface SshFactory {

	Host newHost(String aHostName, int aPort);

}
