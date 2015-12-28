package com.blogspot.mikelaud.je.core;

import com.blogspot.mikelaud.je.domain.DomainModule;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class CoreModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new DomainModule());
		bind(Core.class).to(CoreImpl.class).in(Singleton.class);
	}

}
