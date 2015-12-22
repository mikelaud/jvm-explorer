package com.blogspot.mikelaud.je.common;

public class Method {

	private String mName;
	
	public String getName() { return mName; }
	
	public Method(String aName) {
		mName = aName;
	}
	
	public Method() {
		this("");
	}
	
}
