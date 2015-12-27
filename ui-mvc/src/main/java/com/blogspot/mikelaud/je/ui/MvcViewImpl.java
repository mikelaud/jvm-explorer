package com.blogspot.mikelaud.je.ui;

import com.google.inject.Inject;

import javafx.application.Application;
import javafx.stage.Stage;

public class MvcViewImpl extends Application implements MvcView {

	private final String[] ARGS;
	
	@Inject
	private MvcViewImpl(String[] args) {
		ARGS = args;
	}

	@Override
	public void start(Stage aStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Application.launch(ARGS);
	}
	
}
