package org.jow.hotfix.instrumentation.agent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

/**
 * 重新加载类的Agent
 * @author gaopan
 */
public class ClassReloaderAgent {
	/** agent使用的工具对象 */
	private static Instrumentation inst;

	public static void premain(String agentArgs, Instrumentation i) {
		inst = i;
	}
	
	public static void reload(Class<?> clazz, File file) {
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			byte[] buffer = new byte[bis.available()];
			bis.read(buffer, 0, buffer.length);
			inst.redefineClasses(new ClassDefinition(clazz, buffer));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
