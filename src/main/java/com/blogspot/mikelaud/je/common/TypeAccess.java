package com.blogspot.mikelaud.je.common;

import java.util.Arrays;
import java.util.stream.IntStream;

import javafx.scene.image.Image;

public enum TypeAccess {

	Default,
	Public,
	Protected,
	Private;
	
	private final Image[] IMAGES;
	
	private void createImage(TypeType aTypeType) {
		String imagePath = "type/" + name().toLowerCase() + "/" + aTypeType.getImageFilename();
		IMAGES[aTypeType.ordinal()] = new Image(imagePath);		
	}
	
	private TypeAccess() {
		IMAGES = new Image[TypeType.values().length];
		IntStream.range(0, TypeType.values().length)
			.forEach(i -> IMAGES[i] = TypeType.Class.getImage());
		//
		Arrays.stream(TypeType.values()).forEach(t -> createImage(t));
	}
	
	public Image getImage(TypeType aTypeType) {
		return IMAGES[aTypeType.ordinal()];
	}
	
}
