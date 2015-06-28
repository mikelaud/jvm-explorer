package com.blogspot.mikelaud.je.common;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class TypeVisitor extends ClassVisitor {

	private Type mType;
	
	private boolean isAnnotation(int aAccess) { return 0 != (aAccess & Opcodes.ACC_ANNOTATION); }
	private boolean isEnum(int aAccess) { return 0 != (aAccess & Opcodes.ACC_ENUM); }
	private boolean isInterface(int aAccess) { return 0 != (aAccess & Opcodes.ACC_INTERFACE); }

	private boolean isPublic(int aAccess) { return 0 != (aAccess & Opcodes.ACC_PUBLIC); }
	private boolean isProtected(int aAccess) { return 0 != (aAccess & Opcodes.ACC_PROTECTED); }
	private boolean isPrivate(int aAccess) { return 0 != (aAccess & Opcodes.ACC_PRIVATE); }
	
	private void setFullName(String aName) {
		String fullname = aName.replace('/', '.');
		mType.setFullName(fullname);
	}
	
	private void setType(int aAccess) {
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
	
	private void setAccess(int aAccess) {
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
	
	public void reset() { mType = new Type(); }
	
	public Type getType() { return mType; }
	
	@Override
	public void visit(int aVersion, int aAccess, String aName, String aSignature, String aSuperName, String[] aInterfaces) {
		setFullName(aName);
		setType(aAccess);
		setAccess(aAccess);
	}

	@Override
	public void visitOuterClass(String aOwner, String aName, String aDesc) {
		mType.setInner(true);
	}
	
	public TypeVisitor() {
		super(Opcodes.ASM5);
		reset();
	}

}
