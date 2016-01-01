package com.blogspot.mikelaud.je.ui.search;

import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.domain.types.TypeInheritance;
import com.blogspot.mikelaud.je.domain.types.TypeStatic;
import com.blogspot.mikelaud.je.domain.types.TypeType;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UiSearchListCell extends ListCell<DomainType> {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL; 
	//
	private final TextField TEXT_FIELD;
	private final Font FILTER_FONT;
	private final HBox BOX;
	private final ImageView VIEW;
	private final ImageView VIEW2;
	//
	private final Label LABEL_FILTER;
	private final Label LABEL_TYPE;
	private final Label LABEL_PACKAGE;
	
	private Font createFilterFont() {
		Font font = new Label().getFont();
		return Font.font(font.getFamily(), FontWeight.BOLD, font.getSize());
	}
	
	@Override
	protected void updateItem(DomainType aType, boolean aEmpty) {
		super.updateItem(aType, aEmpty);
		if (aEmpty) {
			setGraphic(null);
		}
		else {
			VIEW.setImage(MODEL.getImage(aType.getDeprecated(), aType.getAccess(), aType.getType()));
			VIEW2.setImage(MODEL.getImage(aType.getStatic(), aType.getInheritance()));
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
	
	public UiSearchListCell
	(	MvcController aController
	,	TextField aSearchField
	) {
		CONTROLLER = aController;
		MODEL = CONTROLLER.getModel();
		//
		TEXT_FIELD = aSearchField;
		FILTER_FONT = createFilterFont();
		BOX = new HBox();
		BOX.setAlignment(Pos.CENTER_LEFT);
		//
		VIEW = new ImageView();
		VIEW.setVisible(true);
		VIEW.setCache(true);
		VIEW.setImage(MODEL.getImage(TypeType.Class));
		//
		VIEW2 = new ImageView();
		VIEW2.setVisible(true);
		VIEW2.setCache(true);
		
		VIEW2.setImage(MODEL.getImage(TypeStatic.No, TypeInheritance.No));
		//
		StackPane viewPane = new StackPane(VIEW, VIEW2);
		viewPane.setAlignment(Pos.TOP_LEFT);
		//
		LABEL_FILTER = new Label();
		LABEL_FILTER.setFont(FILTER_FONT);
		LABEL_TYPE = new Label();
		LABEL_PACKAGE = new  Label();
		LABEL_PACKAGE.setTextFill(Color.GREY);
		//
		BOX.getChildren().addAll(viewPane, LABEL_FILTER, LABEL_TYPE, LABEL_PACKAGE);
		setGraphic(BOX);
		setText(null);
	}
	
}
