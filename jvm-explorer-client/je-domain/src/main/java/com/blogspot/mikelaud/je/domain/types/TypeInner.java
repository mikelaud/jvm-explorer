package com.blogspot.mikelaud.je.domain.types;

public enum TypeInner {
	
	No("non_inner"),
	Yes("inner");
	
	private final String PATH;
	
	private TypeInner(String aPath) {
		PATH = aPath;
	}

	public final String getPath() {
		return PATH;
	}
	
}
