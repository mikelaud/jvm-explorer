package com.blogspot.mikelaud.je.mvc.impl;

import com.blogspot.mikelaud.je.mvc.MvcConst;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.mvc.MvcView;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class MvcModule extends AbstractModule {

	private void configureConst() {
		bind(MvcConst.class).to(MvcConstImpl.class).in(Singleton.class);
		//
		bindConstant().annotatedWith(MvcConst.Padding.class).to(10);
	}

	@Override
	protected final void configure() {
		configureConst();
		//
		bind(MvcModel.class).to(MvcModelImpl.class).in(Singleton.class);
		bind(MvcView.class).to(MvcViewImpl.class).in(Singleton.class);
		bind(MvcController.class).to(MvcControllerImpl.class).in(Singleton.class);
	}

}
