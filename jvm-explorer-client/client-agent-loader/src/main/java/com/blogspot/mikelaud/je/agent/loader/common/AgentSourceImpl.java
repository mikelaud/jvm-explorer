package com.blogspot.mikelaud.je.agent.loader.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.Objects;

import com.blogspot.mikelaud.je.common.file_source.FileSource;
import com.blogspot.mikelaud.je.common.file_source.FileSourceFactory;
import com.google.inject.Inject;

public class AgentSourceImpl implements AgentSource {

	private final FileSourceFactory FILE_SOURCE_FACTORY;
	private final String AGENT_JAR_PATTERN;
	private final Path PROGRAM_JAR_PATH;
	private final boolean HAS_PROGRAM_JAR;

	@Inject
	private AgentSourceImpl(FileSourceFactory aFileSourceFactory) {
		FILE_SOURCE_FACTORY = Objects.requireNonNull(aFileSourceFactory);
		AGENT_JAR_PATTERN = "META-INF/resources/server-agent-%s-1.0.0-jar-with-dependencies.jar";
		try {
			CodeSource codeSource = getClass().getProtectionDomain().getCodeSource();
			PROGRAM_JAR_PATH = Objects.requireNonNull(Paths.get(codeSource.getLocation().toURI()));
			HAS_PROGRAM_JAR = PROGRAM_JAR_PATH.toFile().exists() && PROGRAM_JAR_PATH.toFile().isFile();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public FileSource get(AgentSourceType aSourceType) {
		String name = aSourceType.toString().toLowerCase();
		String agentJarLocation = String.format(AGENT_JAR_PATTERN, name);
		if (HAS_PROGRAM_JAR) {
			return FILE_SOURCE_FACTORY.newFileSourceJar(PROGRAM_JAR_PATH, agentJarLocation);
		}
		else {
			return FILE_SOURCE_FACTORY.newFileSource(PROGRAM_JAR_PATH.resolve(agentJarLocation));
		}
	}

	@Override public FileSource getBios() { return get(AgentSourceType.BIOS); }
	@Override public FileSource getHead() { return get(AgentSourceType.HEAD); }
	@Override public FileSource getBody() { return get(AgentSourceType.BODY); }

	@Override
	public String toString() {
		return PROGRAM_JAR_PATH.toString();
	}

}
