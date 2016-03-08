package com.blogspot.mikelaud.je.common.file_source;

import java.net.URL;
import java.nio.file.Path;

public interface FileSourceJar extends FileSource {

	String getJarName();
	Path getJarPath();
	URL getJarUrl();

}
