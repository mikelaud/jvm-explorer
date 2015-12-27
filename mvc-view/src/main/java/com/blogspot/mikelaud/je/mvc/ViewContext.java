package com.blogspot.mikelaud.je.mvc;

import com.blogspot.mikelaud.je.model.Model;

public interface ViewContext {

	Model getModel();
	Controller getController();
	
}
