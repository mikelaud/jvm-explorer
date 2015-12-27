package com.blogspot.mikelaud.je.domain;

public enum TypeInheritance {

	No,
	Abstract,
	Final;
		
	public String getLabel() {
		return name().toLowerCase();
	}
	
}
