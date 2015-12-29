package com.blogspot.mikelaud.je.ui.program;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;

import com.google.inject.BindingAnnotation;

public interface UiProgramConst {

	String getProgramTitle();
	Path getProgramIcon();
	//
	double getScaleWidth();
	double getScaleHeight();
	//
	String getEmptyHint();
	
	//------------------------------------------------------------------------
	
	@BindingAnnotation @Target({ElementType.FIELD, ElementType.PARAMETER}) @Retention(RetentionPolicy.RUNTIME)
	@interface ProgramTitle {}
	//
	@BindingAnnotation @Target({ElementType.FIELD, ElementType.PARAMETER}) @Retention(RetentionPolicy.RUNTIME)
	@interface ProgramIcon {}
	//
	//
	@BindingAnnotation @Target({ElementType.FIELD, ElementType.PARAMETER}) @Retention(RetentionPolicy.RUNTIME)
	@interface ScaleWidth {}
	//
	@BindingAnnotation @Target({ElementType.FIELD, ElementType.PARAMETER}) @Retention(RetentionPolicy.RUNTIME)
	@interface ScaleHeight {}
	//
	//
	@BindingAnnotation @Target({ElementType.FIELD, ElementType.PARAMETER}) @Retention(RetentionPolicy.RUNTIME)
	@interface EmptyHint {}	
	
}
