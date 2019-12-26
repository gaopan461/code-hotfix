package org.jow.hotfix.classloader.module;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 模块类加载器
 * @author gaopan
 */
public class ModClassLoader extends ClassLoader {
	/** 模块class文件所在目录 */
	private String modClassPath = System.getProperty("user.dir") + "/../../hotfix-classloader-module/bin/";

	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		String fileName = modClassPath + className.replace('.', '/') + ".class";
		File file = new File(fileName);
		if (!file.exists()) {
			throw new ClassNotFoundException();
		}
		
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			byte[] b = new byte[bis.available()];
			bis.read(b, 0, b.length);
			return defineClass(className, b, 0, b.length);
		} catch (IOException e) {
			throw new ClassNotFoundException("", e);
		}
	}
	
}
