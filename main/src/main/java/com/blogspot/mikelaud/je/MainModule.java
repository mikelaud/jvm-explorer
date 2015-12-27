package com.blogspot.mikelaud.je;

import com.blogspot.mikelaud.je.ui.MvcModule;
import com.google.inject.AbstractModule;

public class MainModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new MvcModule());
	}

}
