package com.blogspot.mikelaud.je.mvc.impl;

import com.blogspot.mikelaud.je.mvc.MvcView;
import com.blogspot.mikelaud.je.ui.program.UiProgram;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javafx.application.Application;
import javafx.stage.Stage;

public class MvcViewImpl extends Application implements MvcView {

	private final UiProgram.Factory PROGRAM_FACTORY;
	private final String[] ARGS;
	
	@Inject
	private MvcViewImpl
	(	UiProgram.Factory aUiProgramFactory
	,	@Assisted String[] args
	) {
		PROGRAM_FACTORY = aUiProgramFactory;
		ARGS = args;
	}

	@Override
	public final void start(Stage aStage) throws Exception {
		UiProgram uiProgram = PROGRAM_FACTORY.create(ARGS, aStage);
		uiProgram.show();
	}

	@Override
	public final void show() {
		Application.launch(ARGS);
	}
	
}
