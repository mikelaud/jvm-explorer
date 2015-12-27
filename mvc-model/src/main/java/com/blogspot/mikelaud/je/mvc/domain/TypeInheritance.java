package com.blogspot.mikelaud.je.mvc.domain;

public enum TypeInheritance {

	No,
	Abstract,
	Final;
		
	public String getLabel() {
		return name().toLowerCase();
	}
	
}
