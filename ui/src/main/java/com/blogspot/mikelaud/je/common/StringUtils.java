package com.blogspot.mikelaud.je.common;

public interface StringUtils {

	static String nvl(String aString) {
		return (null == aString ? "" : aString);
	}
	
}
