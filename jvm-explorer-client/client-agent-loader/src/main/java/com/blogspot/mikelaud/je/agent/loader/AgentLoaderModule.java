package com.blogspot.mikelaud.je.agent.loader;

import com.blogspot.mikelaud.je.agent.loader.common.LocalAgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.LocalAgentLoaderImpl;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteAgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteAgentLoaderSsh;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class AgentLoaderModule extends AbstractModule {

	@Override
	protected final void configure() {
		//
		install(new FactoryModuleBuilder()
			.implement(LocalAgentLoader.class, LocalAgentLoaderImpl.class)
			.implement(RemoteAgentLoader.class, RemoteAgentLoaderSsh.class)
		.build(AgentLoaderFactory.class));
	}

}
