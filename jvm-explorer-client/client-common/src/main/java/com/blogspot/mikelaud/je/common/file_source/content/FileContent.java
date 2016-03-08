package com.blogspot.mikelaud.je.common.file_source.content;

import java.io.InputStream;

public interface FileContent {

	long getSize();
	//
	long getLastAccessTime();
	long getLastModifiedTime();
	//
	InputStream takeStream();

}
