package com.blogspot.mikelaud.je.ui.program;

import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.code.UiCode;
import com.blogspot.mikelaud.je.ui.search.UiSearch;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UiProgramImpl implements UiProgram {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL;
	private final UiProgramConst CONST;
	private final UiSearch SEARCH;
	private final UiCode CODE;
	//
	private final SplitPane PANE;
	private final Scene SCENE;
	private final Stage STAGE;
	
	@Inject
	private UiProgramImpl
	(	MvcController aController
	,	UiProgramConst aConst
	,	UiSearch aSearch
	,	UiCode aCode
	,	@Assisted Stage aStage
	) {
		CONTROLLER = aController;
		MODEL = CONTROLLER.getModel();
		CONST = aConst;
		SEARCH = aSearch;
		CODE = aCode;
		//
		PANE = new SplitPane();
		SCENE = new Scene(PANE);
		STAGE = aStage;
		//
		buildePane();
	}
	
	private void buildePane() {
		PANE.getItems().addAll(SEARCH.getPane(), CODE.getPane());
		buildStage();
	}

	private void buildStage() {
		STAGE.setScene(SCENE);
		STAGE.setTitle(CONST.getProgramTitle());
		STAGE.getIcons().setAll(MODEL.getImage(CONST.getProgramIcon()));
		STAGE.fullScreenExitHintProperty().setValue(CONST.getEmptyHint());
		//
		Rectangle2D visualBounds = createVisualBounds();
		Rectangle2D defaultBounds = createDefaultBounds(visualBounds);
		//
		STAGE.setWidth(defaultBounds.getWidth());
		STAGE.setHeight(defaultBounds.getHeight());
		//
		STAGE.setMinWidth(STAGE.getWidth());
		STAGE.setMinHeight(STAGE.getHeight());
		//
		STAGE.setMaxWidth(visualBounds.getWidth());
		STAGE.setMaxHeight(visualBounds.getHeight());
	}

	private Rectangle2D createVisualBounds() {
		return Screen.getPrimary().getVisualBounds();
	}
	
	private Rectangle2D createDefaultBounds(Rectangle2D aVisualBounds) {
		double defaultWidth = aVisualBounds.getWidth() / CONST.getScaleWidth();
		double defaultHeight = aVisualBounds.getHeight() / CONST.getScaleHeight();
		return new Rectangle2D(0, 0, defaultWidth, defaultHeight);
	}	

	@Override
	public final SplitPane getPane() {
		return PANE;
	}

	@Override
	public final void show() {
		if (! STAGE.isShowing()) {
			STAGE.show();
			Platform.runLater(() -> CONTROLLER.getCore().setDefaultTypes());
		}
	}
	
}
