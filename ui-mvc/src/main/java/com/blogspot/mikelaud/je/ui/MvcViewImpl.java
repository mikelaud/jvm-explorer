package com.blogspot.mikelaud.je.ui;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javafx.application.Application;
import javafx.stage.Stage;

public class MvcViewImpl extends Application implements MvcView {

	private final UiProgram.Factory UI_PROGRAM_FACTORY;
	private final String[] ARGS;
	
	@Inject
	private MvcViewImpl
	(	UiProgram.Factory aUiProgramFactory
	,	@Assisted String[] args
	) {
		UI_PROGRAM_FACTORY = aUiProgramFactory;
		ARGS = args;
	}

	@Override
	public void start(Stage aStage) throws Exception {
		UiProgram uiProgram = UI_PROGRAM_FACTORY.create(ARGS, aStage);
		uiProgram.show();
	}

	@Override
	public void show() {
		Application.launch(ARGS);
	}
	
}
