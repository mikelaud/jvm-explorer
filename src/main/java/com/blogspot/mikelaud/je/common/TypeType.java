package com.blogspot.mikelaud.je.common;

import javafx.scene.image.Image;

public enum TypeType {

	Unknown("TypeUnknown.png"),
	Error("TypeError.png"),
	//
	Annotation("TypeAnnotation.gif"),
	Class("TypeClass.gif"),
	Enum("TypeEnum.gif"),
	Interface("TypeInterface.gif");

	private final String IMAGE_FILENAME;
	private final Image IMAGE;
	
	private TypeType(String aImageFilename) {
		IMAGE_FILENAME = aImageFilename;
		IMAGE = new Image(IMAGE_FILENAME);
	}
	
	public String getImageFilename() {
		return IMAGE_FILENAME;
	}
	
	public Image getImage() {
		return IMAGE;
	}
	
}
