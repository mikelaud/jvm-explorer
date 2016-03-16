package com.blogspot.mikelaud.je.ui.jvm;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.background.UiBackground;
import com.google.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
	private final ListView<String> JVM_LIST_VIEW;
	//
	private final TextField HOST_FIELD;
	private final Button DISCONNECT_BUTTON;
	private final Button LIST_BUTTON;
	private final Button CONNECT_BUTTON;
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
		HOST_FIELD = new TextField();
		DISCONNECT_BUTTON = new Button();
		LIST_BUTTON = new Button();
		CONNECT_BUTTON = new Button();
		CANCEL_BUTTON = new Button();
		//
		buildForm();
	}

	private void switchToDisconnect() {
		LIST_BUTTON.setDisable(false);
		DISCONNECT_BUTTON.setDisable(true);
		CONNECT_BUTTON.setVisible(false);
		CANCEL_BUTTON.setVisible(false);
		HOST_FIELD.setDisable(false);
		JVM_LIST_VIEW.setVisible(false);
		BACKGROUND.getImageView().setEffect(null);
	}

	private void switchToList() {
		LIST_BUTTON.setDisable(true);
		DISCONNECT_BUTTON.setDisable(true);
		CONNECT_BUTTON.setVisible(true);
		CANCEL_BUTTON.setVisible(true);
		HOST_FIELD.setDisable(true);
		JVM_LIST_VIEW.setVisible(true);
		BACKGROUND.getImageView().setEffect(new ColorAdjust(0, 0, -0.7, 0));
	}

	private Node createTop() {
		DISCONNECT_BUTTON.setText("Disconnect");
		DISCONNECT_BUTTON.setOnAction(a -> switchToDisconnect());
		//
		HOST_FIELD.setPromptText("Host name or IP");
		HOST_FIELD.setAlignment(Pos.CENTER);
		HOST_FIELD.setFocusTraversable(false);
		//
		LIST_BUTTON.setText("List");
		LIST_BUTTON.prefWidthProperty().bind(DISCONNECT_BUTTON.widthProperty());
		LIST_BUTTON.setOnAction(a -> switchToList());
		//
		BorderPane pane = new BorderPane();
		pane.setLeft(DISCONNECT_BUTTON);
		pane.setCenter(HOST_FIELD);
		pane.setRight(LIST_BUTTON);
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
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll
		(	"111", "222", "333", "444", "555", "666", "777", "888", "999"
		,	"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"
		,	"111", "222", "333", "444", "555", "666", "777", "888", "999"
		,	"111", "222", "333", "444", "555", "666", "777", "888", "999"
		);
		JVM_LIST_VIEW.setItems(items);
		pane.setCenter(JVM_LIST_VIEW);
		//
		CANCEL_BUTTON.setText("Cancel"); 
		CANCEL_BUTTON.setId("jvm-list-cancel");
		CANCEL_BUTTON.prefWidthProperty().bind(DISCONNECT_BUTTON.widthProperty());
		CANCEL_BUTTON.setOnAction(a -> switchToDisconnect());
		//
		CONNECT_BUTTON.setText("Connect");
		CONNECT_BUTTON.setId("jvm-list-connect");
		CONNECT_BUTTON.prefWidthProperty().bind(DISCONNECT_BUTTON.widthProperty());
		//
		TilePane buttons = new TilePane();
		buttons.getChildren().setAll(CANCEL_BUTTON, CONNECT_BUTTON);
		buttons.hgapProperty().bind(DISCONNECT_BUTTON.widthProperty());
		buttons.setAlignment(Pos.CENTER);
		//
		pane.setTop(buttons);
		return pane;
	}

	private void buildForm() {
		PANE.setTop(createTop());
		PANE.setCenter(createCenter());
		//
		PANE.setPadding(new Insets(MODEL.getConst().getPadding()));
		BorderPane.setMargin(PANE.getTop(), new Insets(0, 0, MODEL.getConst().getPadding(), 0));
		//
		switchToDisconnect();
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
