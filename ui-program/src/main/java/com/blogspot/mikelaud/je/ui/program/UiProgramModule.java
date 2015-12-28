package com.blogspot.mikelaud.je.ui.program;

import java.nio.file.Paths;

import com.blogspot.mikelaud.je.ui.UiProgram;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import javafx.scene.image.Image;

public class UiProgramModule extends AbstractModule {

	private void configureConst() {
		bindConstant().annotatedWith(UiProgramConst.ProgramTitle.class).to("JVM Explorer");
		bind(Image.class).annotatedWith(UiProgramConst.ProgramIcon.class).toInstance(new Image(Paths.get("program.png").toString()));
		//
		bindConstant().annotatedWith(UiProgramConst.ScaleWidth.class).to(4.0d);
		bindConstant().annotatedWith(UiProgramConst.ScaleHeight.class).to(2.0d);
		//
		bindConstant().annotatedWith(UiProgramConst.EmptyHint.class).to("");
		//
		//--------------------------------------------------------------------
		bind(UiProgramConst.class).to(UiProgramConstImpl.class).in(Singleton.class);
	}
	
	@Override
	protected void configure() {
		configureConst();
		//
		install(new FactoryModuleBuilder()
			.implement(UiProgram.class, UiProgramImpl.class)
			.build(UiProgram.Factory.class));
	}

}
