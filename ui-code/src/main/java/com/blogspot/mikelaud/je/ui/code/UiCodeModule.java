package com.blogspot.mikelaud.je.ui.code;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class UiCodeModule extends AbstractModule {

	@Override
	protected final void configure() {
		bind(UiCode.class).to(UiCodeImpl.class).in(Singleton.class);		
	}

}
