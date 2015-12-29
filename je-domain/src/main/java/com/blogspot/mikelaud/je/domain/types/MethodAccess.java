package com.blogspot.mikelaud.je.domain.types;

public enum MethodAccess {

	Public,
	Protected,
	Default,
	Private;
	
	private final String PATH;
	
	private MethodAccess() {
		PATH = name().toLowerCase();
	}

	public final String getPath() {
		return PATH;
	}
	
	public final String getCode() {
		return (this == Default ? "" : PATH);
	}
	
}
