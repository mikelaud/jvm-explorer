package com.blogspot.mikelaud.je.common;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.image.Image;

public enum TypeType {

	Annotation,
	Class,
	Enum,
	Interface;

	private final Path IMAGE_PATH;
	private final Image IMAGE;
	
	private TypeType() {
		String imageFilename = name().toLowerCase() + ".png";
		IMAGE_PATH = Paths.get("type", "public",  imageFilename);
		IMAGE = new Image(IMAGE_PATH.toString());
	}
	
	public String getImageFilename() {
		return IMAGE_PATH.getFileName().toString();
	}
	
	public Path getImagePath() {
		return IMAGE_PATH;
	}
	
	public Image getImage() {
		return IMAGE;
	}
	
}
