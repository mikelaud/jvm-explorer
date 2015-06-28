package com.blogspot.mikelaud.je.common;

import javafx.scene.image.Image;

public enum TypeType {

	Annotation,
	Class,
	Enum,
	Interface;

	private final String IMAGE_FILENAME;
	private final String IMAGE_PATH;
	private final Image IMAGE;
	
	private TypeType() {
		IMAGE_FILENAME = name().toLowerCase() + ".png";
		IMAGE_PATH = "type/public/" + IMAGE_FILENAME;
		IMAGE = new Image(IMAGE_PATH);
	}
	
	public String getImageFilename() {
		return IMAGE_FILENAME;
	}
	
	public String getImagePath() {
		return IMAGE_PATH;
	}
	
	public Image getImage() {
		return IMAGE;
	}
	
}
