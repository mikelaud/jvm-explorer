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
	
	private final ObservableList<Type> DATA;
	private final FilteredList<Type> FILTERED_DATA;
	private final SortedList<Type> SORTED_DATA;
	
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
	
	public String getDefaultPackage() {
		return Const.JAR_NAME;
	}
	
	public ObservableList<Type> getData() { return DATA; }
	public FilteredList<Type> getFilteredData() { return FILTERED_DATA; }
	public SortedList<Type> getSortedData() { return SORTED_DATA; }
	
	public OpenType() {
		DATA = FXCollections.observableArrayList(getJarTypes());
		FILTERED_DATA = new FilteredList<>(DATA, p -> true);
		SORTED_DATA = new SortedList<>(FILTERED_DATA, (a, b) -> a.getName().compareTo(b.getName()));
	}
	
}
