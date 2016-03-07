package com.blogspot.mikelaud.je.agent.loader.source.content;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.Manifest;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class FileContentJarImpl implements FileContentJar {

	private final URL FILE_URL;
	private final JarURLConnection JAR_URL_CONNECTION;
	private final JarEntry JAR_ENTRY;

	@Inject
	private FileContentJarImpl(@Assisted URL aFileUrl) {
		FILE_URL = Objects.requireNonNull(aFileUrl);
		try {
			JAR_URL_CONNECTION = Objects.requireNonNull((JarURLConnection) FILE_URL.openConnection());
			JAR_ENTRY = Objects.requireNonNull(JAR_URL_CONNECTION.getJarEntry());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Manifest getManifest() {
		try {
			return JAR_URL_CONNECTION.getManifest();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override public long getSize() { return JAR_ENTRY.getSize(); }
	@Override public long getLastAccessTime() { return getLastModifiedTime(); }
	@Override public long getLastModifiedTime() { return JAR_ENTRY.getTime(); }

	@Override
	public InputStream takeStream() {
		try {
			return JAR_URL_CONNECTION.getJarFile().getInputStream(JAR_ENTRY);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return FILE_URL.toString();
	}

}
