package com.blogspot.mikelaud.je.ui.code;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;

import com.google.inject.BindingAnnotation;

public interface UiCodeConst {

	Path getBackgroundImage();

	//------------------------------------------------------------------------

	@BindingAnnotation @Target({ElementType.FIELD, ElementType.PARAMETER}) @Retention(RetentionPolicy.RUNTIME)
	@interface BackgroundImage {}

}
