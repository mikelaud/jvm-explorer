package com.blogspot.mikelaud.je;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ProgramView {

	private final String TITLE;
	private final String ICON_FILENAME;
	private final double SCALE_WIDTH;
	private final double SCALE_HEIGHT;
	//
	private BorderPane mPane;
	private Scene mScene;
	private Stage mWindow;

	private Image createIcon() {
		final String iconUrl = "/" + ICON_FILENAME;
		return new Image(iconUrl);
	}
	
	private Rectangle2D createVisualBounds() {
		return Screen.getPrimary().getVisualBounds();
	}
	
	private Rectangle2D createDefaultBounds(Rectangle2D aVisualBounds) {
		double defaultWidth = aVisualBounds.getWidth() / SCALE_WIDTH;
		double defaultHeight = aVisualBounds.getHeight() / SCALE_HEIGHT;
		return new Rectangle2D(0, 0, defaultWidth, defaultHeight);
	}
	
	private void initWindow() {
		mWindow.setScene(mScene);
		mWindow.setTitle(TITLE);
		mWindow.getIcons().setAll(createIcon());
		mWindow.fullScreenExitHintProperty().setValue("");
		//
		Rectangle2D visualBounds = createVisualBounds();
		Rectangle2D defaultBounds = createDefaultBounds(visualBounds);
		//
		mWindow.setWidth(defaultBounds.getWidth());
		mWindow.setHeight(defaultBounds.getHeight());
		//
		mWindow.setMinWidth(mWindow.getWidth());
		mWindow.setMinHeight(mWindow.getHeight());
		//
		mWindow.setMaxWidth(visualBounds.getWidth());
		mWindow.setMaxHeight(visualBounds.getHeight());
	}
	
	public void show() {
		if (! mWindow.isShowing()) {
			initWindow();
			mWindow.show();
		}
	}
	
	public ProgramView(Stage aWindow) {
		TITLE = "JVM Explorer";
		ICON_FILENAME = "Program.png";
		SCALE_WIDTH = 3;
		SCALE_HEIGHT = 2;
		//
		mPane = new BorderPane();
		mScene = new Scene(mPane);
		mWindow = aWindow;
	}
	
}
