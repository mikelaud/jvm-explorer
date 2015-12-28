package com.blogspot.mikelaud.je.ui;

import com.blogspot.mikelaud.je.controller.Controller;
import com.blogspot.mikelaud.je.model.Model;
import com.google.inject.Inject;

public class MvcControllerImpl implements MvcController {

	private final MvcModel MVC_MODEL;
	
	@Inject
	private MvcControllerImpl(MvcModel aMvcModel) {
		MVC_MODEL = aMvcModel;
	}

	@Override
	public final Model getModel() {
		return MVC_MODEL.getModel();
	}

	@Override
	public final Controller getController() {
		return MVC_MODEL.getController();
	}

	@Override
	public final MvcModel getMvcModel() {
		return MVC_MODEL;
	}
	
}
