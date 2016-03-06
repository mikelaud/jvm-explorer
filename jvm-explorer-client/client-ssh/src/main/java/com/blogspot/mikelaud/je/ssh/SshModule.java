package com.blogspot.mikelaud.je.ssh;

import com.blogspot.mikelaud.je.ssh.hosts.Host;
import com.blogspot.mikelaud.je.ssh.hosts.UnixHost;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class SshModule extends AbstractModule {

	@Override
	protected final void configure() {
		install(new FactoryModuleBuilder()
			.implement(Host.class, UnixHost.class)
			.build(SshFactory.class));
	}

}
