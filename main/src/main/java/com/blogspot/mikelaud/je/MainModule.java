package com.blogspot.mikelaud.je;

import com.blogspot.mikelaud.je.core.CoreModule;
import com.blogspot.mikelaud.je.domain.DomainModule;
import com.blogspot.mikelaud.je.ui.background.UiBackgroundModule;
import com.blogspot.mikelaud.je.ui.mvc.MvcModule;
import com.blogspot.mikelaud.je.ui.program.UiProgramModule;
import com.blogspot.mikelaud.je.ui.search.UiSearchModule;
import com.google.inject.AbstractModule;

public class MainModule extends AbstractModule {

	@Override
	protected final void configure() {
		//
		install(new DomainModule());
		install(new CoreModule());
		//
		install(new UiBackgroundModule());
		install(new UiSearchModule());
		install(new UiProgramModule());
		//
		install(new MvcModule());
	}

}
