package com.blogspot.mikelaud.je.mvc;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.blogspot.mikelaud.je.mvc.domain.Bytecode;
import com.blogspot.mikelaud.je.mvc.domain.Type;
import com.google.inject.Inject;

public class ControllerImpl implements Controller {

	private static interface Const {
		//
		String JAVA_HOME_PROPERTY = "java.home";
		String JAVA_HOME_EMPTY = "";
		//
		String JAR_NAME = "rt.jar";
		String JAR_DIR = "lib";
		//
		String CLASS_EXT = ".class";
	}
	
	private final Model MODEL;
	
	@Inject
	private ControllerImpl(Model aModel) {
		MODEL = aModel;
	}

	private Path getJavaHome() {
		String javaHome = System.getProperty(Const.JAVA_HOME_PROPERTY);
		if (null == javaHome) { 
			javaHome = Const.JAVA_HOME_EMPTY;
		}
		return Paths.get(javaHome);
	}
	
	private Path getJavaJar() {
		final Path javaJar = Paths.get(Const.JAR_DIR, Const.JAR_NAME);
		Path javaHome = getJavaHome();
		return javaHome.resolve(javaJar);
	}

	private List<Type> getJarTypes() {
		List<Type> types = new ArrayList<>();
		try {
			Bytecode bytecode = new Bytecode();
			try (ZipInputStream zip = new ZipInputStream(new FileInputStream(getJavaJar().toFile()))) {
				ZipEntry entry;
				while ((entry = zip.getNextEntry()) != null) {
					if (!entry.isDirectory() && entry.getName().endsWith(Const.CLASS_EXT)) {
						types.add(bytecode.read(zip));
					}
				}
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		return types;
	}
	
	@Override
	public final void setDefaultTypes() {
		MODEL.getTypes().addAll(getJarTypes());
		MODEL.setTypesSource(Const.JAR_NAME);
	}
	
}
