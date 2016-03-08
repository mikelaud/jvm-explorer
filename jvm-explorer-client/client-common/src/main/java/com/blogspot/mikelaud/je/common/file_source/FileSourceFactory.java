package com.blogspot.mikelaud.je.common.file_source;

import java.nio.file.Path;

public interface FileSourceFactory {

	FileSource newFileSource(Path aFilePath);
	FileSourceJar newFileSourceJar(Path aJarPath, String aFilePath);

}
