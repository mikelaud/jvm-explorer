package com.blogspot.mikelaud.je.ssh;

import java.nio.file.Paths;

import com.blogspot.mikelaud.je.ssh.hosts.Host;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

	public static void main(String[] arg) {
		Injector injector = Guice.createInjector(new SshModule());
		SshFactory sshFactory = injector.getInstance(SshFactory.class);
		//
		Host host = sshFactory.newHost("192.168.10.101", 22);
		host.open("root", "xxx");
		host.exec("ls -l");
		host.exec("pwd");
		host.copyFromLocal(Paths.get("C:/Windows/notepad.exe"), Paths.get("/root/notepad.exe"));
		host.copyFromLocal(Paths.get("C:/Windows/notepad.exe"), Paths.get("/root/notepad2.exe"));
		host.exec("ls");
		host.copyToLocal(Paths.get("/root/notepad2.exe"), Paths.get("C:/Users/Mykhailo/notepad2.exe"));
		host.close();
	}
}
