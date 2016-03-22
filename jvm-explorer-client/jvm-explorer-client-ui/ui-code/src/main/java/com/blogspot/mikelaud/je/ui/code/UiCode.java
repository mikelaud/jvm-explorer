package com.blogspot.mikelaud.je.ui.code;

import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.ui.background.UiBackground;

import javafx.scene.layout.Pane;

public interface UiCode {

	Pane getPane();
	UiBackground getBackground();

	void setType(DomainType aType);

}
