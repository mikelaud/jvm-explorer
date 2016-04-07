package com.blogspot.mikelaud.je.core;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.mikelaud.je.agent.api.TypesMXBean;
import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.agent.loader.AgentLoaderFactory;
import com.blogspot.mikelaud.je.agent.loader.common.LocalAgentLoader;
import com.blogspot.mikelaud.je.agent.loader.common.RemoteAgentLoader;
import com.blogspot.mikelaud.je.core.helper.Bytecode;
import com.blogspot.mikelaud.je.domain.Domain;
import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.ssh.domain.Status;
import com.google.inject.Inject;

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

	private final Logger LOGGER = LoggerFactory.getLogger(CoreImpl.class);
	//
	private final Domain DOMAIN;
	private final AgentLoaderFactory AGENT_LOADER_FACTORY;
	//
	private RemoteAgentLoader mAgentLoader;

	@Inject
	private CoreImpl(Domain aDomain, AgentLoaderFactory aAgentLoaderFactory) {
		DOMAIN = aDomain;
		AGENT_LOADER_FACTORY = aAgentLoaderFactory;
		//
		mAgentLoader = AGENT_LOADER_FACTORY.newRemoteLoader("127.0.0.1");
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

	@SuppressWarnings("unused")
	private void loadLocalAgent(String aId) {
		LocalAgentLoader localAgentLoader = AGENT_LOADER_FACTORY.newLocalLoader();
		localAgentLoader.loadAgent();
	}

	/*
	@SuppressWarnings("unused")
	private void startRemoteManagementAgent() {
		try {
			LocalAgentLoader localAgentLoader = AGENT_LOADER_FACTORY.newLocalLoader();
			VirtualMachine jvm = VirtualMachine.attach(localAgentLoader.getJvmId());
			Properties props = new Properties();
			props.put("com.sun.management.jmxremote.port", "5000");
			props.put("com.sun.management.jmxremote.authenticate", "false");
			props.put("com.sun.management.jmxremote.ssl", "false");
			LOGGER.info("Start startManagementAgent agent: {}", props);
			jvm.startManagementAgent(props);
			jvm.detach();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}
	*/

	@SuppressWarnings("unused")
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
	public Domain getDomain() {
		return DOMAIN;
	}

	@Override
	public void setDefaultTypes() {
		//loadLocalAgent("");
		//loadLocalAgent("");
		//startRemoteManagementAgent();
		//DOMAIN.getTypes().addAll(callLocalAgentEcho());
		DOMAIN.setTypesSource(Const.JAR_NAME);
	}

	@Override
	public String loadAgent(String aHost, String aName) {
		try (RemoteAgentLoader remoteLoader = AGENT_LOADER_FACTORY.newRemoteLoader(aHost)) {
			remoteLoader.open("root", "1q2w3e");
			Status status = remoteLoader.loadAgentByName(aName);
			return (status.getCode() == 0 ? status.getMessage() : "Fail.");
		}
	}

	@Override
	public void doJvmConnect(String aHost) {
		Objects.requireNonNull(aHost);
		mAgentLoader = AGENT_LOADER_FACTORY.newRemoteLoader(aHost);
		mAgentLoader.open("root", "1q2w3e");
	}

	@Override
	public void doJvmDisconnect() {
		mAgentLoader.close();
	}

	@Override
	public Stream<JvmIdentity> doJvmList() {
		return mAgentLoader.getJvmList();
	}

}
