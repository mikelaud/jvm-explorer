package com.blogspot.mikelaud.je.ui.api;

public interface MvcView {

	interface Factory {
		MvcView create(String[] args);
	}
	
	void show();
	
}
