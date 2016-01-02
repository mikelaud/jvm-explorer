package com.blogspot.mikelaud.je.utils;

import org.objectweb.asm.Type;

public class TypeUtils {

	public static boolean isArray(Type aType) {
		return (Type.ARRAY == aType.getSort());
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
