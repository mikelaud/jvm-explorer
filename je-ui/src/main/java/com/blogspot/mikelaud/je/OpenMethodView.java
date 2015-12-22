package com.blogspot.mikelaud.je;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.blogspot.mikelaud.je.common.ImagePane;
import com.blogspot.mikelaud.je.common.Method;
import com.blogspot.mikelaud.je.common.MethodListCell;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class OpenMethodView {

	private static interface Const {
		//
		Path BACKGROUND_IMAGE = Paths.get("background", "method.jpg");
		//
		int SPACING = 5;
		int PADDING = 10;
	}

	private final OpenMethod MODEL;
	private final BorderPane FORM;
	
	private Node createCenter() {
		ListView<Method> methodView = new ListView<>();
		methodView.setEditable(false);
		methodView.setItems(MODEL.getSortedData());
		methodView.setCellFactory((tableColumn) -> new MethodListCell());
		methodView.visibleProperty().bind(Bindings.isNotEmpty(MODEL.getFilteredData()));
		//
		ImagePane imagePane = new ImagePane();
		imagePane.setImage(new Image(Const.BACKGROUND_IMAGE.toString()));
		imagePane.getChildren().add(methodView);
		return imagePane;
	}
	
	private void buildForm() {
		FORM.setCenter(createCenter());
		//
		BorderPane.setMargin(FORM.getCenter(), new Insets(Const.SPACING, 0, Const.SPACING, 0));
		FORM.setPadding(new Insets(Const.PADDING, Const.PADDING, Const.PADDING, Const.PADDING));
	}
	
	public Pane getForm() {
		return FORM;
	}
	
	public OpenMethod getModel() {
		return MODEL;
	}
	
	public OpenMethodView() {
		MODEL = new OpenMethod();
		FORM = new BorderPane();
		buildForm();
	}
	
}
