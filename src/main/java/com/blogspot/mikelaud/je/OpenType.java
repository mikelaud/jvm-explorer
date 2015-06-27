package com.blogspot.mikelaud.je;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.blogspot.mikelaud.je.common.Bytecode;
import com.blogspot.mikelaud.je.common.Type;

public class OpenType {

	private final String JAVA_HOME_PROPERTY;
	private final String JAR_NAME;
	private final String JAR_DIR;
	private final String CLASS_EXT;
	private final Bytecode BYTECODE;
	
	private Path getJavaHome() {
		String javaHome = System.getProperty(JAVA_HOME_PROPERTY);
		if (null == javaHome) { 
			javaHome = "";
		}
		return Paths.get(javaHome);
	}
	
	private Path getJavaJar() {
		final Path javaJar = Paths.get(JAR_DIR, JAR_NAME);
		Path javaHome = getJavaHome();
		return javaHome.resolve(javaJar);
	}

	private List<Type> getJarFiles() {
		List<Type> types = new ArrayList<>();
		try {
			try (ZipInputStream zip = new ZipInputStream(new FileInputStream(getJavaJar().toFile()))) {
				ZipEntry entry;
				while ((entry = zip.getNextEntry()) != null) {
					if (!entry.isDirectory() && entry.getName().endsWith(CLASS_EXT)) {
						types.add(BYTECODE.read(zip));
					}
				}
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		Collections.sort(types, (a, b) -> a.getName().compareTo(b.getName()));
		return types;
	}
	
	public String getJarName() {
		return JAR_NAME;
	}
	
	public List<Type> get() {
		return getJarFiles();
	}
	
	public OpenType() {
		JAVA_HOME_PROPERTY = "java.home";
		JAR_NAME = "rt.jar";
		JAR_DIR = "lib";
		CLASS_EXT = ".class";
		BYTECODE = new Bytecode();
	}
	
}
