package com.blogspot.mikelaud.je.common;

import com.blogspot.mikelaud.je.common.file_source.FileSource;
import com.blogspot.mikelaud.je.common.file_source.FileSourceFactory;
import com.blogspot.mikelaud.je.common.file_source.FileSourceImpl;
import com.blogspot.mikelaud.je.common.file_source.FileSourceJar;
import com.blogspot.mikelaud.je.common.file_source.FileSourceJarImpl;
import com.blogspot.mikelaud.je.common.file_source.content.FileContent;
import com.blogspot.mikelaud.je.common.file_source.content.FileContentFactory;
import com.blogspot.mikelaud.je.common.file_source.content.FileContentImpl;
import com.blogspot.mikelaud.je.common.file_source.content.FileContentJar;
import com.blogspot.mikelaud.je.common.file_source.content.FileContentJarImpl;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class CommonModule extends AbstractModule {

	@Override
	protected void configure() {
		//
		install(new FactoryModuleBuilder()
			.implement(FileSource.class, FileSourceImpl.class)
			.implement(FileSourceJar.class, FileSourceJarImpl.class)
		.build(FileSourceFactory.class));
		//
		install(new FactoryModuleBuilder()
			.implement(FileContent.class, FileContentImpl.class)
			.implement(FileContentJar.class, FileContentJarImpl.class)
		.build(FileContentFactory.class));
	}

}
