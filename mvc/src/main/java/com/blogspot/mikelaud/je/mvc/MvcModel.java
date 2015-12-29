package com.blogspot.mikelaud.je.mvc;

import java.nio.file.Path;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;

import javafx.scene.image.Image;

public interface MvcModel {

	Domain getDomain();
	Core getCore();
	Image getImage(Path aPath);
	
}
