package com.blogspot.mikelaud.je.domain.types;

public enum TypeStatic {

	No("non_static"),
	Yes("static");
	
	private final String PATH;
	
	private TypeStatic(String aPath) {
		PATH = aPath;
	}

	public final String getPath() {
		return PATH;
	}
	
}
