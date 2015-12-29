package com.blogspot.mikelaud.je.domain.types;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javafx.scene.image.Image;

public enum TypeAccess {

	Default(""),
	Public("public"),
	Protected("protected"),
	Private("private");

	private static interface Const {
		Path IMAGES_PATH = Paths.get("type", "access");
	}
	
	private final String CODE;
	private final Image[][] IMAGES;
	
	private void createImage(TypeType aType, TypeDeprecated aDeprecated) {
		Path imagePath = Paths.get(Const.IMAGES_PATH.toString(), aDeprecated.getLabel(), getLabel(), aType.getImageFilename());
		IMAGES[aDeprecated.ordinal()][aType.ordinal()] = new Image(imagePath.toString());
	}
	
	private void createImages(TypeDeprecated aDeprecated) {
		Arrays.stream(TypeType.values()).forEach(t -> createImage(t, aDeprecated));
	}
	
	private TypeAccess(String aCode) {
		CODE = aCode;
		IMAGES = new Image[TypeDeprecated.values().length][TypeType.values().length];
		//
		Image defaultImage = TypeType.Class.getImage();
		for (TypeDeprecated deprecated : TypeDeprecated.values()) {
			for (TypeType type : TypeType.values()) {
				IMAGES[deprecated.ordinal()][type.ordinal()] = defaultImage;
			}
		}
		//
		Arrays.stream(TypeDeprecated.values()).forEach(d -> createImages(d));
	}
	
	public String getCode() {
		return CODE;
	}
	
	public String getLabel() {
		return name().toLowerCase();
	}
	
	public Image getImage(TypeType aType, TypeDeprecated aDeprecated) {
		return IMAGES[aDeprecated.ordinal()][aType.ordinal()];
	}
	
}