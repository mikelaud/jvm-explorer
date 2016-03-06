package com.blogspot.mikelaud.je.ui.code;

import java.nio.file.Path;

import com.google.inject.Inject;

public class UiCodeConstImpl implements UiCodeConst {

	private final Path BACKGROUND_IMAGE;
	//
	private final int SPACING;
	private final int PADDING;
	
	@Inject
	private UiCodeConstImpl
	(	@UiCodeConst.BackgroundImage Path aBackgroundImage
	//
	,	@UiCodeConst.Spacing int aSpacing
	,	@UiCodeConst.Padding int aPadding
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
