package com.blogspot.mikelaud.je.agent.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

import com.blogspot.mikelaud.je.agent.Transformer;

public class Types implements TypesMXBean {

	private final Instrumentation INSTRUMENTATION;
	private final List<byte[]> BYTECODES;

	public Types(Instrumentation aInstrumentation) {
		INSTRUMENTATION = aInstrumentation;
		BYTECODES = new ArrayList<>(); //TODO: concurrent
		INSTRUMENTATION.addTransformer(new Transformer());
	}

	@Override
	public void echo() {
		System.out.println("[agent] echo.");
	}

	private byte[] readBytes(InputStream aInputStream) {
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = aInputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			return buffer.toByteArray();
		}
		catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}
	
	@Override
	public List<byte[]> getBytecodes() {
		for (Class<?> clazz : INSTRUMENTATION.getAllLoadedClasses()) {
			if (clazz.getName().contains("$Lambda$")) continue;
			if (clazz.getName().startsWith("java.lang.")) continue;
			if (clazz.getName().startsWith("[")) continue;
			final String name = clazz.getName().replaceAll("\\.", "/") + ".class";
			ClassLoader classLoader = clazz.getClassLoader();
			if (null == classLoader) {
				System.out.println("[agent] skip (no class loader): " + name);
				continue;
			}
			InputStream stream = classLoader.getResourceAsStream(name);
			if (null == stream) {
				System.out.println("[agent] skip (no class stream): " + name);
				continue;
			}
			byte[] bytecode = readBytes(stream);
			BYTECODES.add(bytecode);
		}
		return BYTECODES;
	}

	@Override
	public void addLogging(int aClassLoaderId, String aClassDesc, String aMethodDesc) {
		// TODO Auto-generated method stub
		System.out.println("[agent] add logging.");
	}

	@Override
	public void removeLogging(int aClassLoaderId, String aClassDesc, String aMethodDesc) {
		// TODO Auto-generated method stub
		System.out.println("[agent] remove logging.");
	}
	
}
