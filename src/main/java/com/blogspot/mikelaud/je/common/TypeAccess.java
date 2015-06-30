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
	
	private void createImage(TypeType aTypeType, TypeSource aSource) {
		Path imagePath = Paths.get("type", aSource.name().toLowerCase(), name().toLowerCase(), aTypeType.getImageFilename());
		IMAGES[aSource.ordinal()][aTypeType.ordinal()] = new Image(imagePath.toString());
	}
	
	private TypeAccess() {
		IMAGES = new Image[TypeSource.values().length][TypeType.values().length];
		//
		Image defaultImage = TypeType.Class.getImage();
		for (TypeSource source : TypeSource.values()) {
			for (TypeType typeType : TypeType.values()) {
				IMAGES[source.ordinal()][typeType.ordinal()] = defaultImage;
			}
		}
		//
		Arrays.stream(TypeType.values()).forEach(t -> createImage(t, TypeSource.Normal));
		Arrays.stream(TypeType.values()).forEach(t -> createImage(t, TypeSource.Deprecated));
	}
	
	public Image getImage(TypeType aTypeType, boolean aDeprecated) {
		return IMAGES[aDeprecated ? 1 : 0][aTypeType.ordinal()];
	}
	
}
