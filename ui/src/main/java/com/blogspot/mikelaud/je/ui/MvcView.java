package com.blogspot.mikelaud.je.ui;

public interface MvcView {

	interface Factory {
		MvcView create(String[] args);
	}
	
	void show();
	
}
