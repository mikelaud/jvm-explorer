package com.blogspot.mikelaud.je.common;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class TypeVisitor extends ClassVisitor {

	private Type mType;
	
	private boolean isAnnotation(int aAccess) { return 0 != (aAccess & Opcodes.ACC_ANNOTATION); }
	private boolean isEnum(int aAccess) { return 0 != (aAccess & Opcodes.ACC_ENUM); }
	private boolean isInterface(int aAccess) { return 0 != (aAccess & Opcodes.ACC_INTERFACE); }

	private void setName(String aName) {
		mType.setName(aName);
		mType.setName(mType.getName().replace('/', '.'));
	}
	
	private void setTypeType(int aAccess) {
		mType.setTypeType(TypeType.Class);
		for (;;) {
			if (isAnnotation(aAccess)) {
				mType.setTypeType(TypeType.Annotation);
				break;
			}
			if (isEnum(aAccess)) {
				mType.setTypeType(TypeType.Enum);
				break;
			}
			if (isInterface(aAccess)) {
				mType.setTypeType(TypeType.Interface);
				break;
			}
			break;
		}
	}
	
	public void reset() { mType = new Type(); }
	
	public Type getType() { return mType; }
	
	@Override
	public void visit(int aVersion, int aAccess, String aName, String aSignature, String aSuperName, String[] aInterfaces) {
		setName(aName);
		setTypeType(aAccess);
	}

	public TypeVisitor() {
		super(Opcodes.ASM5);
		reset();
	}

}
