package com.blogspot.mikelaud.je.ui.jvm;

import java.nio.file.Path;

import com.google.inject.Inject;

public class UiJvmConstImpl implements UiJvmConst {

	private final String NAME;
	private final Path BACKGROUND_IMAGE;

	@Inject
	private UiJvmConstImpl
	(	@UiJvmConst.Name String aName
	,	@UiJvmConst.BackgroundImage Path aBackgroundImage
	) {
		NAME = aName;
		BACKGROUND_IMAGE = aBackgroundImage;
	}

	@Override public final String getName() { return NAME; }
	@Override public final Path getBackgroundImage() { return BACKGROUND_IMAGE; }

}
