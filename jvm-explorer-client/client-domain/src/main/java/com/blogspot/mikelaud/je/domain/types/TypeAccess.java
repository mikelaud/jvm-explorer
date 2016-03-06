package com.blogspot.mikelaud.je.domain.types;

public enum TypeAccess {

	Public,
	Protected,
	Default,
	Private;

	private final String PATH;
	
	private TypeAccess() {
		PATH = name().toLowerCase();
	}
	
	public final String getPath() {
		return PATH;
	}
	
	public final String getCode() {
		return (this == Default ? "" : PATH);
	}

}
