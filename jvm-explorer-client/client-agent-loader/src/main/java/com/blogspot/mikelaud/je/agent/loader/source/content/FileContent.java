package com.blogspot.mikelaud.je.agent.loader.source.content;

import java.io.InputStream;

public interface FileContent {

	long getSize();
	//
	long getLastAccessTime();
	long getLastModifiedTime();
	//
	InputStream takeStream();

}
