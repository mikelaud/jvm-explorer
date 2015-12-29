package com.blogspot.mikelaud.je.domain.types;

public enum TypeType {

	Annotation,
	Class,
	Enum,
	Interface;

	private final String PATH;
	
	private TypeType() {
		PATH = name().toLowerCase();
	}
	
	public final String getPath() {
		return PATH;
	}
	
	public final String getCode() {
		return (this == Annotation ? "@interface" : PATH);
	}
	
}
