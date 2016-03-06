package com.blogspot.mikelaud.je.domain.types;

public enum AccFinal {

	No("non_final"),
	Yes("final");
	
	private final String PATH;
	
	private AccFinal(String aPath) {
		PATH = aPath;
	}

	public final String getPath() {
		return PATH;
	}

	public final String getCode() {
		return (this == No ? "" : PATH);
	}

}
