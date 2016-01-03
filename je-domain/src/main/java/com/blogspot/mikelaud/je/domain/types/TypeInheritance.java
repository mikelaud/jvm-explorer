package com.blogspot.mikelaud.je.domain.types;

public enum TypeInheritance {

	No,
	Abstract,
	Final;
	
	private final String PATH;
	
	private TypeInheritance() {
		PATH = name().toLowerCase();
	}
	
	public final String getPath() {
		return PATH;
	}

	public final String getCode() {
		return (this == No ? "" : PATH);
	}

}
