package com.blogspot.mikelaud.je.common;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class TypeVisitor extends ClassVisitor {

	private Type mType;
	private String mTypeInternalName;
	
	private void fillParsedNames(Type aType) {
		String fullName = aType.getFullName();
		int dotIndex = fullName.lastIndexOf('.');
		//
		String name = "";
		String packageName = "";
		if (dotIndex >= 0) {
			int nameIndex = dotIndex + 1;
			if (nameIndex >= fullName.length()) {
				name = "";
				packageName = fullName;
			}
			else {
				name = fullName.substring(nameIndex);
				packageName = fullName.substring(0, dotIndex);
			}
		}
		else {
			name = fullName;
			packageName = "";
		}
		aType.setName(name);
		aType.setNameLowCase(name.toLowerCase());
		aType.setPackageName(packageName);
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
		mType.setType(BytecodeUtils.toTypeType(aAccess));
		mType.setAccess(BytecodeUtils.toTypeAccess(aAccess));
		mType.setDeprecated(BytecodeUtils.toDeprecated(aAccess));
		fillParsedNames(mType);
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
