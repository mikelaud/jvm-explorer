package com.blogspot.mikelaud.je;

import com.blogspot.mikelaud.je.common.ImagePane;
import com.blogspot.mikelaud.je.common.Type;
import com.blogspot.mikelaud.je.common.TypeListCell;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OpenTypeView {

	private static interface Const {
		
		String BACKGROUND_IMAGE = "background.png";
		String PACKAGE_ICON = "library.png";
		//
		String SEARCH_LABEL = "Enter type name prefix or pattern (*, ?, or camel case):";
		String MATCHING_LABEL = "Matching items:";
		String COUNT_LABEL = " of ";
		//
		int SPACING = 5;
		int PADDING = 10;
	}
	
	private final OpenType MODEL;
	private final TextField SEARCH_FIELD;
	private Pane mPane;

	private Node createMatching() {
		Label countFoundLabel = new Label();
		Label countLabel = new Label(Const.COUNT_LABEL);
		Label countAllLabel = new Label();
		//
		countFoundLabel.textProperty().bind(Bindings.size(MODEL.getFilteredData()).asString());
		countAllLabel.textProperty().bind(Bindings.size(MODEL.getData()).asString());
		//
		BorderPane pane = new BorderPane();
		pane.setLeft(new Label(Const.MATCHING_LABEL));
		pane.setRight(new HBox(countFoundLabel, countLabel, countAllLabel));
		return pane;
	}
	
	private Node createTop() {
		Label searchLabel = new Label(Const.SEARCH_LABEL);
		SEARCH_FIELD.setEditable(true);
		SEARCH_FIELD.setAlignment(Pos.CENTER);
		//
		SEARCH_FIELD.textProperty().addListener((observable, oldValue, newValue) -> {
			String pattern = newValue.toLowerCase();
			MODEL.getFilteredData().setPredicate(type -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				else {
					return type.getNameLowCase().startsWith(pattern);
				}
			});
		});
		//
		VBox top = new VBox(searchLabel, SEARCH_FIELD, createMatching());
		top.setSpacing(Const.SPACING);
		return top;
	}
	
	private Node createCenter() {
		ListView<Type> listView = new ListView<>();
		listView.setEditable(false);
		listView.setItems(MODEL.getSortedData());
		listView.setCellFactory((tableColumn) -> new TypeListCell(SEARCH_FIELD));
		listView.visibleProperty().bind(Bindings.isNotEmpty(MODEL.getFilteredData()));
		//
		ImagePane imagePane = new ImagePane();
		imagePane.setImage(new Image(Const.BACKGROUND_IMAGE));
		imagePane.getChildren().add(listView);
		return imagePane; 
	}

	private Node createBottom() {
		Label locationLabel = new Label(MODEL.getDefaultPackage(), new ImageView(new Image(Const.PACKAGE_ICON)));
		locationLabel.setBorder(new TextField().getBorder());
		//
		VBox bottom = new VBox(locationLabel);
		bottom.setSpacing(Const.SPACING);
		return bottom;
	}
	
	private Pane createPane() {
		BorderPane pane = new BorderPane();
		pane.setTop(createTop());
		pane.setCenter(createCenter());
		pane.setBottom(createBottom());
		//
		BorderPane.setMargin(pane.getCenter(), new Insets(Const.SPACING, 0, Const.SPACING, 0));
		pane.setPadding(new Insets(Const.PADDING, Const.PADDING, Const.PADDING, Const.PADDING));
		return pane;
	}
		
	public Pane getPane() {
		return mPane;
	}
	
	public OpenTypeView() {
		MODEL = new OpenType();
		SEARCH_FIELD = new TextField();
		mPane = createPane();
	}
	
}
