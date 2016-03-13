package com.blogspot.mikelaud.je.ui.jvm;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.background.UiBackground;
import com.google.inject.Inject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class UiJvmImpl implements UiJvm {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL;
	private final UiJvmConst CONST;
	private final UiBackground BACKGROUND;
	private final BorderPane PANE;
	//
	private final TextField HOST_FIELD;

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
		PANE = new BorderPane();
		//
		HOST_FIELD = new TextField();
		//
		buildForm();
	}

	private Node createTop() {
		Button disconnectButton = new Button("Disconnect");
		disconnectButton.setDisable(true);
		disconnectButton.setOnAction(a -> CONTROLLER.doJvmDisconnect());
		//
		HOST_FIELD.setPromptText("Host name or IP");
		HOST_FIELD.setAlignment(Pos.CENTER);
		//
		Button connectButton = new Button("Connect");
		connectButton.setDisable(false);
		connectButton.setOnAction(a -> CONTROLLER.doJvmConnect());
		//
		BorderPane pane = new BorderPane();
		pane.setLeft(disconnectButton);
		pane.setCenter(HOST_FIELD);
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
	@Override public String getHost() { return HOST_FIELD.getText(); }

	@Override
	public void setJvmList(Stream<JvmIdentity> aJvmList) {
		Objects.requireNonNull(aJvmList);
		BACKGROUND.getLogger().setText(aJvmList.map(e -> e.toString()).collect(Collectors.joining("\n")));
		BACKGROUND.getLogger().setVisible(true);
	}

}
