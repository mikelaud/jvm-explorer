package com.blogspot.mikelaud.je.common;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class MethodListCell extends ListCell<Method> {

	private final HBox BOX;
	//
	private final Label LABEL_METHOD;
	
	@Override
	protected void updateItem(Method aMethod, boolean aEmpty) {
		super.updateItem(aMethod, aEmpty);
		if (aEmpty) {
			setGraphic(null);
		}
		else {
			LABEL_METHOD.setText(aMethod.getName());
			setGraphic(BOX);
		}
	}

	public MethodListCell() {
		BOX = new HBox();
		BOX.setAlignment(Pos.CENTER_LEFT);
		//
		LABEL_METHOD = new Label();
		//
		BOX.getChildren().addAll(LABEL_METHOD);
		setGraphic(BOX);
		setText(null);
	}
	
}
