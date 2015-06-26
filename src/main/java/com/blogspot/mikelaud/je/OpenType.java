package com.blogspot.mikelaud.je;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class OpenType {

	private Path getJavaHome() {
		final String javaHomeProperty = "java.home";
		String javaHome = System.getProperty(javaHomeProperty);
		if (null == javaHome) { 
			javaHome = "";
		}
		return Paths.get(javaHome);
	}
	
	private Path getJavaJar() {
		final Path javaJar = Paths.get("lib", "rt.jar");
		Path javaHome = getJavaHome();
		return javaHome.resolve(javaJar);
	}

	private List<String> getJarFiles() {
		List<String> classNames = new ArrayList<String>();
		try {
			try (ZipInputStream zip = new ZipInputStream(new FileInputStream(getJavaJar().toFile()))) {
				for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
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
	
	public String get() {
		return getJarFiles().stream().collect(Collectors.joining("\n"));
	}
	
	public OpenType() {
		//
	}
	
}
