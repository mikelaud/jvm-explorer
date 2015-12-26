package com.blogspot.mikelaud.je.mvc;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ViewImpl implements View {

	private final ViewContext CONTEXT;
	private final ViewConst CONST;
	//
	private final SplitPane PANE;
	private final Scene SCENE;
	private final Stage STAGE;
	
	@Inject
	private ViewImpl
	(	ViewContext aContext
	,	ViewConst aConst
	,	@Assisted Stage aStage
	) {
		CONTEXT = aContext;
		CONST = aConst;
		//
		PANE = new SplitPane();
		SCENE = new Scene(PANE);
		STAGE = aStage;
		//
		buildPane();
	}
	
	//private final OpenMethodView OPEN_METHODS_VIEW;
	//private final OpenTypeView OPEN_TYPE_VIEW;

	private Rectangle2D createVisualBounds() {
		return Screen.getPrimary().getVisualBounds();
	}
	
	private Rectangle2D createDefaultBounds(Rectangle2D aVisualBounds) {
		double defaultWidth = aVisualBounds.getWidth() / CONST.getScaleWidth();
		double defaultHeight = aVisualBounds.getHeight() / CONST.getScaleHeight();
		return new Rectangle2D(0, 0, defaultWidth, defaultHeight);
	}
	
	private void buildStage() {
		STAGE.setScene(SCENE);
		STAGE.setTitle(CONST.getProgramTitle());
		STAGE.getIcons().setAll(CONTEXT.getUtils().createImage(CONST.getProgramIcon()));
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
	
	private void buildPane() {
		//FORM.getItems().addAll(OPEN_TYPE_VIEW.getForm(), OPEN_METHODS_VIEW.getForm());
		buildStage();
	}
	
	@Override
	public void show() {
		if (! STAGE.isShowing()) {
			STAGE.show();
		}
	}
	
}
