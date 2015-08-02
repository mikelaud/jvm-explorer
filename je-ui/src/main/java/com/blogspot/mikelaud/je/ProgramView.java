package com.blogspot.mikelaud.je;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ProgramView {

	private static interface Const {
		//
		String TITLE = "JVM Explorer";
		String PROGRAM_ICON = "program.png";
		String EMPTY_HINT = "";
		//
		double SCALE_WIDTH = 2;
		double SCALE_HEIGHT = 2;
	}
	
	private final SplitPane FORM;
	private final Scene SCENE;
	private final Stage WINDOW;
	//
	private final OpenTypeView OPEN_TYPE_VIEW;
	private final OpenMethodsView OPEN_METHODS_VIEW;

	private Image createIcon() {
		return new Image(Const.PROGRAM_ICON);
	}
	
	private Rectangle2D createVisualBounds() {
		return Screen.getPrimary().getVisualBounds();
	}
	
	private Rectangle2D createDefaultBounds(Rectangle2D aVisualBounds) {
		double defaultWidth = aVisualBounds.getWidth() / Const.SCALE_WIDTH;
		double defaultHeight = aVisualBounds.getHeight() / Const.SCALE_HEIGHT;
		return new Rectangle2D(0, 0, defaultWidth, defaultHeight);
	}
	
	private void buildWindow() {
		WINDOW.setScene(SCENE);
		WINDOW.setTitle(Const.TITLE);
		WINDOW.getIcons().setAll(createIcon());
		WINDOW.fullScreenExitHintProperty().setValue(Const.EMPTY_HINT);
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
		FORM.getItems().addAll(OPEN_TYPE_VIEW.getForm(), OPEN_METHODS_VIEW.getForm());
		buildWindow();
	}
	
	public void show() {
		if (! WINDOW.isShowing()) {
			WINDOW.show();
		}
	}
	
	public ProgramView(Stage aWindow) {
		FORM = new SplitPane();
		SCENE = new Scene(FORM);
		WINDOW = aWindow;
		//
		OPEN_TYPE_VIEW = new OpenTypeView();
		OPEN_METHODS_VIEW = new OpenMethodsView();
		buildForm();
	}
	
}
