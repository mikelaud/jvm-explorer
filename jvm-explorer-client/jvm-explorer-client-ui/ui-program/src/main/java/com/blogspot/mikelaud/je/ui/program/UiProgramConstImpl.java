package com.blogspot.mikelaud.je.ui.program;

import java.nio.file.Path;

import com.google.inject.Inject;

public class UiProgramConstImpl implements UiProgramConst {

	private final String PROGRAM_TITLE;
	private final Path PROGRAM_ICON;
	//
	private final double SCALE_WIDTH;
	private final double SCALE_HEIGHT;
	//
	private final String EMPTY_HINT;
	
	@Inject
	private UiProgramConstImpl
	(	@UiProgramConst.ProgramTitle String aProgramTitle
	,	@UiProgramConst.ProgramIcon Path aProgramIcon
	//
	,	@UiProgramConst.ScaleWidth double aScaleWidth
	,	@UiProgramConst.ScaleHeight double aScaleHeight
	//
	,	@UiProgramConst.EmptyHint String aEmptyHint
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
	@Override public final Path getProgramIcon() { return PROGRAM_ICON; }
	//
	@Override public final double getScaleWidth() { return SCALE_WIDTH; }
	@Override public final double getScaleHeight() { return SCALE_HEIGHT; }
	//
	@Override public final String getEmptyHint() { return EMPTY_HINT; }

}
