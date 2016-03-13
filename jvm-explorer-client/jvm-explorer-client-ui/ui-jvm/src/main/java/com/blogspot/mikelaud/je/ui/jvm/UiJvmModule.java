package com.blogspot.mikelaud.je.ui.jvm;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class UiJvmModule extends AbstractModule {

	private void configureConst() {
		//
		bindConstant().annotatedWith(UiJvmConst.Name.class).to("JVM");
		bind(Path.class).annotatedWith(UiJvmConst.BackgroundImage.class).toInstance(Paths.get("background", "jvm.jpg"));
		//
		bindConstant().annotatedWith(UiJvmConst.Spacing.class).to(0);
		bindConstant().annotatedWith(UiJvmConst.Padding.class).to(0);
		//
		//--------------------------------------------------------------------
		bind(UiJvmConst.class).to(UiJvmConstImpl.class).in(Singleton.class);
	}

	@Override
	protected void configure() {
		configureConst();
		//
		bind(UiJvm.class).to(UiJvmImpl.class).in(Singleton.class);
	}

}
