package com.blogspot.mikelaud.je.ui.program;

import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.code.UiCode;
import com.blogspot.mikelaud.je.ui.resources.UiResources;
import com.blogspot.mikelaud.je.ui.search.UiSearch;
import com.google.inject.Inject;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UiProgramImpl implements UiProgram {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL;
	private final UiProgramConst CONST;
	private final UiResources RESOURCES;
	private final UiSearch SEARCH;
	private final UiCode CODE;
	//
	private final SplitPane PANE;
	private final Scene SCENE;
	private final Stage STAGE;

	@Inject
	private UiProgramImpl
	(	MvcController aController
	,	UiProgramConst aConst
	,	UiResources aResources
	,	UiSearch aSearch
	,	UiCode aCode
	) {
		CONTROLLER = aController;
		MODEL = CONTROLLER.getModel();
		CONST = aConst;
		RESOURCES = aResources;
		SEARCH = aSearch;
		CODE = aCode;
		//
		PANE = new SplitPane();
		SCENE = new Scene(PANE);
		STAGE = MODEL.getStage();
		//
		buildePane();
	}

	private TitledPane createJvmPane() {
		TitledPane pane = new TitledPane();
		pane.setText("JVM");
		BorderPane border = new BorderPane();
		//
		TextArea textArea = new TextArea();
		border.setCenter(textArea);
		border.setPadding(new Insets(5));
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
		border.setTop(hostBorder);
		pane.setContent(border);
		return pane;
	}

	private TitledPane createTypePane() {
		TitledPane pane = new TitledPane();
		pane.setText("Type");
		pane.setContent(SEARCH.getPane());
		return pane;
	}

	private Accordion createLeftPane() {
		Accordion accordion = new Accordion();
		accordion.getPanes().setAll(createJvmPane(), createTypePane());
		TitledPane lastPane = accordion.getPanes().stream().reduce((a, b) -> b).orElse(null);
		lastPane.setCollapsible(false);
		accordion.setExpandedPane(lastPane);
		accordion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
			@Override
			public void changed(ObservableValue<? extends TitledPane> observable, TitledPane oldPane, TitledPane newPane) {
				if (null != oldPane) {
					oldPane.setCollapsible(true);
				}
				if (null != newPane) {
					Platform.runLater(() -> newPane.setCollapsible(false));
				}
			}
		});
		return accordion;
	}

	private void buildePane() {
		PANE.getItems().addAll(createLeftPane(), CODE.getPane());
		buildStage();
	}

	private void buildStage() {
		STAGE.setScene(SCENE);
		RESOURCES.loadCss();
		STAGE.setTitle(CONST.getProgramTitle());
		STAGE.getIcons().setAll(MODEL.getImage(CONST.getProgramIcon()));
		STAGE.fullScreenExitHintProperty().setValue(CONST.getEmptyHint());
		//
		Rectangle2D visualBounds = createVisualBounds();
		Rectangle2D defaultBounds = createDefaultBounds(visualBounds);
		//
		STAGE.setWidth(defaultBounds.getWidth());
		STAGE.setHeight(defaultBounds.getHeight());
		//
		STAGE.setMinWidth(STAGE.getWidth());
		STAGE.setMinHeight(STAGE.getHeight());
		//
		STAGE.setMaxWidth(visualBounds.getWidth());
		STAGE.setMaxHeight(visualBounds.getHeight());
	}

	private Rectangle2D createVisualBounds() {
		return Screen.getPrimary().getVisualBounds();
	}

	private Rectangle2D createDefaultBounds(Rectangle2D aVisualBounds) {
		double defaultWidth = aVisualBounds.getWidth() / CONST.getScaleWidth();
		double defaultHeight = aVisualBounds.getHeight() / CONST.getScaleHeight();
		return new Rectangle2D(0, 0, defaultWidth, defaultHeight);
	}

	@Override
	public final SplitPane getPane() {
		return PANE;
	}

	@Override
	public final void show() {
		if (! STAGE.isShowing()) {
			STAGE.show();
			Platform.runLater(() -> CONTROLLER.getCore().setDefaultTypes());
		}
	}

	@Override
	public final void showCode(DomainType aType) {
		CODE.setType(aType);
	}
}
