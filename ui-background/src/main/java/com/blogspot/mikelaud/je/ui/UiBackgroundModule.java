package com.blogspot.mikelaud.je.ui;

import com.google.inject.AbstractModule;

public class UiBackgroundModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UiBackground.class).to(UiBackgroundImpl.class);
	}

}
