package com.blogspot.mikelaud.je.ssh;

import java.nio.file.Paths;

import com.blogspot.mikelaud.je.ssh.hosts.Host;
import com.blogspot.mikelaud.je.ssh.hosts.UnixHost;

public class Main {

	public static void main(String[] arg) {
		Host host = new UnixHost("192.168.10.101", 22);
		host.login("root", "xxx");
		host.exec("ls -l");
		host.exec("pwd");
		host.copy(Paths.get("notepad.exe"), Paths.get("C:/Windows/notepad.exe2"));
		host.exec("ls");
		host.logout();
	}
}
