package com.blogspot.mikelaud.je.common;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.image.Image;

public enum TypeType {

	Annotation,
	Class,
	Enum,
	Interface;

	private static interface Const {
		//
		String IMAGES_DIR1 = "type";
		String IMAGES_DIR2 = "normal";
		String IMAGES_DIR3 = "public";
		//
		String CLASS_EXT = ".png";
	}
	
	private final Path IMAGE_PATH;
	private final Image IMAGE;
	
	private TypeType() {
		String imageFilename = name().toLowerCase() + Const.CLASS_EXT;
		IMAGE_PATH = Paths.get(Const.IMAGES_DIR1, Const.IMAGES_DIR2, Const.IMAGES_DIR3,  imageFilename);
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
