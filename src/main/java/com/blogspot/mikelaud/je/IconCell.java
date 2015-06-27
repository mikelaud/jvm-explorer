package com.blogspot.mikelaud.je;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class IconCell extends TableCell<String, String> {

	private final VBox BOX;
	private final ImageView IMAGE_VIEW;

	@Override
	protected void updateItem(String aItem, boolean aEmpty) {
		super.updateItem(aItem, aEmpty);
		if (aEmpty) {
			setText(null);
			setGraphic(null);
		}
		else {
			setGraphic(BOX);
		}
	}
	
	public IconCell() {
		BOX = new VBox();
		BOX.setAlignment(Pos.CENTER);
		//
		IMAGE_VIEW = new ImageView();
		//IMAGE_VIEW.setFitHeight(16);
		//IMAGE_VIEW.setFitWidth(16);
		IMAGE_VIEW.setVisible(true);
		IMAGE_VIEW.setCache(true);
		IMAGE_VIEW.setImage(new Image("TypeClass.gif"));
		BOX.getChildren().addAll(IMAGE_VIEW);
		//
		setGraphic(BOX);
	}
	
}
