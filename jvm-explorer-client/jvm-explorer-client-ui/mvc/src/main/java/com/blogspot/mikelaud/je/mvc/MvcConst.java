package com.blogspot.mikelaud.je.mvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

public interface MvcConst {

	int getPadding();

	//------------------------------------------------------------------------

	@BindingAnnotation @Target({ElementType.FIELD, ElementType.PARAMETER}) @Retention(RetentionPolicy.RUNTIME)
	@interface Padding {}

}
