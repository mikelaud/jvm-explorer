package com.blogspot.mikelaud.je;

import com.blogspot.mikelaud.je.ui.mvc.MvcModule;
import com.google.inject.AbstractModule;

public class MainModule extends AbstractModule {

	@Override
	protected final void configure() {
		install(new MvcModule());
	}

}
