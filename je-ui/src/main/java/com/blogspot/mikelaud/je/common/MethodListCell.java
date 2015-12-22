package com.blogspot.mikelaud.je.common;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MethodListCell extends ListCell<Method> {

	private final HBox BOX;
	private final ImageView VIEW;
	//
	private final Label LABEL_METHOD;
	
	@Override
	protected void updateItem(Method aMethod, boolean aEmpty) {
		super.updateItem(aMethod, aEmpty);
		if (aEmpty) {
			setGraphic(null);
		}
		else {
			VIEW.setImage(aMethod.getAccess().getImage());
			LABEL_METHOD.setText(aMethod.getName() + "()");
			setGraphic(BOX);
		}
	}

	public MethodListCell() {
		BOX = new HBox();
		BOX.setAlignment(Pos.CENTER_LEFT);
		//
		VIEW = new ImageView();
		VIEW.setVisible(true);
		VIEW.setCache(true);
		VIEW.setImage(MethodAccess.Private.getImage());
		//
		LABEL_METHOD = new Label();
		//
		BOX.getChildren().addAll(VIEW, LABEL_METHOD);
		setGraphic(BOX);
		setText(null);
	}
	
}
