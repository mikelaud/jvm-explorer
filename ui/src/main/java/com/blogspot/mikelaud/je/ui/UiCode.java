package com.blogspot.mikelaud.je.ui;

import javafx.scene.layout.Pane;

public interface UiCode {

	interface Factory {
		UiCode create(MvcController aMvcController); 
	}

	Pane getPane();

}
