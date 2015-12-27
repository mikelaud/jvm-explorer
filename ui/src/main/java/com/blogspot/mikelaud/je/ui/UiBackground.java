package com.blogspot.mikelaud.je.ui;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public interface UiBackground {

	interface Factory {
		UiBackground create(MvcController aMvcController); 
	}
	
	Pane getPane();
	//
	Image getImage();
	void setImage(Image aImage);
	
}
