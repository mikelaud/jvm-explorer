package com.blogspot.mikelaud.je.mvc.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.domain.types.MethodAccess;
import com.blogspot.mikelaud.je.domain.types.TypeAccess;
import com.blogspot.mikelaud.je.domain.types.TypeDeprecated;
import com.blogspot.mikelaud.je.domain.types.TypeInheritance;
import com.blogspot.mikelaud.je.domain.types.TypeStatic;
import com.blogspot.mikelaud.je.domain.types.TypeType;
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
	public final Image getImage(Path aPath) {
		return RESOURCES.getImage(aPath);
	}

	@Override
	public final Image getImage(MethodAccess aAccess) {
		final String fileName = aAccess.getPath().concat(".png");
		final Path fileDir = Paths.get("method", "access");
		final Path filePath = fileDir.resolve(fileName);
		return getImage(filePath);
	}

	@Override
	public final Image getImage(TypeDeprecated aDeprecated, TypeAccess aAccess, TypeType aType) {
		final String fileName = aType.getPath().concat(".png");
		final Path fileDir = Paths.get("type", "access", aDeprecated.getPath(), aAccess.getPath()); 
		final Path filePath = fileDir.resolve(fileName); 
		return getImage(filePath);
	}

	@Override
	public final Image getImage(TypeStatic aStatic, TypeInheritance aInheritance) {
		final String fileName = aInheritance.getPath().concat(".png");
		final Path fileDir = Paths.get("type", "modifier", "combo");
		final Path filePath = fileDir.resolve(fileName); 
		return getImage(filePath);
	}
	
	@Override
	public final Image getImage(TypeType aType) {
		return getImage(TypeDeprecated.No, TypeAccess.Public, aType);
	}
	
}
