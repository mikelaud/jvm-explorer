package com.blogspot.mikelaud.je.common.utils;

import org.objectweb.asm.Type;

public class TypeUtils {

	public static boolean isArray(Type aType) {
		return (Type.ARRAY == aType.getSort());
	}

	public static String getPackage(Type aType) {
		if (null == aType) {
			return "";
		}
		else {
			final String className = aType.getClassName();
			final int lastIndex = className.lastIndexOf(".");
			if (lastIndex <= -1) {
				return "";
			}
			else {
				return className.substring(0, lastIndex);
			}
		}
	}
	
	public static String getName(Type aType) {
		if (null == aType) {
			return "";
		}
		else {
			final String className = aType.getClassName();
			final int lastIndex = className.lastIndexOf(".");
			if (lastIndex <= -1) {
				return className;
			}
			else {
				return className.substring(lastIndex + 1);
			}
		}
	}
	
	public static Type toElementarType(Type aType) {
		return (isArray(aType) ? aType.getElementType() : aType);
	}

	public static boolean isElementarTypeIsObject(Type aType) {
		return (Type.OBJECT == toElementarType(aType).getSort());
	}
	
	public static int getDimentionsCount(Type aType) {
		int dimentionsCount = 0;
		if (null != aType) {
			if (isArray(aType)) {
				dimentionsCount = aType.getDimensions();
			}
		}
		return dimentionsCount;
	}

	public static String toDimentions(Type aType) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < getDimentionsCount(aType); i++) {
			b.append("[]");
		}
		return b.toString();
	}

}
