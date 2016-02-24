package com.blogspot.mikelaud.je.ssh.common;

import java.io.IOException;
import java.io.OutputStream;

public class VoidOutputStream extends OutputStream {

	@Override
	public void write(int aByte) throws IOException {
		// void
	}

}
