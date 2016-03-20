package com.blogspot.mikelaud.je.ui.program;

import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.code.UiCode;
import com.blogspot.mikelaud.je.ui.jvm.UiJvm;
import com.blogspot.mikelaud.je.ui.resources.UiResources;
import com.blogspot.mikelaud.je.ui.search.UiSearch;
import com.google.inject.Inject;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UiProgramImpl implements UiProgram {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL;
	private final UiProgramConst CONST;
	private final UiResources RESOURCES;
	private final UiSearch SEARCH;
	private final UiCode CODE;
	private final UiJvm JVM;
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
	,	UiJvm aJvm
	) {
		CONTROLLER = aController;
		MODEL = CONTROLLER.getModel();
		CONST = aConst;
		RESOURCES = aResources;
		SEARCH = aSearch;
		CODE = aCode;
		JVM = aJvm;
		//
		PANE = new SplitPane();
		SCENE = new Scene(PANE);
		STAGE = MODEL.getStage();
		//
		buildePane();
	}

	private Accordion createLeftPane() {
		Accordion accordion = new Accordion();
		accordion.getPanes().setAll
		(	new TitledPane(JVM.getName(), JVM.getPane())
		,	new TitledPane(SEARCH.getName(), SEARCH.getPane())
		);
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
		Accordion accordion = createLeftPane();
		PANE.getItems().setAll(accordion, CODE.getPane());
		buildStage(accordion);
	}

	private void buildStage(Accordion aAccordion) {
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
