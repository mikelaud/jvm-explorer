package com.blogspot.mikelaud.je.ui.resources;

import com.blogspot.mikelaud.je.mvc.MvcController;

public class UiResourcesImpl implements UiResources {

	@SuppressWarnings("unused")
	private final MvcController CONTROLLER;

	public UiResourcesImpl(MvcController aMvcController) {
		CONTROLLER = aMvcController;
	}


}
