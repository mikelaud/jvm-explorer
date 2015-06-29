package com.blogspot.mikelaud.je.common;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class TypeVisitor extends ClassVisitor {

	private Type mType;
	private String mTypeInternalName;
	
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
		mType.setFullName(BytecodeUtils.toTypeFullname(aName));
		mType.setType(BytecodeUtils.toTypeType(aAccess));
		mType.setAccess(BytecodeUtils.toTypeAccess(aAccess));
	}

	@Override
	public void visitInnerClass(String aName, String aOuterName, String aInnerName, int aAccess) {
		if (mTypeInternalName.equals(aName)) {
			mType.setInner(true);
			mType.setAccess(BytecodeUtils.toTypeAccessInner(aAccess));
		}
	}
	
	public TypeVisitor() {
		super(Opcodes.ASM5);
		reset();
	}

}
