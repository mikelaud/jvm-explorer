package com.blogspot.mikelaud.je.ssh.operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.blogspot.mikelaud.je.common.file_source.FileSource;
import com.blogspot.mikelaud.je.ssh.common.VoidOutputStream;

public class CopyToVoidOperation extends CopyToLocalOperation {

	@Override
	protected OutputStream newFileOutputStream(File aFile) throws FileNotFoundException {
		return new VoidOutputStream();
	}

	public CopyToVoidOperation(Path aFileRemote, FileSource aFileLocal) {
		super(aFileRemote, Paths.get("/dev/null"));
	}

	@Override
	public String toString() {
		return String.format("check: %s@%s:%s", getUserName(), getHostName(), FILE_REMOTE.getFilePath());
	}

}
