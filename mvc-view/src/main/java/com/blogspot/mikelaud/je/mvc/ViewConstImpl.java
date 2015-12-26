package com.blogspot.mikelaud.je.mvc;

import com.google.inject.Inject;

public class ViewConstImpl implements ViewConst {

	private final String PROGRAM_TITLE;
	private final String PROGRAM_ICON;
	private final String EMPTY_HINT;
	private final double SCALE_WIDTH;
	private final double SCALE_HEIGHT;
	
	@Inject
	private ViewConstImpl
	(	@ViewConst.ProgramTitle String aProgramTitle
	,	@ViewConst.ProgramIcon String aProgramIcon
	,	@ViewConst.EmptyHint String aEmptyHint
	,	@ViewConst.ScaleWidth double aScaleWidth
	,	@ViewConst.ScaleHeight double aScaleHeight
	) {
		PROGRAM_TITLE = aProgramTitle;
		PROGRAM_ICON = aProgramIcon;
		EMPTY_HINT = aEmptyHint;
		SCALE_WIDTH = aScaleWidth;
		SCALE_HEIGHT = aScaleHeight;
	}
	
	@Override public String getProgramTitle() { return PROGRAM_TITLE; }
	@Override public String getProgramIcon() { return PROGRAM_ICON; }
	@Override public String getEmptyHint() { return EMPTY_HINT; }
	@Override public double getScaleWidth() { return SCALE_WIDTH; }
	@Override public double getScaleHeight() { return SCALE_HEIGHT; }

}
