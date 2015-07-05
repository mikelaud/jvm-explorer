package com.blogspot.mikelaud.je.common;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class TypeVisitor extends ClassVisitor {

	private Type mType;
	private String mTypeInternalName;
	
	private String getTypeName(String aTypeInternalName) {
		return aTypeInternalName.substring(aTypeInternalName.lastIndexOf('.') + 1);
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
		mType.setFullName(BytecodeUtils.toTypeFullname(aName));
		mType.setName(getTypeName(mType.getFullName()));
		mType.setType(BytecodeUtils.toTypeType(aAccess));
		mType.setAccess(BytecodeUtils.toTypeAccess(aAccess));
		mType.setDeprecated(BytecodeUtils.toDeprecated(aAccess));
	}

	@Override
	public void visitInnerClass(String aName, String aOuterName, String aInnerName, int aAccess) {
		if (mTypeInternalName.equals(aName)) {
			mType.setInner(TypeInner.Yes);
			mType.setType(BytecodeUtils.toTypeType(aAccess));
			mType.setAccess(BytecodeUtils.toTypeAccessInner(aAccess));
			mType.setDeprecated(BytecodeUtils.toDeprecated(aAccess));
		}
	}
	
	public TypeVisitor() {
		super(Opcodes.ASM5);
		reset();
	}

}
