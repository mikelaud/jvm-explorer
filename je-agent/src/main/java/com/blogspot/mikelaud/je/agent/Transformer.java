package com.blogspot.mikelaud.je.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

public class Transformer implements ClassFileTransformer {

	private final List<byte[]> BYTECODES;
	
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		BYTECODES.add(classfileBuffer);
		return null;
	}

	public Transformer(List<byte[]> aBytecodes) {
		BYTECODES = aBytecodes;
	}
	
}
