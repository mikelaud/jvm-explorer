package com.blogspot.mikelaud.je.ui.background;

import com.blogspot.mikelaud.je.ui.UiBackground;
import com.google.inject.AbstractModule;

public class UiBackgroundModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UiBackground.class).to(UiBackgroundImpl.class);
	}

}
