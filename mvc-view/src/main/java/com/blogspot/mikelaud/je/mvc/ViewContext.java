package com.blogspot.mikelaud.je.mvc;

public interface ViewContext {

	Model getModel();
	Controller getController();
	ViewUtils getUtils();
	
}
