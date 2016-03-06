package com.blogspot.mikelaud.je.mvc;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.domain.types.MethodAccess;
import com.blogspot.mikelaud.je.domain.types.TypeAccess;
import com.blogspot.mikelaud.je.domain.types.TypeDeprecated;
import com.blogspot.mikelaud.je.domain.types.TypeInheritance;
import com.blogspot.mikelaud.je.domain.types.TypeStatic;
import com.blogspot.mikelaud.je.domain.types.TypeType;

import javafx.application.Application.Parameters;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public interface MvcModel {

	Domain getDomain();
	Core getCore();
	//
	void setParameters(Parameters aParameters);
	Parameters getParameters();
	//
	void setStage(Stage aStage);
	Stage getStage();
	//
	Image getImage(Path aPath);
	Image getImage(MethodAccess aAccess);
	Image getImage(TypeDeprecated aDeprecated, TypeAccess aAccess, TypeType aType);
	Image getImage(TypeStatic aStatic, TypeInheritance aInheritance);
	Image getImage(TypeType aType);
	
}
