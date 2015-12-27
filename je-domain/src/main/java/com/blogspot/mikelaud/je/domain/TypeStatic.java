package com.blogspot.mikelaud.je.domain;

public enum TypeStatic {

	No("non_static"),
	Yes("static");
	
	private final String LABEL;
	
	private TypeStatic(String aLabel) {
		LABEL = aLabel;
	}

	public String getLabel() {
		return LABEL;
	}
	
}
