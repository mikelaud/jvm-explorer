package com.blogspot.mikelaud.je.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Bytes {

	private static interface Const {
		int DEFAULT_BUFFER_SIZE = 1024;
	}
	
	private final int BUFFER_SIZE;
	private final byte[] BUFFER;
	private final ByteArrayOutputStream STREAM;
	//
	private byte[] mBytes;
	
	public byte[] get() {
		return mBytes;
	}
		
	public byte[] read(InputStream aStream) {
		STREAM.reset();
		if (null != aStream) {
			try {
				int count;
				while ((count = aStream.read(BUFFER)) >= 0) {
					STREAM.write(BUFFER, 0, count);
				}
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
		mBytes = STREAM.toByteArray();
		return mBytes;
	}

	public byte[] read(byte[] aBytes) {
		STREAM.reset();
		if (null != aBytes) {
			try {
				STREAM.write(aBytes);
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
		mBytes = STREAM.toByteArray();
		return mBytes;
	}
		
	public Bytes(int aBufferSize) {
		BUFFER_SIZE = aBufferSize;
		BUFFER = new byte[BUFFER_SIZE];
		STREAM = new ByteArrayOutputStream(BUFFER_SIZE);
		//
		STREAM.reset();
		mBytes = STREAM.toByteArray();
	}
	
	public Bytes() {
		this(Const.DEFAULT_BUFFER_SIZE);
	}
	
}
