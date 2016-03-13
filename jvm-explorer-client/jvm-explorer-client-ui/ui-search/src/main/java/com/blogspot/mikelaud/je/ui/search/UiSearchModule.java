package com.blogspot.mikelaud.je.ui.search;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class UiSearchModule extends AbstractModule {

	private void configureConst() {
		bind(UiSearchConst.class).to(UiSearchConstImpl.class).in(Singleton.class);
		//
		bindConstant().annotatedWith(UiSearchConst.Name.class).to("Type");
		bind(Path.class).annotatedWith(UiSearchConst.BackgroundImage.class).toInstance(Paths.get("background", "search.jpg"));
		bind(Path.class).annotatedWith(UiSearchConst.PackageIcon.class).toInstance(Paths.get("library.png"));
		//
		bindConstant().annotatedWith(UiSearchConst.SearchLabel.class).to("Enter type name prefix or pattern (*, ?, or camel case):");
		bindConstant().annotatedWith(UiSearchConst.MatchingLabel.class).to("Matching items:");
		bindConstant().annotatedWith(UiSearchConst.CountLabel.class).to(" of ");
	}

	@Override
	protected final void configure() {
		configureConst();
		//
		bind(UiSearch.class).to(UiSearchImpl.class).in(Singleton.class);
	}

}
