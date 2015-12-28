package com.blogspot.mikelaud.je.domain.types;

public enum TypeInheritance {

	No,
	Abstract,
	Final;
		
	public String getLabel() {
		return name().toLowerCase();
	}
	
}
