package com.blogspot.mikelaud.je.ui.jvm;

import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.background.UiBackground;
import com.google.inject.Inject;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class UiJvmImpl implements UiJvm {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL;
	private final UiJvmConst CONST;
	private final UiBackground BACKGROUND;
	//
	private final BorderPane PANE;
	@SuppressWarnings("unused")
	private final ScrollPane JVM_PANE;

	@Inject
	private UiJvmImpl
	(	MvcController aController
	,	UiJvmConst aConst
	,	UiBackground aBackground
	) {
		CONTROLLER = aController;
		MODEL = CONTROLLER.getModel();
		CONST = aConst;
		BACKGROUND = aBackground;
		//
		PANE = new BorderPane();
		JVM_PANE = new ScrollPane();
		//
		buildForm();
	}

	private Node createTop() {
		//TitledPane pane = new TitledPane();
		//pane.setText("JVM");
		//BorderPane border = new BorderPane();
		//
		TextArea textArea = new TextArea();
		//border.setCenter(JVM.getPane());
		//border.setPadding(new Insets(5));
		BorderPane hostBorder = new BorderPane();
		//
		BorderPane nameBorder = new BorderPane();
		nameBorder.setLeft(new Label("Name: "));
		TextField nameField = new TextField("com.blogspot.mikelaud.je.agent.bios.Main");
		nameBorder.setCenter(nameField);
		hostBorder.setTop(nameBorder);
		//
		TextField hostField = new TextField("192.168.10.101");
		Button button = new Button("Load agent");
		button.setOnAction(e -> {
			textArea.setText("");
			Platform.runLater(() -> {
				String text = MODEL.getCore().loadAgent(hostField.getText(), nameField.getText());
				textArea.setText(text);
			});
		});
		hostBorder.setLeft(new Label("IP: "));
		hostBorder.setCenter(hostField);
		hostBorder.setRight(button);
		//
		//border.setTop(hostBorder);
		//pane.setContent(border);
		return hostBorder;
	}

	private Node createCenter() {
		PANE.setPadding(new Insets(CONST.getSpacing(), CONST.getSpacing(), CONST.getSpacing(), CONST.getSpacing()));
		//PANE.setContent();
		PANE.setVisible(true);
		//
		BACKGROUND.setImage(MODEL.getImage(CONST.getBackgroundImage()));
		//BACKGROUND.getPane().getChildren().add(JVM_PANE);
		return BACKGROUND.getPane();
	}

	private void buildForm() {
		PANE.setTop(createTop());
		PANE.setCenter(createCenter());
		//
		PANE.setPadding(new Insets(CONST.getPadding(), CONST.getPadding(), CONST.getPadding(), CONST.getPadding()));
	}

	@Override
	public Pane getPane() {
		return PANE;
	}

}
