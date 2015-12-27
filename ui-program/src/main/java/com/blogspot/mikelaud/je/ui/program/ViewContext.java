package com.blogspot.mikelaud.je.ui.program;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.model.Model;

public interface ViewContext {

	Model getModel();
	Core getController();
	
}
