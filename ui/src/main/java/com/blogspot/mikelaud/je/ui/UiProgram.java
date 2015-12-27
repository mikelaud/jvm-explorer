package com.blogspot.mikelaud.je.ui;

import javafx.scene.layout.Pane;

public interface UiProgram {

	interface Factory {
		UiProgram create(MvcController aMvcController); 
	}

	Pane getPane();

}
