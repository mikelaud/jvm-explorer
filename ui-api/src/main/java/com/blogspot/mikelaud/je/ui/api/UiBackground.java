package com.blogspot.mikelaud.je.ui.api;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public interface UiBackground {

	Pane getPane();
	//
	Image getImage();
	void setImage(Image aImage);
	
}
