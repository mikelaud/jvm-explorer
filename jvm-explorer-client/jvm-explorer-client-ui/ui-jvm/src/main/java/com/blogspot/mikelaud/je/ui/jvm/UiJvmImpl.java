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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class UiJvmImpl implements UiJvm {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL;
	private final UiJvmConst CONST;
	private final UiBackground BACKGROUND;
	private final BorderPane PANE;
	private final ListView<JvmIdentity> JVM_LIST_VIEW;
	//
	private final Label HOST_LABEL;
	private final TextField HOST_FIELD;
	private final Button DISCONNECT_BUTTON;
	private final Button CONNECT_BUTTON;
	private final Button ATTACH_BUTTON;
	private final Button CANCEL_BUTTON;

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
		JVM_LIST_VIEW = new ListView<>();
		//
		HOST_LABEL = new Label();
		HOST_FIELD = new TextField();
		DISCONNECT_BUTTON = new Button();
		CONNECT_BUTTON = new Button();
		ATTACH_BUTTON = new Button();
		CANCEL_BUTTON = new Button();
		//
		buildForm();
	}

	private void switchToCancel() {
		CONNECT_BUTTON.setDisable(false);
		DISCONNECT_BUTTON.setDisable(true);
		ATTACH_BUTTON.setVisible(false);
		CANCEL_BUTTON.setVisible(false);
		HOST_FIELD.setDisable(false);
		JVM_LIST_VIEW.setVisible(false);
		BACKGROUND.getImageView().setEffect(null);
		//
		ATTACH_BUTTON.setDisable(true);
	}

	private void switchToConnect() {
		if (HOST_FIELD.getText().isEmpty()) {
			HOST_FIELD.setText(HOST_FIELD.getPromptText());
		}
		CONNECT_BUTTON.setDisable(true);
		DISCONNECT_BUTTON.setDisable(true);
		ATTACH_BUTTON.setVisible(true);
		CANCEL_BUTTON.setVisible(true);
		HOST_FIELD.setDisable(true);
		JVM_LIST_VIEW.setVisible(true);
		BACKGROUND.getImageView().setEffect(new ColorAdjust(0, 0, -0.7, 0));
		//
		ATTACH_BUTTON.setDisable(true);
		CONTROLLER.doJvmConnect();
		ATTACH_BUTTON.setDisable(false);
	}

	private Node createHostPanel() {
		HOST_LABEL.setText("Host name or IP: ");
		HOST_LABEL.setMaxHeight(Double.MAX_VALUE);
		//
		HOST_FIELD.setPromptText("localhost");
		HOST_FIELD.setText(HOST_FIELD.getPromptText());
		HOST_FIELD.setFocusTraversable(false);
		HOST_FIELD.setAlignment(Pos.CENTER);
		HOST_FIELD.setMaxHeight(Double.MAX_VALUE);
		//
		CONNECT_BUTTON.setText("Connect");
		CONNECT_BUTTON.setMaxHeight(Double.MAX_VALUE);
		CONNECT_BUTTON.prefWidthProperty().bind(HOST_LABEL.widthProperty());
		CONNECT_BUTTON.setOnAction(a -> switchToConnect());
		//
		BorderPane pane = new BorderPane();
		pane.setLeft(HOST_LABEL);
		pane.setCenter(HOST_FIELD);
		pane.setRight(CONNECT_BUTTON);
		return pane;
	}

	private Node createCenter() {
		BACKGROUND.setImage(MODEL.getImage(CONST.getBackgroundImage()));
		BACKGROUND.getPane().getChildren().add(createJvmList());
		return BACKGROUND.getPane();
	}

	private Node createJvmList() {
		BorderPane pane = new BorderPane();
		JVM_LIST_VIEW.setId("jvm-list");
		JVM_LIST_VIEW.setItems(CONTROLLER.getModel().getJvmList());
		pane.setCenter(JVM_LIST_VIEW);
		//
		CANCEL_BUTTON.setText("Cancel");
		CANCEL_BUTTON.prefWidthProperty().bind(HOST_LABEL.widthProperty());
		CANCEL_BUTTON.setOnAction(a -> switchToCancel());
		//
		ATTACH_BUTTON.setText("Attach");
		ATTACH_BUTTON.prefWidthProperty().bind(HOST_LABEL.widthProperty());
		//
		TilePane buttons = new TilePane();
		buttons.getChildren().setAll(CANCEL_BUTTON, ATTACH_BUTTON);
		buttons.setAlignment(Pos.CENTER);
		buttons.hgapProperty().bind(HOST_LABEL.widthProperty().divide(2));
		buttons.setPadding(new Insets(MODEL.getConst().getPadding()));
		//
		pane.setTop(buttons);
		return pane;
	}

	private void buildForm() {
		PANE.setTop(createHostPanel());
		PANE.setCenter(createCenter());
		//
		PANE.setPadding(new Insets(MODEL.getConst().getPadding()));
		BorderPane.setMargin(PANE.getTop(), new Insets(0, 0, MODEL.getConst().getPadding(), 0));
		//
		switchToCancel();
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
