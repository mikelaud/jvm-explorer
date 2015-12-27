package com.blogspot.mikelaud.je.mvc.search;

import java.nio.file.Paths;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import javafx.scene.image.Image;

public class VSearchModule extends AbstractModule {

	private void configureConst() {
		//
		bind(Image.class).annotatedWith(VSearchConst.BackgroundImage.class).toInstance(new Image(Paths.get("background", "type.jpg").toString()));
		bind(Image.class).annotatedWith(VSearchConst.PackageIcon.class).toInstance(new Image(Paths.get("library.png").toString()));
		//
		bindConstant().annotatedWith(VSearchConst.SearchLabel.class).to("Enter type name prefix or pattern (*, ?, or camel case):");
		bindConstant().annotatedWith(VSearchConst.MatchingLabel.class).to("Matching items:");
		bindConstant().annotatedWith(VSearchConst.CountLabel.class).to(" of ");
		//
		bindConstant().annotatedWith(VSearchConst.Spacing.class).to(5);
		bindConstant().annotatedWith(VSearchConst.Padding.class).to(10);
		//
		//--------------------------------------------------------------------
		bind(VSearchConst.class).to(VSearchConstImpl.class).in(Singleton.class);
	}
	
	@Override
	protected void configure() {
		configureConst();
		//
		bind(VSearch.class).to(VSearchImpl.class).in(Singleton.class);
	}

}
