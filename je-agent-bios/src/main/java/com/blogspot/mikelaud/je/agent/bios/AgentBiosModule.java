package com.blogspot.mikelaud.je.agent.bios;

import com.blogspot.mikelaud.je.agent.bios.common.AgentBios;
import com.blogspot.mikelaud.je.agent.bios.common.AgentBiosImpl;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class AgentBiosModule extends AbstractModule {

	@Override
	protected final void configure() {
		install(new FactoryModuleBuilder()
			.implement(AgentBios.class, AgentBiosImpl.class)
			.build(AgentBiosFactory.class));
	}

}
