package com.blogspot.mikelaud.je.agent.loader.source;

import java.net.URL;
import java.nio.file.Path;

public interface FileSourceJar extends FileSource {

	String getJarName();
	Path getJarPath();
	URL getJarUrl();

}
