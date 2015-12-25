package com.blogspot.mikelaud.je;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.blogspot.mikelaud.je.common.ImagePane;
import com.blogspot.mikelaud.je.common.Method;
import com.blogspot.mikelaud.je.common.MethodAccess;
import com.blogspot.mikelaud.je.common.Type;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private final ScrollPane CODE_PANE;
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
		CODE_PANE.setPadding(new Insets(Const.SPACING, Const.SPACING, Const.SPACING, Const.SPACING));
		CODE_PANE.setContent(CODE);
		CODE_PANE.setVisible(false);
		//
		ImagePane imagePane = new ImagePane();
		imagePane.setImage(new Image(Const.BACKGROUND_IMAGE.toString()));
		imagePane.getChildren().add(CODE_PANE);
		//imagePane.getChildren().add(methodView);
		return imagePane;
	}
	
	private void buildForm() {
		FORM.setCenter(createCenter());
		//
		BorderPane.setMargin(FORM.getCenter(), new Insets(Const.SPACING, 0, Const.SPACING, 0));
		FORM.setPadding(new Insets(Const.PADDING, Const.PADDING, Const.PADDING, Const.PADDING));
	}
	
	private Text newRem(String aText) {
		Text text = new Text(aText);
		text.setFont(FONT_DEFAULT);
		text.setFill(Color.rgb(0x3f, 0x7f, 0x5f));
		return text;
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
	
	private Hyperlink newLink(String aText) {
		Hyperlink link = new Hyperlink(aText);
		link.setFont(FONT_DEFAULT);
		link.setPadding(Insets.EMPTY);
		link.setTextFill(Color.BLACK);
		link.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(aText);
				alert.setContentText("Code bubble.");
				alert.setHeaderText(null);
				double nodeMinX = link.getLayoutBounds().getMinX();
			    double nodeMinY = link.getLayoutBounds().getMinY();
			    Point2D nodeInScene = link.localToScreen(nodeMinX, nodeMinY + link.getHeight());
				alert.setX(nodeInScene.getX());
				alert.setY(nodeInScene.getY());
				link.setVisited(false);
				alert.showAndWait();
			}
		});
		return link;
	}
	
	private Text newKeyword(String aText) {
		if (null == aText) {
			aText = "";
		}
		else if (aText.length() > 0) {
			aText += " ";
		}
		Text text = new Text(aText);
		text.setFont(FONT_KEYWORD);
		text.setFill(Color.rgb(0x7f, 0, 0x55));
		return text;
	}
		
	public void setType(Type aType) {
		if (null == aType) {
			CODE_PANE.setVisible(false);
		}
		else {
			SortedSet<Method> methods = new TreeSet<>((a, b) -> {
				int cmp = a.getAccess().compareTo(b.getAccess());
				if (cmp == 0) {
					cmp = a.getName().compareTo(b.getName());
				}
				return cmp;
			});
			methods.addAll(aType.getMethods());
			//
			CODE_PANE.setVisible(true);
			MODEL.setType(aType);
			List<Node> nodes = new ArrayList<>();
			//
			nodes.add(newKeyword(aType.getAccess().getCode()));
			nodes.add(newKeyword(aType.getType().getCode()));
			nodes.add(newLink(aType.getName()));
			nodes.add(newEnd(" {"));
			//
			if (methods.isEmpty()) {
				nodes.add(newRem("\t// void\n"));
			}
			else {
				MethodAccess access = null;
				for (Method method : methods) {
					if (method.getAccess() != access) {
						access = method.getAccess();
						nodes.add(newRem("\t// "));
						nodes.add(new ImageView(access.getImage()));
						nodes.add(newEnd(""));
					}
					nodes.add(newTab());
					nodes.add(newKeyword(access.getCode()));
					nodes.add(newKeyword("void"));
					nodes.add(newLink(method.getName() + "()"));
					nodes.add(newEnd(";"));
				}
			}
			nodes.add(newEnd("}"));		
			//
			CODE.getChildren().setAll(nodes);
		}
	}
	
	public Pane getForm() {
		return FORM;
	}
	
	public OpenMethod getModel() {
		return MODEL;
	}
	
	private Font createDefaultFont() {
		Font defaultFont = new Text().getFont();
		return Font.font("Consolas", defaultFont.getSize());
	}
	
	private Font createKeywordFont() {
		Font defaultFont = createDefaultFont();
		return Font.font(defaultFont.getFamily(), FontWeight.BOLD, defaultFont.getSize());
	}
	
	public OpenMethodView() {
		MODEL = new OpenMethod();
		FORM = new BorderPane();
		CODE_PANE = new ScrollPane();
		CODE = new TextFlow();
		FONT_DEFAULT = createDefaultFont();
		FONT_KEYWORD = createKeywordFont();
		buildForm();
	}
	
}
