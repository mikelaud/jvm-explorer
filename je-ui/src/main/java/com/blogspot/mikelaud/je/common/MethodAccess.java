package com.blogspot.mikelaud.je.common;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.image.Image;

public enum MethodAccess {

	Private,
	Default,
	Protected,
	Public;
	
	private static interface Const {
		Path IMAGES_PATH = Paths.get("method", "access");
		String IMAGE_EXT = ".png";
	}
	
	private final Path IMAGE_PATH;
	private final Image IMAGE;
	
	private MethodAccess() {
		String imageFilename = name().toLowerCase() + Const.IMAGE_EXT;
		IMAGE_PATH = Const.IMAGES_PATH.resolve(imageFilename);
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
