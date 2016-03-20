package com.blogspot.mikelaud.je.main;

import com.blogspot.mikelaud.je.mvc.MvcController;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage aStage) throws Exception {
		Injector injector = Guice.createInjector(new MainModule());
		MvcController controller = injector.getInstance(MvcController.class);
		controller.getModel().setParameters(getParameters());
		controller.getModel().setStage(aStage);
		controller.showApplication();
	}

}
