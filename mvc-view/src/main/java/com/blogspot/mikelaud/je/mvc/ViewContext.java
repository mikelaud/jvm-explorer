package com.blogspot.mikelaud.je.mvc;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.model.Model;

public interface ViewContext {

	Model getModel();
	Core getController();
	
}
