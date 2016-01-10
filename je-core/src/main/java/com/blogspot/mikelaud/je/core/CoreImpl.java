package com.blogspot.mikelaud.je.core;

import java.io.FileInputStream;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.blogspot.mikelaud.je.core.helper.Bytecode;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.google.inject.Inject;
import com.sun.tools.attach.VirtualMachine;

public class CoreImpl implements Core {

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
	
	private final Domain DOMAIN;
	
	@Inject
	private CoreImpl(Domain aDomain) {
		DOMAIN = aDomain;
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

	private List<DomainType> getJarTypes() {
		List<DomainType> types = new ArrayList<>();
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

	private List<DomainType> getSelfTypes() {
		List<DomainType> types = new ArrayList<>();
		try {
			String jvmName = ManagementFactory.getRuntimeMXBean().getName();                                                   
		    String pid = jvmName.substring(0, jvmName.indexOf('@'));                                                   
		    VirtualMachine jvm = VirtualMachine.attach(pid);
		    Path userHome = Paths.get(System.getProperty("user.home"));
		    Path agentPath = userHome.resolve(".m2/repository/com/blogspot/mikelaud/je/je-agent/1.0.0/je-agent-1.0.0-jar-with-dependencies.jar");
		    System.out.println("Load agent: " + agentPath);
		    jvm.loadAgent(agentPath.toString());
		    jvm.detach();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		return types;
	}
	
	@Override
	public final Domain getDomain() {
		return DOMAIN;
	}

	@Override
	public final void setDefaultTypes() {
		DOMAIN.getTypes().addAll(getJarTypes());
		DOMAIN.setTypesSource(Const.JAR_NAME);
		getSelfTypes();
	}
	
}
