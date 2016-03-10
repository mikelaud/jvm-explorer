package com.blogspot.mikelaud.je.ui.jvm;

import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.background.UiBackground;
import com.google.inject.Inject;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class UiJvmImpl implements UiJvm {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL;
	private final UiJvmConst CONST;
	private final UiBackground BACKGROUND;
	//
	private final BorderPane PANE;
	private final ScrollPane JVM_PANE;

	@Inject
	private UiJvmImpl
	(	MvcController aController
	,	UiJvmConst aConst
	,	UiBackground aBackground
	) {
		CONTROLLER = aController;
		MODEL = CONTROLLER.getModel();
		CONST = aConst;
		BACKGROUND = aBackground;
		//
		PANE = new BorderPane();
		JVM_PANE = new ScrollPane();
		//
		buildForm();
	}

	private Node createCenter() {
		PANE.setPadding(new Insets(CONST.getSpacing(), CONST.getSpacing(), CONST.getSpacing(), CONST.getSpacing()));
		//PANE.setContent();
		PANE.setVisible(true);
		//
		BACKGROUND.setImage(MODEL.getImage(CONST.getBackgroundImage()));
		//BACKGROUND.getPane().getChildren().add(JVM_PANE);
		return BACKGROUND.getPane();
	}

	private void buildForm() {
		PANE.setCenter(createCenter());
		//
		PANE.setPadding(new Insets(CONST.getPadding(), CONST.getPadding(), CONST.getPadding(), CONST.getPadding()));
	}

	@Override
	public Pane getPane() {
		return PANE;
	}

}
