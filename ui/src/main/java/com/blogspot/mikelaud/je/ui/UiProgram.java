package com.blogspot.mikelaud.je.ui;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public interface UiProgram {

	interface Factory {
		UiProgram create(String[] args, Stage aStage); 
	}

	Pane getPane();
	void show();

}
