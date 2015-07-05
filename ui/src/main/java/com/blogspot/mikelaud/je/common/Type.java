package com.blogspot.mikelaud.je.common;

public class Type {
	
	private String mName;
	private String mFullName;
	private TypeType mType;
	private TypeAccess mAccess;
	private TypeInner mInner;
	private TypeDeprecated mDeprecated;
	
	private TypeType nvl(TypeType aTypeType) { return (null == aTypeType ? TypeType.Class : aTypeType); }
	private TypeAccess nvl(TypeAccess aTypeAccess) { return (null == aTypeAccess ? TypeAccess.Default : aTypeAccess); }
	
	public String getName() { return mName; }
	public String getFullName() { return mFullName; }
	public TypeType getType() { return mType; }
	public TypeAccess getAccess() { return mAccess; }
	public TypeInner getInner() { return mInner; }
	public TypeDeprecated getDeprecated() { return mDeprecated; }
	
	public void setName(String aName) { mName = StringUtils.nvl(aName); }
	public void setFullName(String aFullName) { mFullName = StringUtils.nvl(aFullName); }
	public void setType(TypeType aTypeType) { mType = nvl(aTypeType); }
	public void setAccess(TypeAccess aTypeAccess) { mAccess = nvl(aTypeAccess); }
	public void setInner(TypeInner aInner) { mInner = aInner; }
	public void setDeprecated(TypeDeprecated aDeprecated) { mDeprecated = aDeprecated; }
	
	public Type() {
		mName = "";
		mFullName = "";
		mType = TypeType.Class;
		mAccess = TypeAccess.Default;
		mInner = TypeInner.No;
		mDeprecated = TypeDeprecated.No;
	}
	
}
