package com.blogspot.mikelaud.je.core.helper;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.blogspot.mikelaud.je.domain.pojo.DomainMethod;
import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.domain.types.TypeInner;

public class TypeVisitor extends ClassVisitor {

	private DomainType mType;
	private String mTypeInternalName;
	private List<DomainMethod> mMethods;
	
	private void fillParsedNames(DomainType aType) {
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
		mType.setTypeType(BytecodeHelper.toTypeType(aAccess));
		mType.setDeprecated(BytecodeHelper.toTypeDeprecated(aAccess));
		mType.setStatic(BytecodeHelper.toTypeStatic(aAccess));
		mType.setInheritance(BytecodeHelper.toTypeInheritance(aAccess));
	}
	
	public void reset() {
		mType = new DomainType();
		mTypeInternalName = "";
		mMethods = new ArrayList<>();
	}
	
	public DomainType getType() {
		return mType;
	}
	
	public List<DomainMethod> getMethods() {
		return mMethods;
	}
	
	@Override
	public void visit(int aVersion, int aAccess, String aName, String aSignature, String aSuperName, String[] aInterfaces) {
		mTypeInternalName = aName;
		mType.setFullName(BytecodeHelper.toTypeFullname(aName));
		mType.setAccess(BytecodeHelper.toTypeAccess(aAccess));
		setModifiers(aAccess);
		fillParsedNames(mType);
	}

	@Override
	public void visitInnerClass(String aName, String aOuterName, String aInnerName, int aAccess) {
		if (mTypeInternalName.equals(aName)) {
			mType.setInner(TypeInner.Yes);
			mType.setAccess(BytecodeHelper.toTypeAccessInner(aAccess));
			setModifiers(aAccess);
		}
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		DomainMethod method = new DomainMethod();
		method.setName(name);
		method.setAccess(BytecodeHelper.toMethodAccess(access));
		method.setFinal(BytecodeHelper.toAccFinal(access));
		method.setReturnType(Type.getReturnType(desc));
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
