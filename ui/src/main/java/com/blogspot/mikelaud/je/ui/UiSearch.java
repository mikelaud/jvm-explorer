package com.blogspot.mikelaud.je.ui;

import javafx.scene.layout.Pane;

public interface UiSearch {

	interface Factory {
		UiSearch create(MvcController aMvcController); 
	}

	Pane getPane();
	
}
