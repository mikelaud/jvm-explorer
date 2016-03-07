package com.blogspot.mikelaud.je.agent.loader.source.content;

import java.net.URL;
import java.nio.file.Path;

public interface FileContentFactory {

	FileContent newFileContent(Path aFilePath);
	FileContentJar newFileContentJar(URL aFileUrl);

}
