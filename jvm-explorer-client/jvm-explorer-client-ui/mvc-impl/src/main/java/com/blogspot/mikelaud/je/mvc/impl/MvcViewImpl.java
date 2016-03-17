package com.blogspot.mikelaud.je.mvc.impl;

import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcView;
import com.blogspot.mikelaud.je.ui.jvm.UiJvm;
import com.blogspot.mikelaud.je.ui.program.UiProgram;
import com.google.inject.Inject;

public class MvcViewImpl implements MvcView {

	@SuppressWarnings("unused")
	private final MvcController CONTROLLER;
	private final UiProgram PROGRAM;
	private final UiJvm JVM;

	@Inject
	private MvcViewImpl
	(	MvcController aController
	,	UiProgram aProgram
	,	UiJvm aJvm
	) {
		CONTROLLER = aController;
		PROGRAM = aProgram;
		JVM = aJvm;
	}

	@Override
	public final void show() {
		PROGRAM.show();
	}

	@Override
	public final void showCode(DomainType aType) {
		PROGRAM.showCode(aType);
	}

	@Override
	public String getJvmHost() {
		return JVM.getHost();
	}

}
