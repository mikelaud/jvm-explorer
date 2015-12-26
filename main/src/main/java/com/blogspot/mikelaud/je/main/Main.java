package com.blogspot.mikelaud.je.main;

import com.blogspot.mikelaud.je.mvc.View;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new MainModule());
		injector.getInstance(View.class);
	}

}
