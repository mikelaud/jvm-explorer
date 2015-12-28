package com.blogspot.mikelaud.je.ui.mvc;

import com.blogspot.mikelaud.je.ui.MvcController;
import com.blogspot.mikelaud.je.ui.MvcModel;
import com.blogspot.mikelaud.je.ui.MvcView;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class MvcModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MvcController.class).to(MvcControllerImpl.class).in(Singleton.class);
		bind(MvcModel.class).to(MvcModelImpl.class).in(Singleton.class);
		//
		install(new FactoryModuleBuilder()
			.implement(MvcView.class, MvcViewImpl.class)
			.build(MvcView.Factory.class));
	}

}
