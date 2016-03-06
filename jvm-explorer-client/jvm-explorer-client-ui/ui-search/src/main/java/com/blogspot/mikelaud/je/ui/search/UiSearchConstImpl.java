package com.blogspot.mikelaud.je.ui.search;

import java.nio.file.Path;

import com.google.inject.Inject;

public class UiSearchConstImpl implements UiSearchConst {

	private final Path BACKGROUND_IMAGE;
	private final Path PACKAGE_ICON;
	//
	private final String SEARCH_LABEL;
	private final String MATCHING_LABEL;
	private final String COUNT_LABEL;
	//
	private final int SPACING;
	private final int PADDING;
	
	@Inject
	private UiSearchConstImpl
	(	@UiSearchConst.BackgroundImage Path aBackgroundImage
	,	@UiSearchConst.PackageIcon Path aPackageIcon
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
	
	@Override public final Path getBackgroundImage() { return BACKGROUND_IMAGE; }
	@Override public final Path getPackageIcon() { return PACKAGE_ICON; }
	//
	@Override public final String getSearchLabel() { return SEARCH_LABEL; }
	@Override public final String getMatchingLabel() { return MATCHING_LABEL; }
	@Override public final String getCountLabel() { return COUNT_LABEL; }
	//
	@Override public final int getSpacing() { return SPACING; }
	@Override public final int getPadding() { return PADDING; }

}
