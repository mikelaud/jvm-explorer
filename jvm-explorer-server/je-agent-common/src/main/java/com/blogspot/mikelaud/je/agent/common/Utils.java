package com.blogspot.mikelaud.je.agent.common;

public class Utils {

	public static <T> T requireNonNull(T aObject) {
		if (aObject == null) {
			throw new NullPointerException();
		}
		return aObject;
	}

	public static String nvl(String aString) {
		return (null == aString ? "" : aString);
	}

}
