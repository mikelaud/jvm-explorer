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

public class UiJvmImpl implements UiJvm {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL;
	private final UiJvmConst CONST;
	private final UiBackground BACKGROUND;
	private final BorderPane PANE;
	//
	private final TextField HOST_FIELD;
	private final Button DISCONNECT_BUTTON;
	private final ListView<String> JVM_LIST_VIEW;

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
		DISCONNECT_BUTTON = new Button();
		JVM_LIST_VIEW = new ListView<>();
		//
		buildForm();
	}

	private void switchToDisconnect() {
		DISCONNECT_BUTTON.setDisable(true);
		HOST_FIELD.setDisable(false);
		JVM_LIST_VIEW.setVisible(false);
		BACKGROUND.getImageView().setEffect(null);
	}

	private void switchToList() {
		DISCONNECT_BUTTON.setDisable(false);
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
		Button listButton = new Button("List");
		listButton.prefWidthProperty().bind(DISCONNECT_BUTTON.widthProperty());
		listButton.setOnAction(a -> switchToList());
		//
		BorderPane pane = new BorderPane();
		pane.setLeft(DISCONNECT_BUTTON);
		pane.setCenter(HOST_FIELD);
		pane.setRight(listButton);
		return pane;
	}

	private Node createCenter() {
		BACKGROUND.setImage(MODEL.getImage(CONST.getBackgroundImage()));
		JVM_LIST_VIEW.setId("jvm-list-view");
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll
		(	"111", "222", "333", "444", "555", "666", "777", "888", "999"
		,	"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"
		,	"111", "222", "333", "444", "555", "666", "777", "888", "999"
		,	"111", "222", "333", "444", "555", "666", "777", "888", "999"
		);
		JVM_LIST_VIEW.setItems(items);
		BACKGROUND.getPane().getChildren().add(JVM_LIST_VIEW);
		return BACKGROUND.getPane();
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
