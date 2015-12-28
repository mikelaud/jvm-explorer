package com.blogspot.mikelaud.je.ui.program;

import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

public interface UiProgram {

	interface Factory {
		UiProgram create(String[] args, Stage aStage); 
	}

	SplitPane getPane();
	void show();

}
