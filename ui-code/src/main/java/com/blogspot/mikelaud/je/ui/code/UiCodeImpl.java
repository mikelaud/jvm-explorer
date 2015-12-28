package com.blogspot.mikelaud.je.ui.code;

import com.google.inject.Inject;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class UiCodeImpl implements UiCode {

	private final BorderPane PANE;
	
	@Inject
	private UiCodeImpl() {
		PANE = new BorderPane();
	}
	
	@Override
	public final Pane getPane() {
		return PANE;
	}

}
