package com.blogspot.mikelaud.je.agent.beans;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.blogspot.mikelaud.je.agent.Transformer;

public class Types implements TypesMXBean {

	private final Instrumentation INSTRUMENTATION;
	private final List<byte[]> BYTECODES;

	public Types(Instrumentation aInstrumentation) {
		INSTRUMENTATION = aInstrumentation;
		BYTECODES = new CopyOnWriteArrayList<>();
		INSTRUMENTATION.addTransformer(new Transformer(BYTECODES));
	}

	@Override
	public void echo() {
		System.out.println("[agent] echo.");
	}

	@Override
	public List<byte[]> getBytecodes() {
		for (Class<?> clazz : INSTRUMENTATION.getAllLoadedClasses()) {
			if (clazz.getName().contains("$Lambda$")) continue;
			if (clazz.getName().startsWith("java.lang.")) continue;
			if (clazz.getName().startsWith("[")) continue;
			if (clazz.getName().startsWith("com.blogspot.mikelaud")) {
				System.out.println("[agent] retransform self class: " + clazz.getName());
			}
			try {
				INSTRUMENTATION.retransformClasses(clazz);
			}
			catch (UnmodifiableClassException e) {
				System.err.println("[agent] skip class: " + clazz.getName() + "; " + e);
			}
		}
		return BYTECODES;
	}
	
}
