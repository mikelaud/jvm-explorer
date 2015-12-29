package com.blogspot.mikelaud.je.ui.resources;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.mvc.MvcController;
import com.google.inject.Inject;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class UiResourcesImpl implements UiResources {

	@SuppressWarnings("unused")
	private final MvcController CONTROLLER;
	private final Image EMPTY_IMAGE;

	@Inject
	private UiResourcesImpl(MvcController aController) {
		CONTROLLER = aController;
		EMPTY_IMAGE = new WritableImage(1, 1);
	}

	@Override
	public Image getImage(Path aPath) {
		return EMPTY_IMAGE;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
	}


}
