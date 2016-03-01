package com.blogspot.mikelaud.je.agent.loader;

import com.blogspot.mikelaud.je.agent.loader.common.AgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.AgentLoaderImpl;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteLoader;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteLoaderFactory;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteLoaderSsh;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class AgentLoaderModule extends AbstractModule {

	@Override
	protected final void configure() {
		//
		install(new FactoryModuleBuilder()
			.implement(AgentLoader.class, AgentLoaderImpl.class)
			.build(AgentLoaderFactory.class));
		//
		install(new FactoryModuleBuilder()
			.implement(RemoteLoader.class, RemoteLoaderSsh.class)
			.build(RemoteLoaderFactory.class));
	}

}
