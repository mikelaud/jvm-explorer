package com.blogspot.mikelaud.je.mvc.domain;

public enum TypeDeprecated {
	
	No("normal"),
	Yes("deprecated");
	
	private final String LABEL;
	
	private TypeDeprecated(String aLabel) {
		LABEL = aLabel;
	}

	public String getLabel() {
		return LABEL;
	}
	
}
