package com.blogspot.mikelaud.je.ui.mvc;

import com.blogspot.mikelaud.je.ui.api.MvcController;
import com.blogspot.mikelaud.je.ui.api.MvcModel;
import com.blogspot.mikelaud.je.ui.api.MvcView;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class MvcModule extends AbstractModule {

	@Override
	protected final void configure() {
		bind(MvcController.class).to(MvcControllerImpl.class).in(Singleton.class);
		bind(MvcModel.class).to(MvcModelImpl.class).in(Singleton.class);
		//
		install(new FactoryModuleBuilder()
			.implement(MvcView.class, MvcViewImpl.class)
			.build(MvcView.Factory.class));
	}

}
