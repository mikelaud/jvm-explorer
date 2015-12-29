package com.blogspot.mikelaud.je.ui.code;

import com.blogspot.mikelaud.je.mvc.MvcController;
import com.google.inject.Inject;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class UiCodeImpl implements UiCode {

	@SuppressWarnings("unused")
	private final MvcController CONTROLLER;
	private final BorderPane PANE;
	
	@Inject
	private UiCodeImpl(MvcController aController) {
		CONTROLLER = aController;
		PANE = new BorderPane();
	}
	
	@Override
	public final Pane getPane() {
		return PANE;
	}

}
