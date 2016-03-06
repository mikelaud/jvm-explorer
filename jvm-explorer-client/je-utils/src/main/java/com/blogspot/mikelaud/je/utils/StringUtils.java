package com.blogspot.mikelaud.je.utils;

public interface StringUtils {

	static String nvl(String aString) {
		return (null == aString ? "" : aString);
	}
	
}
