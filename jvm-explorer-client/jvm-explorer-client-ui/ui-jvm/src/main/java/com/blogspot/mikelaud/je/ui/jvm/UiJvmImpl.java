package com.blogspot.mikelaud.je.ui.jvm;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.background.UiBackground;
import com.google.inject.Inject;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class UiJvmImpl implements UiJvm {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL;
	//
	private final UiJvmConst CONST;
	private final UiBackground BACKGROUND;
	private final BorderPane PANE;
	//
	private final TextField NAME_FIELD;
	private final TextField PID_FILED;
	private final TextField HOST_FIELD;
	private final Button CONNECT_BUTTON;
	private final Button LIST_BUTTON;
	private final ListView<JvmIdentity> JVM_LIST_VIEW;

	@Inject
	private UiJvmImpl
	(	MvcController aController
	,	UiJvmConst aConst
	,	UiBackground aBackground
	) {
		CONTROLLER = aController;
		MODEL = CONTROLLER.getModel();
		//
		CONST = aConst;
		BACKGROUND = aBackground;
		PANE = new BorderPane();
		//
		NAME_FIELD = new TextField();
		PID_FILED = new TextField();
		HOST_FIELD = new TextField();
		CONNECT_BUTTON = new Button();
		LIST_BUTTON = new Button();
		JVM_LIST_VIEW = new ListView<>();
		//
		buildForm();
		onCancel();
	}

	private void onCancel() {
		CONNECT_BUTTON.setDisable(true);
		LIST_BUTTON.setDisable(false);
		HOST_FIELD.setDisable(false);
		JVM_LIST_VIEW.setVisible(false);
		BACKGROUND.getImageView().setEffect(null);
	}

	private void onList() {
		if (HOST_FIELD.getText().isEmpty()) {
			HOST_FIELD.setText(HOST_FIELD.getPromptText());
		}
		LIST_BUTTON.setDisable(true);
		HOST_FIELD.setDisable(true);
		JVM_LIST_VIEW.setVisible(false);
		BACKGROUND.getImageView().setEffect(null);
		//
		CONTROLLER.doJvmConnect();
		//
		if (! JVM_LIST_VIEW.getItems().isEmpty()) {
			JVM_LIST_VIEW.setVisible(true);
			BACKGROUND.getImageView().setEffect(new ColorAdjust(0, 0, -0.7, 0));
		}
		LIST_BUTTON.setDisable(false);
		HOST_FIELD.setDisable(false);
	}

	private void onJvmSelect(JvmIdentity aOldValue, JvmIdentity aNewValue) {
		if (null == aNewValue) return;
		NAME_FIELD.setText(aNewValue.getName());
		PID_FILED.setText(aNewValue.getId());
		CONNECT_BUTTON.setDisable(false);
	}

	private void onHostEdit(String aOldValue, String aNewValue) {
		CONNECT_BUTTON.setDisable(true);
		NAME_FIELD.clear();
		PID_FILED.clear();
		JVM_LIST_VIEW.setVisible(false);
		BACKGROUND.getImageView().setEffect(null);
	}

	private Node createTop() {
		Label nameLabel = new Label("Name:");
		nameLabel.setMaxHeight(Double.MAX_VALUE);
		//
		NAME_FIELD.setId("read-only-field");
		NAME_FIELD.setEditable(false);
		NAME_FIELD.setFocusTraversable(false);
		NAME_FIELD.setMaxHeight(Double.MAX_VALUE);
		//
		CONNECT_BUTTON.setText("Connect");
		CONNECT_BUTTON.setMaxWidth(Double.MAX_VALUE);
		CONNECT_BUTTON.setMaxHeight(Double.MAX_VALUE);
		//
		Label pidLabel = new Label("PID:");
		pidLabel.setMaxHeight(Double.MAX_VALUE);
		//
		PID_FILED.setId("read-only-field");
		PID_FILED.setEditable(false);
		PID_FILED.setFocusTraversable(false);
		PID_FILED.setMaxHeight(Double.MAX_VALUE);
		//
		Label hostLabel = new Label("Host:");
		hostLabel.setMaxHeight(Double.MAX_VALUE);
		//
		HOST_FIELD.setAlignment(Pos.CENTER);
		HOST_FIELD.setMaxHeight(Double.MAX_VALUE);
		HOST_FIELD.setPromptText("localhost");
		HOST_FIELD.textProperty().addListener((observable, oldValue, newValue) -> onHostEdit(oldValue, newValue));
		//
		LIST_BUTTON.setText("List");
		LIST_BUTTON.setMaxWidth(Double.MAX_VALUE);
		LIST_BUTTON.setMaxHeight(Double.MAX_VALUE);
		LIST_BUTTON.setOnAction(a -> onList());
		//
		GridPane pane = new GridPane();
		pane.setVgap(MODEL.getConst().getPadding() / 2);
		pane.setHgap(pane.getVgap());
		//
		pane.getColumnConstraints().add(new ColumnConstraints()); // 0
		pane.getColumnConstraints().add(new ColumnConstraints()); // 1
		pane.getColumnConstraints().add(new ColumnConstraints()); // 2
		pane.getColumnConstraints().add(new ColumnConstraints()); // 3
		pane.getColumnConstraints().add(new ColumnConstraints()); // 4
		//
		pane.add(nameLabel, 0, 0);
		pane.add(NAME_FIELD, 1, 0, 3, 1);
		pane.add(CONNECT_BUTTON, 4, 0);
		//
		pane.add(pidLabel, 0, 1);
		pane.add(PID_FILED, 1, 1);
		pane.add(hostLabel, 2, 1);
		pane.add(HOST_FIELD, 3, 1);
		pane.add(LIST_BUTTON, 4, 1);
		//
		GridPane.setHalignment(nameLabel, HPos.RIGHT);
		GridPane.setHalignment(pidLabel, HPos.RIGHT);
		GridPane.setHalignment(hostLabel, HPos.RIGHT);
		GridPane.setHgrow(NAME_FIELD, Priority.ALWAYS);
		GridPane.setHgrow(HOST_FIELD, Priority.ALWAYS);
		GridPane.setFillWidth(CONNECT_BUTTON, true);
		GridPane.setFillWidth(LIST_BUTTON, true);
		//
		double nameWidth = 1.1 * (new Text(nameLabel.getText()).getLayoutBounds().getWidth());
		double hostWidth = 1.1 * (new Text(hostLabel.getText()).getLayoutBounds().getWidth());
		double connectWidth = 1.9 * (new Text(CONNECT_BUTTON.getText()).getLayoutBounds().getWidth());
		//
		pane.getColumnConstraints().get(0).setMinWidth(nameWidth);
		pane.getColumnConstraints().get(1).setMinWidth(connectWidth);
		pane.getColumnConstraints().get(1).setPrefWidth(connectWidth);
		pane.getColumnConstraints().get(2).setMinWidth(hostWidth);
		pane.getColumnConstraints().get(3).setMinWidth(connectWidth);
		pane.getColumnConstraints().get(4).setMinWidth(connectWidth);
		//
		return pane;
	}

	private Node createCenter() {
		BACKGROUND.setImage(MODEL.getImage(CONST.getBackgroundImage()));
		BACKGROUND.getPane().getChildren().add(createJvmList());
		return BACKGROUND.getPane();
	}

	private Node createJvmList() {
		JVM_LIST_VIEW.setId("jvm-list");
		JVM_LIST_VIEW.setItems(CONTROLLER.getModel().getJvmList());
		JVM_LIST_VIEW.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onJvmSelect(oldValue, newValue));
		//
		BorderPane pane = new BorderPane();
		pane.setCenter(JVM_LIST_VIEW);
		return pane;
	}

	private void buildForm() {
		PANE.setTop(createTop());
		PANE.setCenter(createCenter());
		//
		int padding = MODEL.getConst().getPadding();
		PANE.setPadding(new Insets(padding));
		BorderPane.setMargin(PANE.getTop(), new Insets(0, 0, padding, 0));
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
