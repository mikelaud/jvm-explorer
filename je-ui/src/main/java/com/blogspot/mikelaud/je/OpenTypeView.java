package com.blogspot.mikelaud.je;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.blogspot.mikelaud.je.common.ImagePane;
import com.blogspot.mikelaud.je.common.Type;
import com.blogspot.mikelaud.je.common.TypeListCell;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

	private final Path BACKGROUND_PATH;
	//
	private final int SPACING;
	private final int PADDING;
	//
	private final OpenType MODEL; 
	//
	private final String SEARCH_LABEL_STRING;
	private final String MATCHING_LABEL_STRING;
	//
	private final String PACKAGE_ICON_FILENAME;
	private final String DEFAULT_PACKAGE;
	//
	private ObservableList<Type> mObservableData;
	private FilteredList<Type> mFilteredData;
	private SortedList<Type> mSortedData;
	//
	private Pane mPane;
	private TextField mSearchField;

	private Node createMatching() {
		Label countFoundLabel = new Label();
		Label countLabel = new Label(" of ");
		Label countAllLabel = new Label();
		//
		countFoundLabel.textProperty().bind(Bindings.size(mFilteredData).asString());
		countAllLabel.textProperty().bind(Bindings.size(mObservableData).asString());
		//
		BorderPane pane = new BorderPane();
		pane.setLeft(new Label(MATCHING_LABEL_STRING));
		pane.setRight(new HBox(countFoundLabel, countLabel, countAllLabel));
		return pane;
	}
	
	private Node createTop() {
		Label searchLabel = new Label(SEARCH_LABEL_STRING);
		mSearchField = new TextField();
		mSearchField.setEditable(true);
		mSearchField.setAlignment(Pos.CENTER);
		//
		mSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
			String pattern = newValue.toLowerCase();
			mFilteredData.setPredicate(type -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				else {
					return type.getNameLowCase().startsWith(pattern);
				}
			});
		});
		//
		VBox top = new VBox(searchLabel, mSearchField, createMatching());
		top.setSpacing(SPACING);
		return top;
	}
	
	private Node createCenter() {
		ListView<Type> listView = new ListView<>();
		listView.setEditable(false);
		listView.setItems(mSortedData);
		listView.setCellFactory((tableColumn) -> new TypeListCell(mSearchField));
		listView.visibleProperty().bind(Bindings.isNotEmpty(mSortedData));
		//
		ImagePane imagePane = new ImagePane();
		imagePane.setImage(new Image(BACKGROUND_PATH.toString()));
		imagePane.getChildren().add(listView);
		return imagePane; 
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
		BACKGROUND_PATH = Paths.get("background.png");
		//
		SPACING = 5;
		PADDING = 10;
		//
		MODEL = new OpenType();
		//
		SEARCH_LABEL_STRING = "Enter type name prefix or pattern (*, ?, or camel case):";
		MATCHING_LABEL_STRING = "Matching items:";
		//
		PACKAGE_ICON_FILENAME = "library.png";
		DEFAULT_PACKAGE = MODEL.getJarName();
		//
		mObservableData = FXCollections.observableArrayList(MODEL.get());
		mFilteredData = new FilteredList<>(mObservableData, p -> true);
		mSortedData = new SortedList<>(mFilteredData, (a, b) -> a.getName().compareTo(b.getName()));
		//
		mPane = createPane();
	}
	
}
