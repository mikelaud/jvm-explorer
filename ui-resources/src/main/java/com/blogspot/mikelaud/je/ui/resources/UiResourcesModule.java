package com.blogspot.mikelaud.je.ui.resources;

import com.google.inject.AbstractModule;

public class UiResourcesModule extends AbstractModule {

	@Override
	protected final void configure() {
		bind(UiResources.class).to(UiResourcesImpl.class);
	}

}
