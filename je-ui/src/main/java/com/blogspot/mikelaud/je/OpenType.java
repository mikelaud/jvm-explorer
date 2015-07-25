package com.blogspot.mikelaud.je;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.blogspot.mikelaud.je.common.Bytecode;
import com.blogspot.mikelaud.je.common.Type;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class OpenType {

	private final String JAVA_HOME_PROPERTY;
	private final String JAR_NAME;
	private final String JAR_DIR;
	private final String CLASS_EXT;
	private final Bytecode BYTECODE;
	//
	private final ObservableList<Type> DATA;
	private final FilteredList<Type> FILTERED_DATA;
	private final SortedList<Type> SORTED_DATA;
	
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
		return types;
	}
	
	public String getDefaultPackage() {
		return JAR_NAME;
	}
	
	public ObservableList<Type> getData() { return DATA; }
	public FilteredList<Type> getFilteredData() { return FILTERED_DATA; }
	public SortedList<Type> getSortedData() { return SORTED_DATA; }
	
	public OpenType() {
		JAVA_HOME_PROPERTY = "java.home";
		JAR_NAME = "rt.jar";
		JAR_DIR = "lib";
		CLASS_EXT = ".class";
		BYTECODE = new Bytecode();
		//
		DATA = FXCollections.observableArrayList(getJarFiles());
		FILTERED_DATA = new FilteredList<>(DATA, p -> true);
		SORTED_DATA = new SortedList<>(FILTERED_DATA, (a, b) -> a.getName().compareTo(b.getName()));
	}
	
}
