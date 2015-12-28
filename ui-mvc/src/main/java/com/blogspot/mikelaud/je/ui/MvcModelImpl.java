package com.blogspot.mikelaud.je.ui;

import com.blogspot.mikelaud.je.controller.Controller;
import com.blogspot.mikelaud.je.model.Model;
import com.google.inject.Inject;

public class MvcModelImpl implements MvcModel {

	private final Controller CONTROLLER;
	
	@Inject
	private MvcModelImpl(Controller aController) {
		CONTROLLER = aController;
	}

	@Override
	public final Model getModel() {
		return CONTROLLER.getModel();
	}

	@Override
	public final Controller getController() {
		return CONTROLLER;
	}
	
}
