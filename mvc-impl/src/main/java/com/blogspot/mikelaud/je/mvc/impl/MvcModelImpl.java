package com.blogspot.mikelaud.je.mvc.impl;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.resources.UiResources;
import com.google.inject.Inject;

import javafx.scene.image.Image;

public class MvcModelImpl implements MvcModel {

	private final Core CORE;
	private final UiResources RESOURCES;
	
	@Inject
	private MvcModelImpl
	(	Core aCore
	,	UiResources aResources
	) {
		CORE = aCore;
		RESOURCES = aResources;
	}

	@Override
	public final Domain getDomain() {
		return CORE.getDomain();
	}

	@Override
	public final Core getCore() {
		return CORE;
	}

	@Override
	public Image getImage(Path aPath) {
		return RESOURCES.getImage(aPath);
	}
	
}
