package com.blogspot.mikelaud.je.main;

import com.blogspot.mikelaud.je.agent.bios.AgentBiosModule;
import com.blogspot.mikelaud.je.core.CoreModule;
import com.blogspot.mikelaud.je.domain.DomainModule;
import com.blogspot.mikelaud.je.mvc.impl.MvcModule;
import com.blogspot.mikelaud.je.ui.background.UiBackgroundModule;
import com.blogspot.mikelaud.je.ui.code.UiCodeModule;
import com.blogspot.mikelaud.je.ui.program.UiProgramModule;
import com.blogspot.mikelaud.je.ui.resources.UiResourcesModule;
import com.blogspot.mikelaud.je.ui.search.UiSearchModule;
import com.google.inject.AbstractModule;

public class MainModule extends AbstractModule {

	@Override
	protected final void configure() {
		//
		install(new AgentBiosModule());
		install(new DomainModule());
		install(new CoreModule());
		//
		install(new UiResourcesModule());
		install(new UiBackgroundModule());
		install(new UiSearchModule());
		install(new UiCodeModule());
		install(new UiProgramModule());
		//
		install(new MvcModule());
	}

}
