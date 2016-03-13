package com.blogspot.mikelaud.je.ui.jvm;

import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.background.UiBackground;
import com.google.inject.Inject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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

	/*
	@SuppressWarnings("unused")
	private Node createTopLegacy() {
		TextArea textArea = new TextArea();
		BorderPane hostBorder = new BorderPane();
		//
		BorderPane nameBorder = new BorderPane();
		nameBorder.setLeft(new Label("Name: "));
		TextField nameField = new TextField("com.blogspot.mikelaud.je.agent.bios.Main");
		nameBorder.setCenter(nameField);
		hostBorder.setTop(nameBorder);
		//
		TextField hostField = new TextField("192.168.10.101");
		Button connectButton = new Button("Connect");
		connectButton.setOnAction(e -> {
			textArea.setText("");
			Platform.runLater(() -> {
				String text = MODEL.getCore().loadAgent(hostField.getText(), nameField.getText());
				textArea.setText(text);
			});
		});
		Button disconnectButton = new Button("Disconnect");
		HBox leftBox = new HBox();
		leftBox.getChildren().setAll(disconnectButton, new Label("IP: "));
		hostBorder.setLeft(leftBox);
		hostBorder.setCenter(hostField);
		hostBorder.setRight(connectButton);
		return hostBorder;
	}
	*/

	private Node createTop() {
		Button disconnectButton = new Button("Disconnect");
		disconnectButton.setDisable(true);
		//
		TextField hostField = new TextField();
		hostField.setPromptText("Host name or IP");
		hostField.setAlignment(Pos.CENTER);
		//
		Button connectButton = new Button("Connect");
		connectButton.setDisable(false);
		//
		BorderPane pane = new BorderPane();
		pane.setLeft(disconnectButton);
		pane.setCenter(hostField);
		pane.setRight(connectButton);
		return pane;
	}

	private Node createCenter() {
		BACKGROUND.setImage(MODEL.getImage(CONST.getBackgroundImage()));
		return BACKGROUND.getPane();
	}

	private void buildForm() {
		PANE.setTop(createTop());
		PANE.setCenter(createCenter());
		//
		PANE.setPadding(new Insets(MODEL.getConst().getPadding()));
		BorderPane.setMargin(PANE.getTop(), new Insets(0, 0, MODEL.getConst().getPadding(), 0));
	}

	@Override public String getName() { return CONST.getName(); }
	@Override public Pane getPane() { return PANE; }

}
