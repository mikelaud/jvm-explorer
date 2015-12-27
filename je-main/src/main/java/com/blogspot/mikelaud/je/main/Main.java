package com.blogspot.mikelaud.je.main;

import com.blogspot.mikelaud.je.mvc.View;
import com.blogspot.mikelaud.je.mvc.ViewFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage aStage) throws Exception {
		Injector injector = Guice.createInjector(new MainModule());
		ViewFactory viewFactory = injector.getInstance(ViewFactory.class);
		View view = viewFactory.create(aStage);
		view.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
