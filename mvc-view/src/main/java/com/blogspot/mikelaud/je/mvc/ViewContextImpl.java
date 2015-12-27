package com.blogspot.mikelaud.je.mvc;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.model.Model;
import com.google.inject.Inject;

public class ViewContextImpl implements ViewContext {

	private final Model MODEL;
	private final Core CONTROLLER;
	
	@Inject
	private ViewContextImpl
	(	Model aModel
	,	Core aController
	) {
		MODEL = aModel;
		CONTROLLER = aController;
	}

	@Override
	public final Model getModel() {
		return MODEL;
	}

	@Override
	public final Core getController() {
		return CONTROLLER;
	}

}
