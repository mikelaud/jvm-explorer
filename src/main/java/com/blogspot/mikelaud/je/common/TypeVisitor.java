package com.blogspot.mikelaud.je.common;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class TypeVisitor extends ClassVisitor {

	private Type mType;
	private String mTypeInternalName;
	
	private boolean isAnnotation(int aAccess) { return 0 != (aAccess & Opcodes.ACC_ANNOTATION); }
	private boolean isEnum(int aAccess) { return 0 != (aAccess & Opcodes.ACC_ENUM); }
	private boolean isInterface(int aAccess) { return 0 != (aAccess & Opcodes.ACC_INTERFACE); }

	private boolean isPublic(int aAccess) { return 0 != (aAccess & Opcodes.ACC_PUBLIC); }
	private boolean isProtected(int aAccess) { return 0 != (aAccess & Opcodes.ACC_PROTECTED); }
	private boolean isPrivate(int aAccess) { return 0 != (aAccess & Opcodes.ACC_PRIVATE); }
	
	private void setTypeFullName(String aName) {
		String fullname = aName.replace('/', '.');
		mType.setFullName(fullname);
	}
	
	private void setTypeType(int aAccess) {
		if (isInterface(aAccess)) {
			mType.setType(TypeType.Interface);
		}
		else if (isEnum(aAccess)) {
			mType.setType(TypeType.Enum);
		}
		else if (isAnnotation(aAccess)) {
			mType.setType(TypeType.Annotation);
		}
		else {
			mType.setType(TypeType.Class);
		}
	}
	
	private void setTypeAccess(int aAccess) {
		if (isPublic(aAccess)) {
			mType.setAccess(TypeAccess.Public);
		}
		else {
			mType.setAccess(TypeAccess.Default);
		}
	}
	
	private void setTypeAccessInner(int aAccess) {
		if (isPublic(aAccess)) {
			mType.setAccess(TypeAccess.Public);
		}
		else if (isProtected(aAccess)) {
			mType.setAccess(TypeAccess.Protected);
		}
		else if (isPrivate(aAccess)) {
			mType.setAccess(TypeAccess.Private);
		}
		else {
			mType.setAccess(TypeAccess.Default);
		}
	}
	
	private void setTypeInner() {
		mType.setInner(true);
	}
	
	public void reset() {
		mType = new Type();
		mTypeInternalName = "";
	}
	
	public Type getType() {
		return mType;
	}
	
	@Override
	public void visit(int aVersion, int aAccess, String aName, String aSignature, String aSuperName, String[] aInterfaces) {
		mTypeInternalName = aName;
		setTypeFullName(aName);
		setTypeType(aAccess);
		setTypeAccess(aAccess);
	}

	@Override
	public void visitInnerClass(String aName, String aOuterName, String aInnerName, int aAccess) {
		if (mTypeInternalName.equals(aName)) {
			setTypeInner();
			setTypeAccessInner(aAccess);
		}
	}
	
	public TypeVisitor() {
		super(Opcodes.ASM5);
		reset();
	}

}
