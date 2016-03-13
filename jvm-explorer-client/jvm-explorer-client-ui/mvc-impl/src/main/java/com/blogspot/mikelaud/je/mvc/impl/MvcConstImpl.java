package com.blogspot.mikelaud.je.mvc.impl;

import com.blogspot.mikelaud.je.mvc.MvcConst;
import com.google.inject.Inject;

public class MvcConstImpl implements MvcConst {

	private final int PADDING;

	@Inject
	private MvcConstImpl
	(	@MvcConst.Padding int aPadding
	) {
		PADDING = aPadding;
	}

	@Override public int getPadding() { return PADDING; }

}
