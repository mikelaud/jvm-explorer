package com.blogspot.mikelaud.je.mvc.impl;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.google.inject.Inject;

public class MvcModelImpl implements MvcModel {

	private final Core CORE;
	
	@Inject
	private MvcModelImpl(Core aCore) {
		CORE = aCore;
	}

	@Override
	public final Domain getDomain() {
		return CORE.getDomain();
	}

	@Override
	public final Core getCore() {
		return CORE;
	}
	
}
