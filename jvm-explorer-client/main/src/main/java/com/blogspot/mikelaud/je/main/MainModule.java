package com.blogspot.mikelaud.je.main;

import com.blogspot.mikelaud.je.agent.loader.AgentLoaderModule;
import com.blogspot.mikelaud.je.common.CommonModule;
import com.blogspot.mikelaud.je.core.CoreModule;
import com.blogspot.mikelaud.je.domain.DomainModule;
import com.blogspot.mikelaud.je.mvc.impl.MvcModule;
import com.blogspot.mikelaud.je.ssh.SshModule;
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
		install(new CommonModule());
		install(new SshModule());
		install(new AgentLoaderModule());
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
