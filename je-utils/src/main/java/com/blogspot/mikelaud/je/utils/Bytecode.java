package com.blogspot.mikelaud.je.utils;

import java.io.InputStream;

import org.objectweb.asm.ClassReader;

import com.blogspot.mikelaud.je.domain.Type;

public class Bytecode {

	private final Bytes BYTES;
	private final TypeVisitor TYPE_VISITOR;
	
	public Type getType() {
		return TYPE_VISITOR.getType();
	}
	
	public Type read(byte[] aTypeBytes) {
		TYPE_VISITOR.reset();
		if (null != aTypeBytes) {
			ClassReader reader = new ClassReader(aTypeBytes);
			reader.accept(TYPE_VISITOR, 0);
		}
		return TYPE_VISITOR.getType();
	}
	
	public Type read(InputStream aStream) {
		BYTES.read(aStream);
		return read(BYTES.get());
	}
	
	public Bytecode() {
		BYTES = new Bytes();
		TYPE_VISITOR = new TypeVisitor();
	}
	
}
