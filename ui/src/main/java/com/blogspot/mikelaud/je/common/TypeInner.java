package com.blogspot.mikelaud.je.common;

public enum TypeInner {
	
	No("normal"),
	Yes("inner");
	
	private final String LABEL;
	
	private TypeInner(String aLabel) {
		LABEL = aLabel;
	}

	public String getLabel() {
		return LABEL;
	}
	
}
