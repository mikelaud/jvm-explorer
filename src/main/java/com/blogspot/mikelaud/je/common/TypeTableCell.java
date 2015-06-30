package com.blogspot.mikelaud.je.common;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class TypeTableCell extends TableCell<Type, Type> {

	private final VBox BOX;
	private final ImageView VIEW;

	@Override
	protected void updateItem(Type aType, boolean aEmpty) {
		super.updateItem(aType, aEmpty);
		if (aEmpty) {
			setText(null);
			setGraphic(null);
		}
		else {
			Image image = aType.getAccess().getImage(aType.getType(), aType.getDeprecated());
			VIEW.setImage(image);
			setGraphic(BOX);
		}
	}
	
	public TypeTableCell() {
		BOX = new VBox();
		BOX.setAlignment(Pos.CENTER);
		//
		VIEW = new ImageView();
		VIEW.setVisible(true);
		VIEW.setCache(true);
		VIEW.setImage(TypeType.Class.getImage());
		//
		BOX.getChildren().addAll(VIEW);
		setGraphic(BOX);
	}
	
}
