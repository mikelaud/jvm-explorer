package com.blogspot.mikelaud.je.agent.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import com.blogspot.mikelaud.je.agent.body.Transformer;

public class Types implements TypesMXBean {

	static {
		System.out.println(">>>>>>>>> Init Types.");
	}

	private final Instrumentation INSTRUMENTATION;
	private final List<byte[]> BYTECODES;
	private final String ID;

	public Types(Instrumentation aInstrumentation, String aId) {
		INSTRUMENTATION = aInstrumentation;
		BYTECODES = new ArrayList<>(); //TODO: concurrent
		//TODO: map of WeekReference<ClassLoader>
		INSTRUMENTATION.addTransformer(new Transformer());
		ID = aId;
	}

	@Override
	public void echo() {
		System.out.println("[agent] echo from: " + ID);
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
	public byte[][] getBytecodes() {
		for (Class<?> clazz : INSTRUMENTATION.getAllLoadedClasses()) {
			if (clazz.getName().contains("$Lambda$")) continue;
			if (clazz.getName().startsWith("java.lang.")) continue;
			if (clazz.getName().startsWith("[")) continue;
			final String name = clazz.getName().replaceAll("\\.", "/") + ".class";
			ClassLoader classLoader = clazz.getClassLoader();
			if (null == classLoader) {
				if (name.contains("mikelaud")) {
					System.out.println("[agent] skip (no class loader): " + name);
				}
				continue;
			}
			InputStream stream = classLoader.getResourceAsStream(name);
			if (null == stream) {
				if (name.contains("mikelaud")) {
					System.out.println("[agent] skip (no class stream): " + name);
				}
				continue;
			}
			if (name.contains("mikelaud")) {
				System.out.println("[agent] get (class bytecode): " + name);
			}
			byte[] bytecode = readBytes(stream);
			BYTECODES.add(bytecode);
		}
		return BYTECODES.stream().toArray(byte[][]::new);
	}

	class EnteringAdapter extends AdviceAdapter {

		private String mClassDesc;
		private String name;

		protected EnteringAdapter(String aClassDesc, MethodVisitor mv, int acc, String name, String desc) {
			super(Opcodes.ASM5, mv, acc, name, desc);
			mClassDesc = aClassDesc;
			this.name = name;
		}

		protected void onMethodEnter() {
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			mv.visitLdcInsn("[agent] LOG!!! " + mClassDesc + "." + name);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
		}
	}

	public class ReturnAdapter extends ClassVisitor {

		private String mClassDesc;
		private String mMethodDesc;

		  public ReturnAdapter(ClassVisitor cv, String aClassDesc, String aMethodDesc) {
		    super(Opcodes.ASM5, cv);
		    mClassDesc = aClassDesc;
		    mMethodDesc = aMethodDesc;
		  }

		  @Override
		  public MethodVisitor visitMethod(
		      int access,
		      String name,
		      String desc,
		      String signature,
		      String[] exceptions) {
		    MethodVisitor mv;
		    mv = cv.visitMethod(access, name, desc, signature, exceptions);
		    if (name.endsWith(mMethodDesc)) {
		    	mv = new EnteringAdapter(mClassDesc, mv, access, name, desc);
		    }
		    return mv;
		  }
	}

	@Override
	public void addLogging(int aClassLoaderId, String aClassDesc, String aMethodDesc) {
		// TODO Auto-generated method stub
		System.out.println("[agent] add logging: " + aClassDesc + "." + aMethodDesc);
		for (Class<?> clazz : INSTRUMENTATION.getAllLoadedClasses()) {
			if (clazz.getName().endsWith(aClassDesc)) {
				try {
					System.out.println("[agent] redefineClass: " + clazz.getName());
					//INSTRUMENTATION.retransformClasses(clazz);
					final String name = clazz.getName().replaceAll("\\.", "/") + ".class";
					ClassLoader classLoader = clazz.getClassLoader();
					byte[] bytecode = readBytes(classLoader.getResourceAsStream(name));
					//
					ClassReader cr = new ClassReader(bytecode);
					ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
					ReturnAdapter cv = new ReturnAdapter(cw, aClassDesc, aMethodDesc);
					cr.accept(cv, ClassReader.EXPAND_FRAMES);
					//
					ClassDefinition cd = new ClassDefinition(clazz, cw.toByteArray());
					INSTRUMENTATION.redefineClasses(cd);
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}

	@Override
	public void removeLogging(int aClassLoaderId, String aClassDesc, String aMethodDesc) {
		// TODO Auto-generated method stub
		System.out.println("[agent] remove logging.");
	}

}
