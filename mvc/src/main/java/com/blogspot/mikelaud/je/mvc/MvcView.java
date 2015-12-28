package com.blogspot.mikelaud.je.mvc;

public interface MvcView {

	interface Factory {
		MvcView create(String[] args);
	}
	
	void show();
	
}
