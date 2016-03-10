package com.blogspot.mikelaud.je.ui.jvm;

import java.nio.file.Path;

import com.google.inject.Inject;

public class UiJvmConstImpl implements UiJvmConst {

	private final Path BACKGROUND_IMAGE;
	//
	private final int SPACING;
	private final int PADDING;

	@Inject
	private UiJvmConstImpl
	(	@UiJvmConst.BackgroundImage Path aBackgroundImage
	//
	,	@UiJvmConst.Spacing int aSpacing
	,	@UiJvmConst.Padding int aPadding
	) {
		BACKGROUND_IMAGE = aBackgroundImage;
		//
		SPACING = aSpacing;
		PADDING = aPadding;
	}

	@Override public final Path getBackgroundImage() { return BACKGROUND_IMAGE; }

	@Override public final int getSpacing() { return SPACING; }
	@Override public final int getPadding() { return PADDING; }

}
