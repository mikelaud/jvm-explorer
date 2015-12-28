package com.blogspot.mikelaud.je.ui.background;

import com.google.inject.AbstractModule;

public class UiBackgroundModule extends AbstractModule {

	@Override
	protected final void configure() {
		bind(UiBackground.class).to(UiBackgroundImpl.class);
	}

}
