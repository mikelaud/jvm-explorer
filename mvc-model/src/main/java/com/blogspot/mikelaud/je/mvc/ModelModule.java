package com.blogspot.mikelaud.je.mvc;

import com.google.inject.AbstractModule;

public class ModelModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Model.class).to(ModelImpl.class);
	}

}
