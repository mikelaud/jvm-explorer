package com.blogspot.mikelaud.je.core;

import com.blogspot.mikelaud.je.model.ModelModule;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class CoreModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new ModelModule());
		bind(Core.class).to(CoreImpl.class).in(Singleton.class);
	}

}
