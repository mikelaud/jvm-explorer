package com.blogspot.mikelaud.je.ui.program;

import com.blogspot.mikelaud.je.domain.pojo.Type;

import javafx.scene.control.SplitPane;

public interface UiProgram {

	SplitPane getPane();
	void show();
	void showCode(Type aType);

}
