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
	
	static boolean isDeprecated(int aAccess) { return 0 != (aAccess & Opcodes.ACC_DEPRECATED); }
	static boolean isStatic(int aStatic) { return 0 != (aStatic & Opcodes.ACC_STATIC); }

	static boolean isAbstract(int aAccess) { return 0 != (aAccess & Opcodes.ACC_ABSTRACT); }
	static boolean isFinal(int aAccess) { return 0 != (aAccess & Opcodes.ACC_FINAL); }

	static TypeDeprecated toTypeDeprecated(int aAccess) {
		return isDeprecated(aAccess) ? TypeDeprecated.Yes : TypeDeprecated.No;
	}

	static TypeStatic toTypeStatic(int aStatic) {
		return isStatic(aStatic) ? TypeStatic.Yes : TypeStatic.No;
	}

	static TypeInheritance toTypeInheritance(int aAccess) {
		if (isFinal(aAccess)) return TypeInheritance.Final;
		else if (isAbstract(aAccess)) return TypeInheritance.Abstract;
		else return TypeInheritance.No;
	}
	
	static TypeType toTypeType(int aAccess) {
		if (isAnnotation(aAccess)) return TypeType.Annotation; // check annotation before interface
		else if (isInterface(aAccess)) return TypeType.Interface;
		else if (isEnum(aAccess)) return TypeType.Enum;
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
	
	static MethodAccess toMethodAccess(int aAccess) {
		if (isPublic(aAccess)) return MethodAccess.Public;
		else if (isProtected(aAccess)) return MethodAccess.Protected;
		else if (isPrivate(aAccess)) return MethodAccess.Private;
		else return MethodAccess.Default;
	}
	
}
