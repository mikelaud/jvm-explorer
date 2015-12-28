package com.blogspot.mikelaud.je.domain.types;

public class Method {

	private String mName;
	private MethodAccess mAccess;
	
	public String getName() { return mName; }
	public MethodAccess getAccess() { return mAccess; }
	
	public void setName(String aName) { mName = aName; }
	public void setAccess(MethodAccess aAccess) { mAccess = aAccess; }
	
	public Method() {
		mName = "";
		mAccess = MethodAccess.Default;
	}
	
}
