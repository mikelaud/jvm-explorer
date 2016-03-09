package com.blogspot.mikelaud.je.core;

import com.blogspot.mikelaud.je.domain.Domain;

public interface Core {

	Domain getDomain();
	void setDefaultTypes();
	String loadAgent(String aHost, String aName);

}
