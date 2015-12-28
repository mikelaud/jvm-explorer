package com.blogspot.mikelaud.je.ui;

import com.blogspot.mikelaud.je.core.Core;
import com.blogspot.mikelaud.je.domain.Domain;

public interface MvcController {

	Domain getDomain(); 
	Core getCore(); 
	MvcModel getModel();
	
}
