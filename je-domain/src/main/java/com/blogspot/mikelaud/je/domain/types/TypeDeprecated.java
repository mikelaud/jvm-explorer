package com.blogspot.mikelaud.je.domain.types;

public enum TypeDeprecated {
	
	No("non_deprecated"),
	Yes("deprecated");
	
	private final String PATH;
	
	private TypeDeprecated(String aPath) {
		PATH = aPath;
	}

	public final String getPath() {
		return PATH;
	}

}
