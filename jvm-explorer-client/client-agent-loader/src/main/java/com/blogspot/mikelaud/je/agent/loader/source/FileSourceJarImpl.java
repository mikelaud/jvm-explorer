package com.blogspot.mikelaud.je.agent.loader.source;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.blogspot.mikelaud.je.agent.loader.source.content.FileContent;
import com.blogspot.mikelaud.je.agent.loader.source.content.FileContentFactory;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class FileSourceJarImpl implements FileSourceJar {

	private final FileContentFactory FILE_CONTENT_FACTORY;
	//
	private final Path JAR_PATH;
	private final URL JAR_URL;
	private final String JAR_NAME;
	//
	private final String FILE_PATH;
	private final URL FILE_URL;
	private final String FILE_NAME;

	private URL getJarUrl(Path aJarPath) {
		try {
			return aJarPath.toUri().toURL();
		}
		catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private URL getFileUrl(URL aJarUrl, String aFilePath) {
		try {
			return new URL(String.format("jar:%s!/%s", aJarUrl, aFilePath));
		}
		catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Inject
	private FileSourceJarImpl
	(	FileContentFactory aFileContentFactory
	,	@Assisted Path aJarPath
	,	@Assisted String aFilePath
	) {
		FILE_CONTENT_FACTORY = Objects.requireNonNull(aFileContentFactory);
		//
		JAR_PATH = Objects.requireNonNull(aJarPath);
		JAR_URL = Objects.requireNonNull(getJarUrl(JAR_PATH));
		JAR_NAME = Objects.requireNonNull(JAR_PATH.getFileName().toString());
		//
		FILE_PATH = Objects.requireNonNull(aFilePath);
		FILE_URL = Objects.requireNonNull(getFileUrl(JAR_URL, FILE_PATH));
		FILE_NAME = Objects.requireNonNull(Paths.get(FILE_PATH).getFileName().toString());
	}

	@Override public String getJarName() { return JAR_NAME; }
	@Override public Path getJarPath() { return JAR_PATH; }
	@Override public URL getJarUrl() { return JAR_URL; }
	//
	@Override public String getFileName() { return FILE_NAME; }
	@Override public String getFilePath() { return FILE_PATH; }
	@Override public URL getFileUrl() { return FILE_URL; }

	@Override
	public FileContent takeContent() {
		return FILE_CONTENT_FACTORY.newFileContentJar(FILE_URL);
	}

	@Override
	public String toString() {
		return FILE_URL.toString();
	}

}
