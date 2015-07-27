package com.blogspot.mikelaud.je.common;

public enum TypeInheritance {

	No,
	Abstract,
	Final;
		
	public String getLabel() {
		return name().toLowerCase();
	}
	
}
