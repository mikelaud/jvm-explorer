package com.blogspot.mikelaud.je.common;

import org.objectweb.asm.Opcodes;

public interface BytecodeUtils {

	static String toTypeFullname(String aInnerName) {
		return StringUtils.nvl(aInnerName).replace('/', '.');
	}
	
	static boolean isAnnotation(int aAccess) { return 0 != (aAccess & Opcodes.ACC_ANNOTATION); }
	static boolean isEnum(int aAccess) { return 0 != (aAccess & Opcodes.ACC_ENUM); }
	static boolean isInterface(int aAccess) { return 0 != (aAccess & Opcodes.ACC_INTERFACE); }

	static boolean isPublic(int aAccess) { return 0 != (aAccess & Opcodes.ACC_PUBLIC); }
	static boolean isProtected(int aAccess) { return 0 != (aAccess & Opcodes.ACC_PROTECTED); }
	static boolean isPrivate(int aAccess) { return 0 != (aAccess & Opcodes.ACC_PRIVATE); }
	
	static TypeType toTypeType(int aAccess) {
		if (isInterface(aAccess)) return TypeType.Interface;
		else if (isEnum(aAccess)) return TypeType.Enum;
		else if (isAnnotation(aAccess)) return TypeType.Annotation;
		else return TypeType.Class;
	}
	
	static TypeAccess toTypeAccess(int aAccess) {
		if (isPublic(aAccess)) return TypeAccess.Public;
		else return TypeAccess.Default;
	}
	
	static TypeAccess toTypeAccessInner(int aAccess) {
		if (isPublic(aAccess)) return TypeAccess.Public;
		else if (isProtected(aAccess)) return TypeAccess.Protected;
		else if (isPrivate(aAccess)) return TypeAccess.Private;
		else return TypeAccess.Default;
	}
	
}
