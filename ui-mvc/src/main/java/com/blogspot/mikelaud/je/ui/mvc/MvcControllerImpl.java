package com.blogspot.mikelaud.je.ui.mvc;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.domain.types.Type;
import com.blogspot.mikelaud.je.ui.api.MvcController;
import com.blogspot.mikelaud.je.ui.api.MvcModel;
import com.google.inject.Inject;

public class MvcControllerImpl implements MvcController {

	private final MvcModel MODEL;
	
	@Inject
	private MvcControllerImpl(MvcModel aModel) {
		MODEL = aModel;
	}

	@Override
	public final Domain getDomain() {
		return MODEL.getDomain();
	}

	@Override
	public final Core getCore() {
		return MODEL.getCore();
	}

	@Override
	public final MvcModel getModel() {
		return MODEL;
	}

	@Override
	public void showTypeCode(Type aType) {
		// TODO Auto-generated method stub	
	}
	
}
