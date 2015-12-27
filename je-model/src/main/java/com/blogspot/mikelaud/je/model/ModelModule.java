package com.blogspot.mikelaud.je.model;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ModelModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Model.class).to(ModelImpl.class).in(Singleton.class);
	}

}
