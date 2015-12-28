package com.blogspot.mikelaud.je.ui;

import com.blogspot.mikelaud.je.controller.Controller;
import com.blogspot.mikelaud.je.model.Model;

public interface MvcModel {

	Model getModel();
	Controller getController();
	
}
