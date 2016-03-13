package com.blogspot.mikelaud.je.ui.code;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class UiCodeModule extends AbstractModule {

	private void configureConst() {
		bind(UiCodeConst.class).to(UiCodeConstImpl.class).in(Singleton.class);
		//
		bind(Path.class).annotatedWith(UiCodeConst.BackgroundImage.class).toInstance(Paths.get("background", "code.jpg"));
	}

	@Override
	protected final void configure() {
		configureConst();
		//
		bind(UiCode.class).to(UiCodeImpl.class).in(Singleton.class);
	}

}
