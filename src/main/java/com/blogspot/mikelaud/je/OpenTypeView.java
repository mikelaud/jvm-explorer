package com.blogspot.mikelaud.je;

import com.blogspot.mikelaud.je.common.Type;
import com.blogspot.mikelaud.je.common.TypeType;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private final OpenType MODEL; 
	private final String COLUMN_NAME;
	//
	private final String SEARCH_LABEL_STRING;
	private final String MATCHING_LABEL_STRING;
	//
	private final String PACKAGE_ICON_FILENAME;
	private final String DEFAULT_PACKAGE;
	//
	private Pane mPane;
	
	private Node createTop() {
		Label searchLabel = new Label(SEARCH_LABEL_STRING);
		TextField searchText = new TextField();
		searchText.setEditable(true);
		searchText.setAlignment(Pos.CENTER);
		//
		Label matchingLabel = new Label(MATCHING_LABEL_STRING);
		//
		VBox top = new VBox(searchLabel, searchText, matchingLabel);
		top.setSpacing(SPACING);
		return top;
	}
	
	private Node createCenter() {
		TableView<Type> table = new TableView<>();
		table.setEditable(false);
		table.setItems(FXCollections.observableArrayList(MODEL.get()));
		//
		TableColumn<Type,TypeType> imageColumn = new TableColumn<>();
		imageColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<TypeType>(cellData.getValue().getTypeType()));
		imageColumn.setCellFactory((tableColumn) -> new IconCell());
		table.getColumns().add(imageColumn);
		//
		TableColumn<Type,String> typeColumn = new TableColumn<>(COLUMN_NAME);
		typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		table.getColumns().add(typeColumn);
		//
		return table;
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
		MODEL = new OpenType();
		COLUMN_NAME = "Type";
		//
		SEARCH_LABEL_STRING = "Enter type name prefix or pattern (*, ?, or camel case):";
		MATCHING_LABEL_STRING = "Matching items:";
		//
		PACKAGE_ICON_FILENAME = "TypeLibrary.gif";
		DEFAULT_PACKAGE = MODEL.getJarName();
		//
		mPane = createPane();
	}
	
}
