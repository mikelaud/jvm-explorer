package com.blogspot.mikelaud.je.common;

import java.util.stream.IntStream;

import javafx.scene.image.Image;

public class TypeImage {

	private final Image[] IMAGES;
	
	public Image get(TypeType aTypeType) {
		return IMAGES[aTypeType.ordinal()];
	}
	
	public TypeImage() {
		IMAGES = new Image[TypeType.values().length];
		//
		Image defaultImage = new Image("TypeClass.gif");
		IntStream.range(0, IMAGES.length).forEach(i -> IMAGES[i] = defaultImage);
		//
		IMAGES[TypeType.Annotation.ordinal()] = new Image("TypeAnnotation.gif");
		IMAGES[TypeType.Enum.ordinal()] = new Image("TypeEnum.gif");
		IMAGES[TypeType.Interface.ordinal()] = new Image("TypeInterface.gif");
	}
	
}
