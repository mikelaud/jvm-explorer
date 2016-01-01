package com.blogspot.mikelaud.je.domain.pojo;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Type;

import com.blogspot.mikelaud.je.domain.types.AccFinal;
import com.blogspot.mikelaud.je.domain.types.TypeAccess;
import com.blogspot.mikelaud.je.domain.types.TypeDeprecated;
import com.blogspot.mikelaud.je.domain.types.TypeInheritance;
import com.blogspot.mikelaud.je.domain.types.TypeInner;
import com.blogspot.mikelaud.je.domain.types.TypeStatic;
import com.blogspot.mikelaud.je.domain.types.TypeType;

public class DomainType {
	
	private Type mType;
	private String mName;
	private String mNameLowCase;
	private String mPackageName;
	private String mFullName;
	private TypeType mTypeType;
	private TypeAccess mAccess;
	private AccFinal mFinal;
	private TypeInner mInner;
	private TypeDeprecated mDeprecated;
	private TypeInheritance mInheritance;
	private TypeStatic mStatic;
	private List<DomainMethod> mMethods;
	
	private TypeType nvl(TypeType aTypeType) { return (null == aTypeType ? TypeType.Class : aTypeType); }
	private TypeAccess nvl(TypeAccess aTypeAccess) { return (null == aTypeAccess ? TypeAccess.Default : aTypeAccess); }
	
	public Type getType() { return mType; }
	public String getName() { return mName; }
	public String getNameLowCase() { return mNameLowCase; }
	public String getPackageName() { return mPackageName; }
	public String getFullName() { return mFullName; }
	public TypeType getTypeType() { return mTypeType; }
	public TypeAccess getAccess() { return mAccess; }
	public AccFinal getFinal() { return mFinal; }
	public TypeInner getInner() { return mInner; }
	public TypeDeprecated getDeprecated() { return mDeprecated; }
	public TypeInheritance getInheritance() { return mInheritance; }
	public TypeStatic getStatic() { return mStatic; }
	public List<DomainMethod> getMethods() { return mMethods; }
	
	public void setType(Type aType) { mType = aType; }
	public void setName(String aName) { mName = aName; }
	public void setNameLowCase(String aNameLowCase) { mNameLowCase = aNameLowCase; }
	public void setPackageName(String aPackageName) { mPackageName = aPackageName; }
	public void setFullName(String aFullName) { mFullName = aFullName; }
	public void setTypeType(TypeType aTypeType) { mTypeType = nvl(aTypeType); }
	public void setAccess(TypeAccess aTypeAccess) { mAccess = nvl(aTypeAccess); }
	public void setFinal(AccFinal aFinal) { mFinal = aFinal; }
	public void setInner(TypeInner aInner) { mInner = aInner; }
	public void setDeprecated(TypeDeprecated aDeprecated) { mDeprecated = aDeprecated; }
	public void setInheritance(TypeInheritance aInheritance) { mInheritance = aInheritance; }
	public void setStatic(TypeStatic aStatic) { mStatic = aStatic; }
	public void setMethods(List<DomainMethod> aMethods) { mMethods = aMethods; }
	
	public DomainType() {
		mType = Type.VOID_TYPE;
		mName = "";
		mNameLowCase = "";
		mFullName = "";
		mTypeType = TypeType.Class;
		mAccess = TypeAccess.Default;
		mFinal = AccFinal.No;
		mInner = TypeInner.No;
		mDeprecated = TypeDeprecated.No;
		mInheritance = TypeInheritance.No;
		mStatic = TypeStatic.No;
		mMethods = new ArrayList<>();
	}
	
}
