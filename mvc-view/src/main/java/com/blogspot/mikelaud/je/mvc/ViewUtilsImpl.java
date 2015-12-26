package com.blogspot.mikelaud.je.mvc;

import com.google.inject.Inject;

import javafx.scene.image.Image;

public class ViewUtilsImpl implements ViewUtils {

	@Inject
	private ViewUtilsImpl() {
		// void
	}

	@Override
	public Image createImage(String aFilename) {
		return new Image(aFilename);
	}
	
}
