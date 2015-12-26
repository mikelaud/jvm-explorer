package com.blogspot.mikelaud.je.mvc;

import com.google.inject.Inject;

public class ViewImpl implements View {

	@SuppressWarnings("unused")
	private final Model MODEL;
	@SuppressWarnings("unused")
	private final Controller CONTROLLER;
	
	@Inject
	private ViewImpl
	(	Model aModel
	,	Controller aController
	) {
		MODEL = aModel;
		CONTROLLER = aController;
	}
	
}
