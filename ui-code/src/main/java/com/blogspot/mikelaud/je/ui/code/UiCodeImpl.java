package com.blogspot.mikelaud.je.ui.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.objectweb.asm.Type;

import com.blogspot.mikelaud.je.domain.pojo.DomainMethod;
import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.domain.types.AccFinal;
import com.blogspot.mikelaud.je.domain.types.MethodAccess;
import com.blogspot.mikelaud.je.domain.types.TypeAccess;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.background.UiBackground;
import com.blogspot.mikelaud.je.utils.TypeUtils;
import com.google.inject.Inject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class UiCodeImpl implements UiCode {

	private final MvcController CONTROLLER;
	private final MvcModel MODEL;
	private final UiCodeConst CONST;
	private final UiBackground BACKGROUND;
	//
	private final BorderPane PANE;
	private final ScrollPane CODE_PANE;
	private final TextFlow CODE;
	private final Font FONT_DEFAULT;
	private final Font FONT_KEYWORD;
	
	@Inject
	private UiCodeImpl
	(	MvcController aController
	,	UiCodeConst aConst
	,	UiBackground aBackground
	) {
		CONTROLLER = aController;
		MODEL = CONTROLLER.getModel();
		CONST = aConst;
		BACKGROUND = aBackground;
		//
		PANE = new BorderPane();
		CODE_PANE = new ScrollPane();
		CODE = new TextFlow();
		FONT_DEFAULT = createDefaultFont();
		FONT_KEYWORD = createKeywordFont();
		//
		buildForm();
	}
	
	private Node createCenter() {
		CODE_PANE.setPadding(new Insets(CONST.getSpacing(), CONST.getSpacing(), CONST.getSpacing(), CONST.getSpacing()));
		CODE_PANE.setContent(CODE);
		CODE_PANE.setVisible(false);
		//
		BACKGROUND.setImage(MODEL.getImage(CONST.getBackgroundImage()));
		BACKGROUND.getPane().getChildren().add(CODE_PANE);
		return BACKGROUND.getPane();
	}
	
	private void buildForm() {
		PANE.setCenter(createCenter());
		//
		PANE.setPadding(new Insets(CONST.getPadding(), CONST.getPadding(), CONST.getPadding(), CONST.getPadding()));
	}
	
	private Text newRem(String aText) {
		Text text = new Text(aText);
		text.setFont(FONT_DEFAULT);
		text.setFill(Color.rgb(0x3f, 0x7f, 0x5f));
		return text;
	}

	private Text newCode(String aText, boolean aSpace) {
		if (null == aText) {
			aText = "";
		}
		else if (aSpace && aText.length() > 0) {
			aText += " ";
		}
		Text text = new Text(aText);
		text.setFont(FONT_DEFAULT);
		return text;
	}
	
	private Text newCode(String aText) {
		return newCode(aText, true);
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
	
	private Text newEnd() {
		return newEnd("");
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
	
	private Text newKeyword(String aText, boolean aSpace) {
		if (null == aText) {
			aText = "";
		}
		else if (aSpace && aText.length() > 0) {
			aText += " ";
		}
		Text text = new Text(aText);
		text.setFont(FONT_KEYWORD);
		text.setFill(Color.rgb(0x7f, 0, 0x55));
		return text;
	}
	
	private Text newKeyword(String aText) {
		return newKeyword(aText, true);
	}
	
	private Font createDefaultFont() {
		Font defaultFont = new Text().getFont();
		return Font.font("Consolas", defaultFont.getSize());
	}
	
	private Font createKeywordFont() {
		Font defaultFont = createDefaultFont();
		return Font.font(defaultFont.getFamily(), FontWeight.BOLD, defaultFont.getSize());
	}
	
	public final void setType(DomainType aType) {
		if (null == aType) {
			CODE_PANE.setVisible(false);
		}
		else {
			SortedSet<DomainMethod> methods = new TreeSet<>((a, b) -> {
				int cmp = a.getAccess().compareTo(b.getAccess());
				if (cmp == 0) {
					cmp = a.getName().compareTo(b.getName());
				}
				return cmp;
			});
			methods.addAll(aType.getMethods());
			//----------------------------------------------------------------
			SortedMap<String,String> importTypes = new TreeMap<>();
			for (DomainMethod method : methods) {
				Type retType = TypeUtils.toElementarType(method.getReturnType());
				if (!TypeUtils.isElementarTypeIsObject(method.getReturnType())) continue;
				String name = TypeUtils.getName(retType);
				String newPackageName = TypeUtils.getPackage(retType);
				String oldPackageName = importTypes.get(name);
				if (null == oldPackageName) {
					importTypes.put(name, newPackageName);
				}
				else {
					if (oldPackageName.length() > 0) {
						if (! oldPackageName.equals(newPackageName)) {
							importTypes.put(name, "");
						}
					}
				}
			}
			//----------------------------------------------------------------
			List<Node> nodes = new ArrayList<>();
			//
			nodes.add(newKeyword("package"));
			nodes.add(newLink(aType.getPackageName()));
			nodes.add(newEnd(";"));
			nodes.add(newEnd());
			//
			if (importTypes.size() > 0) {
				for (Entry<String,String> entry : importTypes.entrySet()) {
					nodes.add(newKeyword("import"));
					nodes.add(newLink(entry.getValue().concat(".").concat(entry.getKey())));
					nodes.add(newEnd(";"));
				}
				nodes.add(newEnd());
			}
			//
			if (TypeAccess.Default != aType.getAccess()) {
				nodes.add(newKeyword(aType.getAccess().getCode()));
			}
			if (AccFinal.Yes == aType.getFinal()) {
				nodes.add(newKeyword(aType.getFinal().getCode()));
			}
			nodes.add(newKeyword(aType.getTypeType().getCode()));
			nodes.add(newLink(aType.getName()));
			nodes.add(newEnd(" {"));
			//
			if (methods.isEmpty()) {
				nodes.add(newRem("\t// void\n"));
			}
			else {
				MethodAccess access = null;
				for (DomainMethod method : methods) {
					if (method.getAccess() != access) {
						access = method.getAccess();
						nodes.add(newRem("\t// "));
						nodes.add(new ImageView(MODEL.getImage(access)));
						nodes.add(newEnd());
					}
					//
					nodes.add(newTab());
					if (MethodAccess.Default != access) {
						nodes.add(newKeyword(access.getCode()));
					}
					if (AccFinal.Yes == method.getFinal()) {
						nodes.add(newKeyword(method.getFinal().getCode()));
					}
					//
					final Type retType = method.getReturnType();
					final Type retElementarType = TypeUtils.toElementarType(retType);
					final boolean isArray = TypeUtils.isArray(retType);
					if (TypeUtils.isElementarTypeIsObject(retType)) {
						String name = TypeUtils.getName(retElementarType);
						String packageName = importTypes.get(name);
						if (null == packageName || packageName.isEmpty()) {
							name = retElementarType.getClassName();
						}
						nodes.add(newCode(name, !isArray));
						if (isArray) {
							nodes.add(newCode(TypeUtils.toDimentions(retType)));
						}
					}
					else {
						nodes.add(newKeyword(retElementarType.getClassName(), !isArray));
						if (isArray) {
							nodes.add(newCode(TypeUtils.toDimentions(retType)));
						}
					}
					//
					nodes.add(newLink(method.getName() + "()"));
					nodes.add(newEnd(";"));
				}
			}
			nodes.add(newEnd("}"));		
			//
			CODE.getChildren().setAll(nodes);
			CODE_PANE.setVisible(true);
		}
	}
	
	@Override
	public final Pane getPane() {
		return PANE;
	}

}
