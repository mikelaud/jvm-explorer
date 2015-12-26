package com.blogspot.mikelaud.je.mvc;

import com.google.inject.Inject;

public class ViewContextImpl implements ViewContext {

	private final Model MODEL;
	private final Controller CONTROLLER;
	private final ViewUtils UTILS;
	
	@Inject
	private ViewContextImpl
	(	Model aModel
	,	Controller aController
	,	ViewUtils aUtils
	) {
		MODEL = aModel;
		CONTROLLER = aController;
		UTILS = aUtils;
	}

	@Override
	public Model getModel() {
		return MODEL;
	}

	@Override
	public Controller getController() {
		return CONTROLLER;
	}

	@Override
	public ViewUtils getUtils() {
		return UTILS;
	}
	
}
