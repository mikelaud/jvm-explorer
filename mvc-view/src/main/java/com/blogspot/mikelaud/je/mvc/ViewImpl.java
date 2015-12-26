package com.blogspot.mikelaud.je.mvc;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ViewImpl implements View {

	private final ViewConst CONST;
	@SuppressWarnings("unused")
	private final Model MODEL;
	@SuppressWarnings("unused")
	private final Controller CONTROLLER;
	//
	private final SplitPane FORM;
	private final Scene SCENE;
	private final Stage WINDOW;
	
	@Inject
	private ViewImpl
	(	ViewConst aConst
	,	Model aModel
	,	Controller aController
	,	@Assisted Stage aStage
	) {
		CONST = aConst;
		MODEL = aModel;
		CONTROLLER = aController;
		//
		FORM = new SplitPane();
		SCENE = new Scene(FORM);
		WINDOW = aStage;
		//
		buildForm();
	}
	
	//
	//private final OpenMethodView OPEN_METHODS_VIEW;
	//private final OpenTypeView OPEN_TYPE_VIEW;

	private Image createIcon() {
		return new Image(CONST.getProgramIcon());
	}
	
	private Rectangle2D createVisualBounds() {
		return Screen.getPrimary().getVisualBounds();
	}
	
	private Rectangle2D createDefaultBounds(Rectangle2D aVisualBounds) {
		double defaultWidth = aVisualBounds.getWidth() / CONST.getScaleWidth();
		double defaultHeight = aVisualBounds.getHeight() / CONST.getScaleHeight();
		return new Rectangle2D(0, 0, defaultWidth, defaultHeight);
	}
	
	private void buildWindow() {
		WINDOW.setScene(SCENE);
		WINDOW.setTitle(CONST.getProgramTitle());
		WINDOW.getIcons().setAll(createIcon());
		WINDOW.fullScreenExitHintProperty().setValue(CONST.getEmptyHint());
		//
		Rectangle2D visualBounds = createVisualBounds();
		Rectangle2D defaultBounds = createDefaultBounds(visualBounds);
		//
		WINDOW.setWidth(defaultBounds.getWidth());
		WINDOW.setHeight(defaultBounds.getHeight());
		//
		WINDOW.setMinWidth(WINDOW.getWidth());
		WINDOW.setMinHeight(WINDOW.getHeight());
		//
		WINDOW.setMaxWidth(visualBounds.getWidth());
		WINDOW.setMaxHeight(visualBounds.getHeight());
	}
	
	private void buildForm() {
		//FORM.getItems().addAll(OPEN_TYPE_VIEW.getForm(), OPEN_METHODS_VIEW.getForm());
		buildWindow();
	}
	
	@Override
	public void show() {
		if (! WINDOW.isShowing()) {
			WINDOW.show();
		}
	}
	
}
