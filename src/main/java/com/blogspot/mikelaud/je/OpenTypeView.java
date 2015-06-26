package com.blogspot.mikelaud.je;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OpenTypeView {

	private final int SPACING;
	private final int PADDING;
	//
	private final String SEARCH_LABEL_STRING;
	private final String MATCHING_LABEL_STRING;
	//
	private final String PACKAGE_ICON_FILENAME;
	private final String DEFAULT_PACKAGE;
	
	private final OpenType MODEL; 
	private Pane mPane;
	
	private Node createTop() {
		Label searchLabel = new Label(SEARCH_LABEL_STRING);
		TextField searchText = new TextField();
		searchText.setEditable(true);
		Label matchingLabel = new Label(MATCHING_LABEL_STRING);
		//
		VBox top = new VBox(searchLabel, searchText, matchingLabel);
		top.setSpacing(SPACING);
		return top;
	}
	
	private Node createCenter() {
		TextArea matchingArea = new TextArea();
		matchingArea.setEditable(false);
		matchingArea.setText(MODEL.get());
		return matchingArea;
	}

	private Node createBottom() {
		Label locationLabel = new Label(DEFAULT_PACKAGE, new ImageView(new Image(PACKAGE_ICON_FILENAME)));
		locationLabel.setBorder(new TextField().getBorder());
		//
		VBox bottom = new VBox(locationLabel);
		bottom.setSpacing(SPACING);
		return bottom;
	}
	
	private Pane createPane() {
		BorderPane pane = new BorderPane();
		pane.setTop(createTop());
		pane.setCenter(createCenter());
		pane.setBottom(createBottom());
		//
		BorderPane.setMargin(pane.getCenter(), new Insets(SPACING, 0, SPACING, 0));
		pane.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
		return pane;
	}
		
	public Pane getPane() {
		return mPane;
	}
	
	public OpenTypeView() {
		SPACING = 5;
		PADDING = 10;
		//
		SEARCH_LABEL_STRING = "Enter type name prefix or pattern (*, ?, or camel case):";
		MATCHING_LABEL_STRING = "Matching items:";
		//
		PACKAGE_ICON_FILENAME = "TypePackage.gif";
		DEFAULT_PACKAGE = "com.blogspot.mikelaud";
		//
		MODEL = new OpenType();
		mPane = createPane();
	}
	
}
