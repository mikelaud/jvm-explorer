package com.blogspot.mikelaud.je.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javafx.scene.image.Image;

public enum TypeAccess {

	Default,
	Public,
	Protected,
	Private;

	private final Image[][] IMAGES;
	
	private void createImage(TypeType aType, TypeDeprecated aDeprecated) {
		Path imagePath = Paths.get("type", aDeprecated.getLabel(), getLabel(), aType.getImageFilename());
		IMAGES[aDeprecated.ordinal()][aType.ordinal()] = new Image(imagePath.toString());
	}
	
	private void createImages(TypeDeprecated aDeprecated) {
		Arrays.stream(TypeType.values()).forEach(t -> createImage(t, aDeprecated));
	}
	
	private TypeAccess() {
		IMAGES = new Image[TypeDeprecated.values().length][TypeType.values().length];
		//
		Image defaultImage = TypeType.Class.getImage();
		for (TypeDeprecated dprecated : TypeDeprecated.values()) {
			for (TypeType type : TypeType.values()) {
				IMAGES[dprecated.ordinal()][type.ordinal()] = defaultImage;
			}
		}
		//
		Arrays.stream(TypeDeprecated.values()).forEach(d -> createImages(d));
	}
	
	public String getLabel() {
		return name().toLowerCase();
	}
	
	public Image getImage(TypeType aType, TypeDeprecated aDeprecated) {
		return IMAGES[aDeprecated.ordinal()][aType.ordinal()];
	}
	
}
