package com.blogspot.mikelaud.je.mvc;

import com.google.inject.Inject;

import javafx.scene.image.Image;

public class ViewConstImpl implements ViewConst {

	private final String PROGRAM_TITLE;
	private final Image PROGRAM_ICON;
	//
	private final double SCALE_WIDTH;
	private final double SCALE_HEIGHT;
	//
	private final String EMPTY_HINT;
	
	@Inject
	private ViewConstImpl
	(	@ViewConst.ProgramTitle String aProgramTitle
	,	@ViewConst.ProgramIcon Image aProgramIcon
	//
	,	@ViewConst.ScaleWidth double aScaleWidth
	,	@ViewConst.ScaleHeight double aScaleHeight
	//
	,	@ViewConst.EmptyHint String aEmptyHint
	) {
		PROGRAM_TITLE = aProgramTitle;
		PROGRAM_ICON = aProgramIcon;
		//
		SCALE_WIDTH = aScaleWidth;
		SCALE_HEIGHT = aScaleHeight;
		//
		EMPTY_HINT = aEmptyHint;
	}
	
	@Override public final String getProgramTitle() { return PROGRAM_TITLE; }
	@Override public final Image getProgramIcon() { return PROGRAM_ICON; }
	//
	@Override public final double getScaleWidth() { return SCALE_WIDTH; }
	@Override public final double getScaleHeight() { return SCALE_HEIGHT; }
	//
	@Override public final String getEmptyHint() { return EMPTY_HINT; }

}
