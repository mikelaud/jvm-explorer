package com.blogspot.mikelaud.je;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class OpenType {

	private final String JAVA_HOME_PROPERTY;
	private final String JAR_NAME;
	private final String JAR_DIR;
	private final String CLASS_EXT;
	
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

	private List<String> getJarFiles() {
		List<String> classNames = new ArrayList<String>();
		try {
			try (ZipInputStream zip = new ZipInputStream(new FileInputStream(getJavaJar().toFile()))) {
				for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				    if (!entry.isDirectory() && entry.getName().endsWith(CLASS_EXT)) {
				        String className = entry.getName().replace('/', '.');
				        classNames.add(className);
				    }
				}
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		Collections.sort(classNames);
		return classNames;
	}
	
	public String getJarName() {
		return JAR_NAME;
	}
	
	public List<String> get() {
		return getJarFiles();
	}
	
	public OpenType() {
		JAVA_HOME_PROPERTY = "java.home";
		JAR_NAME = "rt.jar";
		JAR_DIR = "lib";
		CLASS_EXT = ".class"; 
	}
	
}
