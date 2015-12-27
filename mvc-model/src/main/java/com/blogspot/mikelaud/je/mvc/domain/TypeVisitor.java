package com.blogspot.mikelaud.je.mvc.domain;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TypeVisitor extends ClassVisitor {

	private Type mType;
	private String mTypeInternalName;
	private List<Method> mMethods;
	
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
	
	private void setModifiers(int aAccess) {
		mType.setType(BytecodeUtils.toTypeType(aAccess));
		mType.setDeprecated(BytecodeUtils.toTypeDeprecated(aAccess));
		mType.setStatic(BytecodeUtils.toTypeStatic(aAccess));
		mType.setInheritance(BytecodeUtils.toTypeInheritance(aAccess));
	}
	
	public void reset() {
		mType = new Type();
		mTypeInternalName = "";
		mMethods = new ArrayList<>();
	}
	
	public Type getType() {
		return mType;
	}
	
	public List<Method> getMethods() {
		return mMethods;
	}
	
	@Override
	public void visit(int aVersion, int aAccess, String aName, String aSignature, String aSuperName, String[] aInterfaces) {
		mTypeInternalName = aName;
		mType.setFullName(BytecodeUtils.toTypeFullname(aName));
		mType.setAccess(BytecodeUtils.toTypeAccess(aAccess));
		setModifiers(aAccess);
		fillParsedNames(mType);
	}

	@Override
	public void visitInnerClass(String aName, String aOuterName, String aInnerName, int aAccess) {
		if (mTypeInternalName.equals(aName)) {
			mType.setInner(TypeInner.Yes);
			mType.setAccess(BytecodeUtils.toTypeAccessInner(aAccess));
			setModifiers(aAccess);
		}
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		Method method = new Method();
		method.setName(name);
		method.setAccess(BytecodeUtils.toMethodAccess(access));
		mMethods.add(method);
		return super.visitMethod(access, name, desc, signature, exceptions);
	}
	
	@Override
	public void visitEnd() {
		mType.setMethods(mMethods);
	}
	
	public TypeVisitor() {
		super(Opcodes.ASM5);
		reset();
	}

}
