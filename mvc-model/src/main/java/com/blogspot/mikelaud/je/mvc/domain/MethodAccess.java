package com.blogspot.mikelaud.je.mvc.domain;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.image.Image;

public enum MethodAccess {

	Public("public"),
	Protected("protected"),
	Default(""),
	Private("private");
	
	private static interface Const {
		Path IMAGES_PATH = Paths.get("method", "access");
		String IMAGE_EXT = ".png";
	}
	
	private final String CODE;
	private final Path IMAGE_PATH;
	private final Image IMAGE;
	
	private MethodAccess(String aCode) {
		CODE = aCode;
		String imageFilename = name().toLowerCase() + Const.IMAGE_EXT;
		IMAGE_PATH = Const.IMAGES_PATH.resolve(imageFilename);
		IMAGE = new Image(IMAGE_PATH.toString());
	}

	public String getCode() {
		return CODE;
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
