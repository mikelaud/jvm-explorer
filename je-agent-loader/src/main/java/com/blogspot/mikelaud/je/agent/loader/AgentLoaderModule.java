package com.blogspot.mikelaud.je.agent.loader;

import com.blogspot.mikelaud.je.agent.loader.common.AgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.AgentLoaderImpl;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class AgentLoaderModule extends AbstractModule {

	@Override
	protected final void configure() {
		//
		install(new FactoryModuleBuilder()
			.implement(AgentLoader.class, AgentLoaderImpl.class)
			.build(AgentLoaderFactory.class));
	}

}
