package com.blogspot.mikelaud.je.ui.program;

import java.nio.file.Paths;

import com.blogspot.mikelaud.je.core.CoreModule;
import com.blogspot.mikelaud.je.mvc.search.VSearchModule;
import com.blogspot.mikelaud.je.ui.background.UiBackgroundModule;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import javafx.scene.image.Image;

public class ViewModule extends AbstractModule {

	private void configureConst() {
		bindConstant().annotatedWith(ViewConst.ProgramTitle.class).to("JVM Explorer");
		bind(Image.class).annotatedWith(ViewConst.ProgramIcon.class).toInstance(new Image(Paths.get("program.png").toString()));
		//
		bindConstant().annotatedWith(ViewConst.ScaleWidth.class).to(4.0d);
		bindConstant().annotatedWith(ViewConst.ScaleHeight.class).to(2.0d);
		//
		bindConstant().annotatedWith(ViewConst.EmptyHint.class).to("");
		//
		//--------------------------------------------------------------------
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
		//
		install(new CoreModule());
		install(new UiBackgroundModule());
		install(new VSearchModule());
	}

}
