package com.blogspot.mikelaud.je.domain.types;

public enum TypeModifier {

	Abstract,
	Final,
	Static;

	private final String PATH;
	
	private TypeModifier() {
		PATH = name().toLowerCase();
	}
	
	public final String getPath() {
		return PATH;
	}
	
	public final String getCode() {
		return PATH;
	}
	
}
