package com.blogspot.mikelaud.je.ui.code;

import java.nio.file.Path;

import com.google.inject.Inject;

public class UiCodeConstImpl implements UiCodeConst {

	private final Path BACKGROUND_IMAGE;

	@Inject
	private UiCodeConstImpl
	(	@UiCodeConst.BackgroundImage Path aBackgroundImage
	) {
		BACKGROUND_IMAGE = aBackgroundImage;
	}

	@Override public final Path getBackgroundImage() { return BACKGROUND_IMAGE; }

}
