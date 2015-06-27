package com.blogspot.mikelaud.je.common;

public class Type {
	
	private String mName;
	private TypeType mTypeType;
	
	private String nvl(String aString) { return (null == aString ? "" : aString); }
	private TypeType nvl(TypeType aTypeType) { return (null == aTypeType ? TypeType.Class : aTypeType); }
	
	public String getName() { return mName; }
	public TypeType getTypeType() { return mTypeType; }
	
	public void setName(String aName) { mName = nvl(aName); }
	public void setTypeType(TypeType aTypeType) { mTypeType = nvl(aTypeType); }
	
	public Type() {
		mName = "";
		mTypeType = TypeType.Class;
	}
	
}
