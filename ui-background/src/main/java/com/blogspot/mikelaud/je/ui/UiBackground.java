package com.blogspot.mikelaud.je.ui;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public interface UiBackground {

	Pane getPane();
	//
	Image getImage();
	void setImage(Image aImage);
	
}
