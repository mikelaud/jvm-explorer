package com.blogspot.mikelaud.je.common.file_source;

import java.net.URL;

import com.blogspot.mikelaud.je.common.file_source.content.FileContent;

public interface FileSource {

	String getFileName();
	String getFilePath();
	URL getFileUrl();
	//
	boolean exists();
	FileContent takeContent();

}
