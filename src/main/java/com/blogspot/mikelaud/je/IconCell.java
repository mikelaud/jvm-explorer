package com.blogspot.mikelaud.je;

import com.blogspot.mikelaud.je.common.Type;
import com.blogspot.mikelaud.je.common.TypeImage;
import com.blogspot.mikelaud.je.common.TypeType;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class IconCell extends TableCell<Type, TypeType> {

	private static final TypeImage TYPE_IMAGE = new TypeImage();
	//
	private final VBox BOX;
	private final ImageView VIEW;

	@Override
	protected void updateItem(TypeType aTypeType, boolean aEmpty) {
		super.updateItem(aTypeType, aEmpty);
		if (aEmpty) {
			setText(null);
			setGraphic(null);
		}
		else {
			VIEW.setImage(TYPE_IMAGE.get(aTypeType));
			setGraphic(BOX);
		}
	}
	
	public IconCell() {
		BOX = new VBox();
		BOX.setAlignment(Pos.CENTER);
		//
		VIEW = new ImageView();
		VIEW.setVisible(true);
		VIEW.setCache(true);
		VIEW.setImage(TYPE_IMAGE.get(TypeType.Class));
		//
		BOX.getChildren().addAll(VIEW);
		setGraphic(BOX);
	}
	
}
