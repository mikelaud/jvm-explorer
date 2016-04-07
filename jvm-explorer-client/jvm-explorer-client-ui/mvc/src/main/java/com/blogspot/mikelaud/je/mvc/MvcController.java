package com.blogspot.mikelaud.je.mvc;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.domain.pojo.DomainType;

public interface MvcController {

	Domain getDomain();
	Core getCore();
	MvcModel getModel();
	//
	void showApplication();
	void showCode(DomainType aType);
	//
	void deployAgent();

}
