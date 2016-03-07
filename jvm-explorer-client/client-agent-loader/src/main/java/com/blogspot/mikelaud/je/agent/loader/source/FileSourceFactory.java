package com.blogspot.mikelaud.je.agent.loader.source;

import java.nio.file.Path;

public interface FileSourceFactory {

	FileSource newFileSource(Path aFilePath);
	FileSourceJar newFileSourceJar(Path aJarPath, String aFilePath);

}
