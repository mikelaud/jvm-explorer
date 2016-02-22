package com.blogspot.mikelaud.je.ssh;

import java.nio.file.Paths;

import com.blogspot.mikelaud.je.ssh.hosts.Host;
import com.blogspot.mikelaud.je.ssh.hosts.UnixHost;

public class Main {

	public static void main(String[] arg) {
		Host host = new UnixHost("192.168.10.101", 22);
		host.login("root", "1q2w3e");
		host.exec("ls -l");
		host.exec("pwd");
		host.copyFromLocal(Paths.get("/root/notepad.exe"), Paths.get("C:/Windows/notepad.exe"));
		host.copyFromLocal(Paths.get("/root/notepad2.exe"), Paths.get("C:/Windows/notepad.exe"));
		host.exec("ls");
		host.copyToLocal(Paths.get("C:/Users/Mykhailo/notepad2.exe"), Paths.get("/root/notepad2.exe"));
		host.logout();
	}
}
