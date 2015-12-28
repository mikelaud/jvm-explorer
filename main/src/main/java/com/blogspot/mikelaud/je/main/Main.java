package com.blogspot.mikelaud.je.main;

import com.blogspot.mikelaud.je.ui.api.MvcView;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new MainModule());
		MvcView.Factory factory = injector.getInstance(MvcView.Factory.class);
		MvcView view = factory.create(args);
		view.show();
	}

}
