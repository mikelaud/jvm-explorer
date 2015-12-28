package com.blogspot.mikelaud.je.core;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class CoreModule extends AbstractModule {

	@Override
	protected final void configure() {
		bind(Core.class).to(CoreImpl.class).in(Singleton.class);
	}

}
