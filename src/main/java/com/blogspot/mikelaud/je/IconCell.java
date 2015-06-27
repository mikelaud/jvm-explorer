package com.blogspot.mikelaud.je;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
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
		else { // Program.png
			Image image = new Image("TypeClass.gif");
			IMAGE_VIEW.setImage(image);
			IMAGE_VIEW.fitHeightProperty().bind(BOX.heightProperty());
			setGraphic(BOX);
		}
	}
	
	public IconCell(TableColumn<String, String> aParam) {
		BOX = new VBox();
		BOX.setAlignment(Pos.CENTER);
		//
		IMAGE_VIEW = new ImageView();
		//IMAGE_VIEW.setFitHeight(16);
		//IMAGE_VIEW.setFitWidth(16);
		IMAGE_VIEW.setVisible(true);
		IMAGE_VIEW.setCache(true);
		BOX.getChildren().addAll(IMAGE_VIEW);
		//
		setGraphic(BOX);
	}
	
}
