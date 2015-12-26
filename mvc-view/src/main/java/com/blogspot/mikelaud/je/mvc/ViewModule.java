package com.blogspot.mikelaud.je.mvc;

import com.google.inject.AbstractModule;

public class ViewModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new ControllerModule());
		bind(View.class).to(ViewImpl.class);
	}

}
