package com.blogspot.mikelaud.je.core;

import java.io.FileInputStream;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.blogspot.mikelaud.je.agent.beans.TypesMXBean;
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

	@SuppressWarnings("unused")
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

	private List<DomainType> getTypes(List<byte[]> aBytecodes) {
		List<DomainType> types = new ArrayList<>();
		try {
			Bytecode bytecode = new Bytecode();
			for (byte[] code : aBytecodes) {
				types.add(bytecode.read(code));
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		return types;
	}

	private String getSelfJvmPid() {
		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
	    return jvmName.substring(0, jvmName.indexOf('@'));
	}

	private void loadLocalAgent() {
		try {
			VirtualMachine jvm = VirtualMachine.attach(getSelfJvmPid());
			Path userHome = Paths.get(System.getProperty("user.home"));
			Path agentPath = userHome.resolve(".m2/repository/com/blogspot/mikelaud/je/je-agent/1.0.0/je-agent-1.0.0-jar-with-dependencies.jar");
			System.out.println("Load local agent: " + agentPath);
			jvm.loadAgent(agentPath.toString());
			jvm.detach();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private void startRemoteManagementAgent() {
		try {
			VirtualMachine jvm = VirtualMachine.attach(getSelfJvmPid());
			Properties props = new Properties();
			props.put("com.sun.management.jmxremote.port", "5000");
			props.put("com.sun.management.jmxremote.authenticate", "false");
			props.put("com.sun.management.jmxremote.ssl", "false");
			System.out.println("Start startManagementAgent agent: " + props);
			jvm.startManagementAgent(props);
			jvm.detach();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private List<DomainType> callLocalAgentEcho() {
		try {
			TypesMXBean bean = DOMAIN.getTypesBean();
			bean.echo();
			byte[][] bytecodes = bean.getBytecodes();
			return getTypes(Arrays.asList(bytecodes));
		}
		catch (Throwable t) {
			t.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public final Domain getDomain() {
		return DOMAIN;
	}

	@Override
	public final void setDefaultTypes() {
		loadLocalAgent();
		loadLocalAgent();
		//startRemoteManagementAgent();
		//DOMAIN.getTypes().addAll(callLocalAgentEcho());
		DOMAIN.setTypesSource(Const.JAR_NAME);
	}

}
