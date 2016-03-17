package com.blogspot.mikelaud.je.mvc.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.domain.types.MethodAccess;
import com.blogspot.mikelaud.je.domain.types.TypeAccess;
import com.blogspot.mikelaud.je.domain.types.TypeDeprecated;
import com.blogspot.mikelaud.je.domain.types.TypeInheritance;
import com.blogspot.mikelaud.je.domain.types.TypeStatic;
import com.blogspot.mikelaud.je.domain.types.TypeType;
import com.blogspot.mikelaud.je.mvc.MvcConst;
import com.blogspot.mikelaud.je.mvc.MvcModel;
import com.blogspot.mikelaud.je.ui.resources.UiResources;
import com.google.inject.Inject;

import javafx.application.Application.Parameters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MvcModelImpl implements MvcModel {

	private final MvcConst CONST;
	//
	private final Core CORE;
	private final UiResources RESOURCES;
	//
	private final ObservableList<JvmIdentity> JVM_LIST;
	private final SortedList<JvmIdentity> SORTED_JVM_LIST;
	//
	private Parameters mParameters;
	private Stage mStage;

	@Inject
	private MvcModelImpl
	(	MvcConst aConst
	,	Core aCore
	,	UiResources aResources
	) {
		CONST = aConst;
		//
		CORE = aCore;
		RESOURCES = aResources;
		//
		JVM_LIST = FXCollections.observableArrayList();
		SORTED_JVM_LIST = new SortedList<>(JVM_LIST, (a, b) -> a.getId().compareTo(b.getId()));
		//
		mParameters = null;
		mStage = null;
	}

	@Override public final MvcConst getConst() { return CONST; }

	@Override public final Domain getDomain() { return CORE.getDomain(); }
	@Override public final Core getCore() { return CORE; }

	@Override public void setParameters(Parameters aParameters) { mParameters = aParameters; }
	@Override public Parameters getParameters() { return mParameters; }

	@Override public void setStage(Stage aStage) { mStage = aStage; }
	@Override public Stage getStage() { return mStage; }

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
		final Path fileDir = Paths.get("type", "modifier", "combo", aStatic.getPath());
		final Path filePath = fileDir.resolve(fileName);
		return getImage(filePath);
	}

	@Override
	public final Image getImage(TypeType aType) {
		return getImage(TypeDeprecated.No, TypeAccess.Public, aType);
	}

	@Override
	public SortedList<JvmIdentity> getJvmList() {
		return SORTED_JVM_LIST;
	}

	@Override
	public void setJvmList(Collection<JvmIdentity> aJvmList) {
		JVM_LIST.setAll(aJvmList);
	}

}
