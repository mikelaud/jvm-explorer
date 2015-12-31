package com.blogspot.mikelaud.je.ui.resources;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.blogspot.mikelaud.je.mvc.MvcController;
import com.google.inject.Inject;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class UiResourcesImpl implements UiResources {

	@SuppressWarnings("unused")
	private final MvcController CONTROLLER;
	private final Map<Path, Image> IMAGES;
	private final Image EMPTY_IMAGE;

	@Inject
	private UiResourcesImpl(MvcController aController) {
		CONTROLLER = aController;
		IMAGES = new HashMap<>();
		EMPTY_IMAGE = new WritableImage(1, 1);
	}

	private Image loadImage(Path aPath) {
		System.out.println("Load image: ".concat(aPath.toString()));
		Image image = EMPTY_IMAGE;
		try {
			image = new Image(aPath.toString());
			IMAGES.put(aPath, image);
		}
		catch (IllegalArgumentException e) {
			IMAGES.put(aPath, EMPTY_IMAGE);
			System.out.println("Lost image: ".concat(aPath.toString()));
		}
		return image;
	}
	
	@Override
	public final Image getImage(Path aPath) {
		Image image = IMAGES.get(aPath);
		if (null == image) {
			image = loadImage(aPath);
		}
		return image;
	}

}
