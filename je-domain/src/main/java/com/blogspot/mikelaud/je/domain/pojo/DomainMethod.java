package com.blogspot.mikelaud.je.domain.pojo;

import org.objectweb.asm.Type;

import com.blogspot.mikelaud.je.domain.types.MethodAccess;

public class DomainMethod {

	private String mName;
	private MethodAccess mAccess;
	private Type mReturnType;
	
	public String getName() { return mName; }
	public MethodAccess getAccess() { return mAccess; }
	public Type getReturnType() { return mReturnType; }
	
	public void setName(String aName) { mName = aName; }
	public void setAccess(MethodAccess aAccess) { mAccess = aAccess; }
	public void setReturnType(Type aReturnType) { mReturnType = aReturnType; }
	
	public DomainMethod() {
		mName = "";
		mAccess = MethodAccess.Default;
		mReturnType = Type.getType(Void.class);
	}
	
}
