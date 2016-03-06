package com.blogspot.mikelaud.je.ui.resources;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.ssh.common.UnixPath;
import com.google.inject.Inject;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class UiResourcesImpl implements UiResources {

	private final MvcController CONTROLLER;
	private final Path IMAGES_LOCATION;
	private final Map<Path, Image> IMAGES;
	private final Image EMPTY_IMAGE;

	@Inject
	private UiResourcesImpl(MvcController aController) {
		CONTROLLER = aController;
		IMAGES_LOCATION = Paths.get("META-INF/resources/images");
		IMAGES = new HashMap<>();
		EMPTY_IMAGE = new WritableImage(1, 1);
	}

	private Image loadImage(Path aPath) {
		System.out.println("Load image: ".concat(aPath.toString()));
		Image image = EMPTY_IMAGE;
		try {
			UnixPath jarPath = new UnixPath(IMAGES_LOCATION.resolve(aPath));
			image = new Image(jarPath.toString());
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

	@Override
	public final void loadCss() {
		Scene scene = CONTROLLER.getModel().getStage().getScene();
		scene.getStylesheets().add("META-INF/resources/je.css");
	}

}
