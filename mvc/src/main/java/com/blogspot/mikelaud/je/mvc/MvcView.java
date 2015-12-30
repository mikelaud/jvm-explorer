package com.blogspot.mikelaud.je.mvc;

public interface MvcView {

	interface Factory {
		MvcView create(MvcController aController);
	}
	
	void show();
	
}
