package com.blogspot.mikelaud.je.ui.api;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.domain.types.Type;

public interface MvcController {

	Domain getDomain(); 
	Core getCore(); 
	MvcModel getModel();
	//
	void showTypeCode(Type aType);
	
}
