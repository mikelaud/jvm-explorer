package com.blogspot.mikelaud.je.mvc;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class ViewModule extends AbstractModule {

	private void configureConst() {
		bindConstant().annotatedWith(ViewConst.ProgramTitle.class).to("JVM Explorer");
		bindConstant().annotatedWith(ViewConst.ProgramIcon.class).to("program.png");
		bindConstant().annotatedWith(ViewConst.EmptyHint.class).to("");
		bindConstant().annotatedWith(ViewConst.ScaleWidth.class).to(2.0d);
		bindConstant().annotatedWith(ViewConst.ScaleHeight.class).to(2.0d);
		//
		bind(ViewConst.class).to(ViewConstImpl.class).in(Singleton.class);		
	}
	
	@Override
	protected void configure() {
		configureConst();
		//
		install(new FactoryModuleBuilder()
			.implement(View.class, ViewImpl.class)
			.build(ViewFactory.class));
		//
		bind(ViewContext.class).to(ViewContextImpl.class).in(Singleton.class);
		bind(ViewUtils.class).to(ViewUtilsImpl.class).in(Singleton.class);
		//
		install(new ControllerModule());
	}

}
