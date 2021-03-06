package com.blogspot.mikelaud.je.agent.body;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class Transformer implements ClassFileTransformer {

	static {
		System.out.println(">>>>>>>>> Init Transformer.");
	}
	
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if (className.startsWith("com/blogspot/mikelaud")) {
			System.out.println("[agent] transform class: " + className);
		}
		return null;
	}
	
}
