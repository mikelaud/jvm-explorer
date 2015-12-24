package com.blogspot.mikelaud.je;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.blogspot.mikelaud.je.common.ImagePane;
import com.blogspot.mikelaud.je.common.Method;
import com.blogspot.mikelaud.je.common.Type;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class OpenMethodView {

	private static interface Const {
		//
		Path BACKGROUND_IMAGE = Paths.get("background", "method.jpg");
		//
		int SPACING = 5;
		int PADDING = 10;
	}

	private final OpenMethod MODEL;
	private final BorderPane FORM;
	private final TextFlow CODE;
	private final Font FONT_DEFAULT;
	private final Font FONT_KEYWORD;
	
	private Node createCenter() {
		/*
		ListView<Method> methodView = new ListView<>();
		methodView.setEditable(false);
		methodView.setItems(MODEL.getSortedData());
		methodView.setCellFactory((tableColumn) -> new MethodListCell());
		methodView.visibleProperty().bind(Bindings.isNotEmpty(MODEL.getFilteredData()));
		*/
		//
		ImagePane imagePane = new ImagePane();
		imagePane.setImage(new Image(Const.BACKGROUND_IMAGE.toString()));
		//imagePane.getChildren().add(methodView);
		imagePane.getChildren().add(CODE);
		BorderPane pane = new BorderPane();
		pane.setCenter(CODE);
		return pane;
	}
	
	private void buildForm() {
		FORM.setCenter(createCenter());
		//
		BorderPane.setMargin(FORM.getCenter(), new Insets(Const.SPACING, 0, Const.SPACING, 0));
		FORM.setPadding(new Insets(Const.PADDING, Const.PADDING, Const.PADDING, Const.PADDING));
	}
	
	private Text newCode(String aText) {
		Text text = new Text(aText + " ");
		text.setFont(FONT_DEFAULT);
		return text;
	}
	
	private Text newTab() {
		Text text = new Text("\t");
		text.setFont(FONT_DEFAULT);
		return text;
	}
	
	private Text newEnd(String aText) {
		Text text = new Text(aText + "\n");
		text.setFont(FONT_DEFAULT);
		return text;
	}
	
	private Text newKeyword(String aText) {
		Text text = new Text(aText + " ");
		text.setFont(FONT_KEYWORD);
		text.setFill(Color.rgb(0x7f, 0, 0x55));
		return text;
	}
	
	public void setType(Type aType) {
		MODEL.setType(aType);
		List<Node> nodes = new ArrayList<>();
		//
		nodes.add(newKeyword(aType.getAccess().getCode()));
		nodes.add(newKeyword(aType.getType().getCode()));
		nodes.add(newCode(aType.getName()));
		nodes.add(newEnd("{"));
		for (Method method : aType.getMethods()) {
			nodes.add(newTab());
			nodes.add(newKeyword("void"));
			nodes.add(newKeyword(method.getAccess().getCode()));
			nodes.add(newEnd(method.getName() + "();"));
		}
		nodes.add(newEnd("}"));		
		//
		CODE.getChildren().setAll(nodes);
	}
	
	public Pane getForm() {
		return FORM;
	}
	
	public OpenMethod getModel() {
		return MODEL;
	}
	
	private Font createDefaultFont() {
		Font defaultFont = new Text().getFont();
		return Font.font(java.awt.Font.MONOSPACED, defaultFont.getSize());
	}
	
	private Font createKeywordFont() {
		Font defaultFont = createDefaultFont();
		return Font.font(java.awt.Font.MONOSPACED, FontWeight.BOLD, defaultFont.getSize());
	}
	
	public OpenMethodView() {
		MODEL = new OpenMethod();
		FORM = new BorderPane();
		CODE = new TextFlow();
		FONT_DEFAULT = createDefaultFont();
		FONT_KEYWORD = createKeywordFont();
		buildForm();
	}
	
}
