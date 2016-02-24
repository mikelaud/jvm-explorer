package com.blogspot.mikelaud.je.ssh.operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.nio.file.Path;

import com.blogspot.mikelaud.je.ssh.common.VoidOutputStream;

public class CopyToVoidOperation extends CopyToLocalOperation {

	@Override
	protected OutputStream newFileOutputStream(File aFile) throws FileNotFoundException {
		return new VoidOutputStream();
	}

	public CopyToVoidOperation(Path aFileRemote, Path aFileLocal) {
		super(aFileRemote, aFileLocal);
	}

	@Override
	public String toString() {
		return String.format("check: %s@%s:%s", getUserName(), getHostName(), FILE_REMOTE.getFilePath());
	}

}
