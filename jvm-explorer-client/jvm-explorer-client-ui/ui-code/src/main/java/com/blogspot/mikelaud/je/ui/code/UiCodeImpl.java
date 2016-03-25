package com.blogspot.mikelaud.je.ui.code;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.objectweb.asm.Type;

import com.blogspot.mikelaud.je.common.utils.TypeUtils;
import com.blogspot.mikelaud.je.domain.pojo.DomainMethod;
import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.domain.types.AccFinal;
import com.blogspot.mikelaud.je.domain.types.MethodAccess;
import com.blogspot.mikelaud.je.domain.types.TypeAccess;
import com.blogspot.mikelaud.je.domain.types.TypeDeprecated;
import com.blogspot.mikelaud.je.domain.types.TypeInheritance;
import com.blogspot.mikelaud.je.domain.types.TypeStatic;
import com.blogspot.mikelaud.je.domain.types.TypeType;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.background.UiBackground;
import com.blogspot.mikelaud.je.ui.background.UiBackgroundAppender;
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
		UiBackgroundAppender.setUi(BACKGROUND);
	}

	private Node createCenter() {
		//CODE_PANE.setPadding(new Insets(MODEL.getConst().getPadding()));
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
		PANE.setPadding(new Insets(MODEL.getConst().getPadding()));
	}

	private Text newRem(String aText) {
		Text text = new Text(aText);
		text.setFont(FONT_DEFAULT);
		text.setFill(Color.rgb(0x3f, 0x7f, 0x5f));
		return text;
	}

	private Text newCode(String aText, boolean aSpace, Color aColor) {
		if (null == aText) {
			aText = "";
		}
		else if (aSpace && aText.length() > 0) {
			aText += " ";
		}
		Text text = new Text(aText);
		text.setFont(FONT_DEFAULT);
		text.setFill(aColor);
		return text;
	}

	private Text newCode(String aText, boolean aSpace) {
		return newCode(aText, aSpace, Color.BLACK);
	}

	@SuppressWarnings("unused")
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

	private Hyperlink newLink(DomainType aType, String aText, boolean aStrikethrough, Color aColor) {
		final String text = (null == aText ? "" : aText);
		Hyperlink link = new Hyperlink(text);
		link.setFont(FONT_DEFAULT);
		link.setPadding(Insets.EMPTY);
		link.setTextFill(aColor);
		if (aStrikethrough) {
			link.getStyleClass().add("hyperlink-strikethrough");
		}
		link.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(text);
				alert.setContentText("Code bubble.");
				alert.setHeaderText(null);
				double nodeMinX = link.getLayoutBounds().getMinX();
			    double nodeMinY = link.getLayoutBounds().getMinY();
			    Point2D nodeInScene = link.localToScreen(nodeMinX, nodeMinY + link.getHeight());
				alert.setX(nodeInScene.getX());
				alert.setY(nodeInScene.getY());
				link.setVisited(false);
				if (null != aType) {
					MODEL.getDomain().getTypesBean().addLogging(0, aType.getName(), text);
				}
				alert.showAndWait();
			}
		});
		return link;
	}

	private Hyperlink newLink(String aText, boolean aStrikethrough) {
		return newLink(null, aText, aStrikethrough, Color.BLACK);
	}

	@SuppressWarnings("unused")
	private Hyperlink newLink(String aText, Color aColor) {
		return newLink(null, aText, false, aColor);
	}

	private Hyperlink newLink(DomainType aType, String aText, Color aColor) {
		return newLink(aType, aText, false, aColor);
	}

	private Hyperlink newLink(String aText) {
		return newLink(null, aText, false, Color.BLACK);
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

	private Map<String,String> getImportTypes(DomainType aType) {
		Map<String,String> importTypes = new HashMap<>();
		for (DomainMethod method : aType.getMethods()) {
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
		return importTypes;
	}

	private SortedSet<Entry<String,String>> getUiImportTypes(DomainType aType, Map<String,String> aImportTypes) {
		SortedSet<Entry<String,String>> uiImportTypes = new TreeSet<>((a, b) -> {
			int cmp = a.getValue().compareTo(b.getValue());
			if (cmp == 0) {
				cmp = a.getKey().compareTo(b.getKey());
			}
			return cmp;
		});
		for (Entry<String,String> entry : aImportTypes.entrySet()) {
			if (entry.getValue().isEmpty()) continue;
			if (entry.getValue().equals("java.lang")) continue;
			if (entry.getValue().equals(aType.getPackageName())) continue;
			uiImportTypes.add(new AbstractMap.SimpleEntry<String,String>(entry.getKey(), entry.getValue()));
		}
		return uiImportTypes;
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
			Map<String,String> importTypes = getImportTypes(aType);
			SortedSet<Entry<String,String>> uiImportTypes = getUiImportTypes(aType, importTypes);
			//----------------------------------------------------------------
			List<Node> nodes = new ArrayList<>();
			//
			nodes.add(newKeyword("package"));
			nodes.add(newLink(aType.getPackageName()));
			nodes.add(newEnd(";"));
			nodes.add(newEnd());
			//
			if (uiImportTypes.size() > 0) {
				for (Entry<String,String> entry : uiImportTypes) {
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
			if (TypeStatic.Yes == aType.getStatic()) {
				nodes.add(newKeyword(aType.getStatic().getCode()));
			}
			if (TypeInheritance.No != aType.getInheritance() && TypeType.Class == aType.getTypeType()) {
				nodes.add(newKeyword(aType.getInheritance().getCode()));
			}
			nodes.add(newKeyword(aType.getTypeType().getCode()));
			final boolean strikethrough = (TypeDeprecated.Yes == aType.getDeprecated());
			nodes.add(newLink(aType.getName(), strikethrough));
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
							if (! aType.getPackageName().equals(TypeUtils.getPackage(retElementarType))) {
								name = retElementarType.getClassName();
							}
						}
						nodes.add(newLink(name));
						if (isArray) {
							nodes.add(newCode(TypeUtils.toDimentions(retType), false));
						}
					}
					else {
						nodes.add(newKeyword(retElementarType.getClassName(), false));
						if (isArray) {
							nodes.add(newCode(TypeUtils.toDimentions(retType), false));
						}
					}
					//
					nodes.add(newCode(" ", false));
					nodes.add(newLink(aType, method.getName(), getColor(method.getAccess())));
					nodes.add(newCode(getArguments(method), false, Color.DIMGRAY));
					nodes.add(newEnd(";"));
				}
			}
			nodes.add(newEnd("}"));
			//
			CODE.getChildren().setAll(nodes);
			CODE_PANE.setVisible(true);
		}
	}

	private Color getColor(MethodAccess aMethodAccess) {
		Color color = Color.BLACK;
		if (null != aMethodAccess) {
			switch (aMethodAccess) {
			case Public:
				color = Color.DARKGREEN;
				break;
			case Protected:
				color = Color.SIENNA;
				break;
			case Default:
				color = Color.DARKBLUE;
				break;
			case Private:
				color = Color.DARKRED;
				break;
			default:
				break;
			}
		}
		return color;
	}

	private String getArguments(DomainMethod aMethod) {
		final int count = aMethod.getArgTypes().size();
		if (count > 0) {
			StringBuilder b = new StringBuilder();
			b.append("(");
			for (int i = 1; i <= count; i++) {
				if (i > 1) {
					b.append(", ");
				}
				b.append("a" + i);
			}
			b.append(")");
			return b.toString();
		}
		else {
			return "()";
		}
	}

	@Override
	public final Pane getPane() {
		return PANE;
	}

	@Override
	public UiBackground getBackground() {
		return BACKGROUND;
	}

}
