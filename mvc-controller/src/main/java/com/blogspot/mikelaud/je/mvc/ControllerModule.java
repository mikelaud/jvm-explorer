package com.blogspot.mikelaud.je.mvc;

import com.google.inject.AbstractModule;

public class ControllerModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new ModelModule());
		bind(Controller.class).to(ControllerImpl.class);
	}

}
