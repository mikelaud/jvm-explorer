package com.blogspot.mikelaud.je.common.file_source;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

import com.blogspot.mikelaud.je.common.file_source.content.FileContent;
import com.blogspot.mikelaud.je.common.file_source.content.FileContentFactory;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class FileSourceImpl implements FileSource {

	private final FileContentFactory FILE_CONTENT_FACTORY;
	//
	private final Path FILE_PATH;
	private final URL FILE_URL;
	private final String FILE_NAME;

	private URL getUrl(Path aPath) {
		try {
			return aPath.toUri().toURL();
		}
		catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Inject
	private FileSourceImpl
	(	FileContentFactory aFileContentFactory
	,	@Assisted Path aFilePath
	) {
		FILE_CONTENT_FACTORY = Objects.requireNonNull(aFileContentFactory);
		//
		FILE_PATH = Objects.requireNonNull(aFilePath);
		FILE_URL = Objects.requireNonNull(getUrl(FILE_PATH));
		FILE_NAME = Objects.requireNonNull(FILE_PATH.getFileName().toString());
	}

	@Override public String getFileName() { return FILE_NAME; }
	@Override public String getFilePath() { return FILE_PATH.toString(); }
	@Override public URL getFileUrl() { return FILE_URL; }

	@Override
	public FileContent takeContent() {
		return FILE_CONTENT_FACTORY.newFileContent(FILE_PATH);
	}

	@Override
	public String toString() {
		return FILE_URL.toString();
	}

}
