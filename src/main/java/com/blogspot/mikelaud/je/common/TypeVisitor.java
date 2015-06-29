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
		mType.setType(TypeType.Class);
		for (;;) {
			if (isInterface(aAccess)) {
				mType.setType(TypeType.Interface);
				break;
			}
			if (isEnum(aAccess)) {
				mType.setType(TypeType.Enum);
				break;
			}
			if (isAnnotation(aAccess)) {
				mType.setType(TypeType.Annotation);
				break;
			}
			break;
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
		mType.setAccess(TypeAccess.Default);
		for (;;) {
			if (isPublic(aAccess)) {
				mType.setAccess(TypeAccess.Public);
				break;
			}
			if (isProtected(aAccess)) {
				mType.setAccess(TypeAccess.Protected);
				break;
			}
			if (isPrivate(aAccess)) {
				mType.setAccess(TypeAccess.Private);
				break;
			}			
			break;
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
