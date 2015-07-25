package com.blogspot.mikelaud.je.common;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TypeListCell extends ListCell<Type> {

	private final TextField TEXT_FIELD;
	private final Font FILTER_FONT;
	private final HBox BOX;
	private final ImageView VIEW;
	//
	private final Label LABEL_FILTER;
	private final Label LABEL_TYPE;
	private final Label LABEL_PACKAGE;
	
	private Font createFilterFont() {
		Font font = new Label().getFont();
		return Font.font(font.getFamily(), FontWeight.BOLD, font.getSize());
	}
	
	@Override
	protected void updateItem(Type aType, boolean aEmpty) {
		super.updateItem(aType, aEmpty);
		if (aEmpty) {
			setGraphic(null);
		}
		else {
			Image image = aType.getAccess().getImage(aType.getType(), aType.getDeprecated());
			VIEW.setImage(image);
			String filter = TEXT_FIELD.getText();
			String text = aType.getName();
			if (filter.length() >= text.length()) {
				LABEL_FILTER.setText(text);
				LABEL_TYPE.setText("");
			}
			else {
				LABEL_FILTER.setText(text.substring(0, filter.length()));
				LABEL_TYPE.setText(text.substring(filter.length()));
			}
			LABEL_PACKAGE.setText(" - " + aType.getPackageName());
			setGraphic(BOX);
		}
	}
	
	public TypeListCell(TextField aSearchField) {
		TEXT_FIELD = aSearchField;
		FILTER_FONT = createFilterFont();
		BOX = new HBox();
		BOX.setAlignment(Pos.CENTER_LEFT);
		//
		VIEW = new ImageView();
		VIEW.setVisible(true);
		VIEW.setCache(true);
		VIEW.setImage(TypeType.Class.getImage());
		//
		LABEL_FILTER = new Label();
		LABEL_FILTER.setFont(FILTER_FONT);
		LABEL_TYPE = new Label();
		LABEL_PACKAGE = new  Label();
		LABEL_PACKAGE.setTextFill(Color.GREY);
		//
		BOX.getChildren().addAll(VIEW, LABEL_FILTER, LABEL_TYPE, LABEL_PACKAGE);
		setGraphic(BOX);
		setText(null);
	}
	
}