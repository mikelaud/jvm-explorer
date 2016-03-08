package com.blogspot.mikelaud.je.common.file_source.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class FileContentImpl implements FileContent {

	private final Path FILE_PATH;
	private final File FILE;

	@Inject
	private FileContentImpl(@Assisted Path aFilePath) {
		FILE_PATH = Objects.requireNonNull(aFilePath);
		FILE = Objects.requireNonNull(aFilePath.toFile());
		//
		if (! FILE.exists()) {
			throw new RuntimeException("File not exists: " + FILE_PATH);
		}
	}

	@Override public long getSize() { return FILE.length(); }
	@Override public long getLastAccessTime() { return getLastModifiedTime(); }
	@Override public long getLastModifiedTime() { return FILE.lastModified(); }

	@Override
	public InputStream takeStream() {
		try {
			return new FileInputStream(FILE);
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return FILE_PATH.toString();
	}

}
