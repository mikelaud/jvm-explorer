package com.blogspot.mikelaud.je.mvc.impl;

import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcView;
import com.blogspot.mikelaud.je.ui.program.UiProgram;
import com.google.inject.Inject;

public class MvcViewImpl implements MvcView {
	
	@SuppressWarnings("unused")
	private final MvcController CONTROLLER;
	private final UiProgram PROGRAM;
	
	@Inject
	private MvcViewImpl
	(	MvcController aController
	,	UiProgram aProgram
	) {
		CONTROLLER = aController;
		PROGRAM = aProgram;
	}

	@Override
	public final void show() {
		PROGRAM.show();
	}

	@Override
	public final void showCode(DomainType aType) {
		PROGRAM.showCode(aType);
	}
	
}
