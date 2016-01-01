package com.blogspot.mikelaud.je.utils;

import org.objectweb.asm.Type;

public class TypeUtils {

	public static boolean isObject(Type aType) {
		if (null == aType) {
			return false;
		}
		else {
			final int typeSort = aType.getSort();
			return (Type.OBJECT == typeSort || Type.ARRAY == typeSort);
		}
	}
	
}
