package com.blogspot.mikelaud.je.mvc.search;

import com.google.inject.Inject;

import javafx.scene.image.Image;

public class VSearchConstImpl implements VSearchConst {

	private final Image BACKGROUND_IMAGE;
	private final Image PACKAGE_ICON;
	//
	private final String SEARCH_LABEL;
	private final String MATCHING_LABEL;
	private final String COUNT_LABEL;
	//
	private final int SPACING;
	private final int PADDING;
	
	@Inject
	private VSearchConstImpl
	(	@VSearchConst.BackgroundImage Image aBackgroundImage
	,	@VSearchConst.PackageIcon Image aPackageIcon
	//
	,	@VSearchConst.SearchLabel String aSearchLabel
	,	@VSearchConst.MatchingLabel String aMatchingLabel
	,	@VSearchConst.CountLabel String aCountLabel
	//
	,	@VSearchConst.Spacing int aSpacing
	,	@VSearchConst.Padding int aPadding
	) {
		BACKGROUND_IMAGE = aBackgroundImage;
		PACKAGE_ICON = aPackageIcon;
		//
		SEARCH_LABEL = aSearchLabel;
		MATCHING_LABEL = aMatchingLabel;
		COUNT_LABEL = aCountLabel;
		//
		SPACING = aSpacing;
		PADDING = aPadding;
	}
	
	@Override public final Image getBackgroundImage() { return BACKGROUND_IMAGE; }
	@Override public final Image getPackageIcon() { return PACKAGE_ICON; }
	//
	@Override public final String getSearchLabel() { return SEARCH_LABEL; }
	@Override public final String getMatchingLabel() { return MATCHING_LABEL; }
	@Override public final String getCountLabel() { return COUNT_LABEL; }
	//
	@Override public final int getSpacing() { return SPACING; }
	@Override public final int getPadding() { return PADDING; }

}
