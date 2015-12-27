package com.blogspot.mikelaud.je.mvc;

import com.google.inject.Inject;

public class ViewContextImpl implements ViewContext {

	private final Model MODEL;
	private final Controller CONTROLLER;
	
	@Inject
	private ViewContextImpl
	(	Model aModel
	,	Controller aController
	) {
		MODEL = aModel;
		CONTROLLER = aController;
	}

	@Override
	public final Model getModel() {
		return MODEL;
	}

	@Override
	public final Controller getController() {
		return CONTROLLER;
	}

}
