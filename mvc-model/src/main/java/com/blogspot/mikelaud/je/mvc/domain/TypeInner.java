package com.blogspot.mikelaud.je.mvc.domain;

public enum TypeInner {
	
	No("non_inner"),
	Yes("inner");
	
	private final String LABEL;
	
	private TypeInner(String aLabel) {
		LABEL = aLabel;
	}

	public String getLabel() {
		return LABEL;
	}
	
}
