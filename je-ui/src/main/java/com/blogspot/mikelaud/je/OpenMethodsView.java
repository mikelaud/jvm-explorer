package com.blogspot.mikelaud.je;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.blogspot.mikelaud.je.common.ImagePane;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class OpenMethodsView {

	private static interface Const {
		//
		Path BACKGROUND_IMAGE = Paths.get("background", "methods.png");
	}

	private final BorderPane FORM;
	
	private void buildForm() {
		ImagePane imagePane = new ImagePane();
		imagePane.setImage(new Image(Const.BACKGROUND_IMAGE.toString()));
		FORM.setCenter(imagePane);
	}
	
	public Pane getForm() {
		return FORM;
	}
	
	public OpenMethodsView() {
		FORM = new BorderPane();
		buildForm();
	}
	
}
