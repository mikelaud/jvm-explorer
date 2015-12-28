package com.blogspot.mikelaud.je.ui;

import com.blogspot.mikelaud.je.controller.Controller;
import com.blogspot.mikelaud.je.model.Model;

public interface MvcController {

	Model getModel(); 
	Controller getController(); 
	MvcModel getMvcModel();
	
}
