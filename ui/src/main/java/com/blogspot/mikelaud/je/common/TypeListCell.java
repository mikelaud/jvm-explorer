package com.blogspot.mikelaud.je.common;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class TypeListCell extends ListCell<Type> {

	private final HBox BOX;
	private final ImageView VIEW;
	//
	private final Label LABEL_TYPE;
	private final Label LABEL_PACKAGE;

	@Override
	protected void updateItem(Type aType, boolean aEmpty) {
		super.updateItem(aType, aEmpty);
		if (aEmpty) {
			setGraphic(null);
		}
		else {
			Image image = aType.getAccess().getImage(aType.getType(), aType.getDeprecated());
			VIEW.setImage(image);
			LABEL_TYPE.setText(aType.getName());
			LABEL_PACKAGE.setText(" - " + aType.getPackageName());
			setGraphic(BOX);
		}
	}
	
	public TypeListCell() {
		BOX = new HBox();
		BOX.setAlignment(Pos.CENTER_LEFT);
		//
		VIEW = new ImageView();
		VIEW.setVisible(true);
		VIEW.setCache(true);
		VIEW.setImage(TypeType.Class.getImage());
		//
		LABEL_TYPE = new Label();
		LABEL_PACKAGE = new  Label();
		LABEL_PACKAGE.setTextFill(Color.GREY);
		//
		BOX.getChildren().addAll(VIEW, LABEL_TYPE, LABEL_PACKAGE);
		setGraphic(BOX);
		setText(null);
	}
	
}
