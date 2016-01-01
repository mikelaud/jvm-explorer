package com.blogspot.mikelaud.je.ui.code;

import com.blogspot.mikelaud.je.domain.pojo.DomainType;

import javafx.scene.layout.Pane;

public interface UiCode {

	Pane getPane();
	void setType(DomainType aType);

}
