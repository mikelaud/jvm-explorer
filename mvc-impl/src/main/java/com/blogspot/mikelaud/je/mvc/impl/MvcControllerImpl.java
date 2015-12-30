package com.blogspot.mikelaud.je.mvc.impl;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.domain.pojo.Type;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.mvc.MvcView;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MvcControllerImpl implements MvcController {

	private final MvcModel MODEL;
	private final Provider<MvcView> VIEW_PROVIDER;
	private MvcView mView;

	@Inject
	private MvcControllerImpl
	(	MvcModel aModel
	,	Provider<MvcView> aViewProvider) {
		MODEL = aModel;
		VIEW_PROVIDER = aViewProvider;
		mView = null;
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
	public void showApplication() {
		if (null == mView) {
			mView = VIEW_PROVIDER.get();
			mView.show();
		}
	}

	@Override
	public void showTypeCode(Type aType) {
		// TODO Auto-generated method stub	
	}
	
}
