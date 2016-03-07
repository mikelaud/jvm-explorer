package com.blogspot.mikelaud.je.agent.loader.source;

import java.net.URL;

import com.blogspot.mikelaud.je.agent.loader.source.content.FileContent;

public interface FileSource {

	String getFileName();
	String getFilePath();
	URL getFileUrl();
	//
	FileContent takeContent();

}
