package com.blogspot.mikelaud.je.agent.loader;

import com.blogspot.mikelaud.je.agent.loader.common.AgentSource;
import com.blogspot.mikelaud.je.agent.loader.common.AgentSourceImpl;
import com.blogspot.mikelaud.je.agent.loader.common.LocalAgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.LocalAgentLoaderImpl;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteAgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteAgentLoaderSsh;
import com.blogspot.mikelaud.je.agent.loader.source.FileSource;
import com.blogspot.mikelaud.je.agent.loader.source.FileSourceFactory;
import com.blogspot.mikelaud.je.agent.loader.source.FileSourceImpl;
import com.blogspot.mikelaud.je.agent.loader.source.FileSourceJar;
import com.blogspot.mikelaud.je.agent.loader.source.FileSourceJarImpl;
import com.blogspot.mikelaud.je.agent.loader.source.content.FileContent;
import com.blogspot.mikelaud.je.agent.loader.source.content.FileContentFactory;
import com.blogspot.mikelaud.je.agent.loader.source.content.FileContentImpl;
import com.blogspot.mikelaud.je.agent.loader.source.content.FileContentJar;
import com.blogspot.mikelaud.je.agent.loader.source.content.FileContentJarImpl;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class AgentLoaderModule extends AbstractModule {

	@Override
	protected final void configure() {
		bind(AgentSource.class).to(AgentSourceImpl.class);
		//
		install(new FactoryModuleBuilder()
			.implement(LocalAgentLoader.class, LocalAgentLoaderImpl.class)
			.implement(RemoteAgentLoader.class, RemoteAgentLoaderSsh.class)
		.build(AgentLoaderFactory.class));
		//
		install(new FactoryModuleBuilder()
			.implement(FileSource.class, FileSourceImpl.class)
			.implement(FileSourceJar.class, FileSourceJarImpl.class)
		.build(FileSourceFactory.class));
		//
		install(new FactoryModuleBuilder()
			.implement(FileContent.class, FileContentImpl.class)
			.implement(FileContentJar.class, FileContentJarImpl.class)
		.build(FileContentFactory.class));
	}

}
