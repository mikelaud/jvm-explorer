package com.blogspot.mikelaud.je.mvc;

import com.google.inject.Inject;

public class ControllerImpl implements Controller {

	@SuppressWarnings("unused")
	private final Model MODEL;
	
	@Inject
	private ControllerImpl(Model aModel) {
		MODEL = aModel;
	}
	
}
