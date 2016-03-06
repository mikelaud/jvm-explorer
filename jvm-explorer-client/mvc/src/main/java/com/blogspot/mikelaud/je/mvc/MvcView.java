package com.blogspot.mikelaud.je.mvc;

import com.blogspot.mikelaud.je.domain.pojo.DomainType;

public interface MvcView {

	void show();
	void showCode(DomainType aType);
	
}
