package com.blogspot.mikelaud.je.ssh.operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class CopyToLocalOperation extends CopyToVoidOperation {

	@Override
	protected OutputStream newFileOutputStream(File aFile) throws FileNotFoundException {
		return new FileOutputStream(aFile);
	}

	public CopyToLocalOperation(Path aFileRemote, Path aFileLocal) {
		super(aFileRemote, aFileLocal);
	}

	@Override
	public String toString() {
		return String.format("scp %s@%s:%s %s", getUserName(), getHostName(), FILE_REMOTE.getFilePath(), FILE_LOCAL);
	}

}
