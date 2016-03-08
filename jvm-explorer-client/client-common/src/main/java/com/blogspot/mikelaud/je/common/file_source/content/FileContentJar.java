package com.blogspot.mikelaud.je.common.file_source.content;

import java.util.jar.Manifest;

public interface FileContentJar extends FileContent {

	Manifest getManifest();

}
