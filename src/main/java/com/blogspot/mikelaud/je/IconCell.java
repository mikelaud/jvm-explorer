package com.blogspot.mikelaud.je;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class IconCell extends TableCell<String, String> {

	private final VBox BOX;

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
		ImageView imageView = new ImageView();
		imageView.setVisible(true);
		imageView.setCache(true);
		imageView.setImage(new Image("TypeClass.gif"));
		//
		BOX.getChildren().addAll(imageView);
		setGraphic(BOX);
	}
	
}
