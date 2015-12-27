package com.blogspot.mikelaud.je.ui.search;

import com.google.inject.Inject;

import javafx.scene.image.Image;

public class UiSearchConstImpl implements UiSearchConst {

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
	private UiSearchConstImpl
	(	@UiSearchConst.BackgroundImage Image aBackgroundImage
	,	@UiSearchConst.PackageIcon Image aPackageIcon
	//
	,	@UiSearchConst.SearchLabel String aSearchLabel
	,	@UiSearchConst.MatchingLabel String aMatchingLabel
	,	@UiSearchConst.CountLabel String aCountLabel
	//
	,	@UiSearchConst.Spacing int aSpacing
	,	@UiSearchConst.Padding int aPadding
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
