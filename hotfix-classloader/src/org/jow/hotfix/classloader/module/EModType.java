package org.jow.hotfix.classloader.module;

import java.lang.reflect.Constructor;

import org.jow.hotfix.classloader.Human;

/**
 * 全部模块枚举定义
 * @author gaopan
 */
public enum EModType {
	ModTest(org.jow.hotfix.classloader.module.test.ModTest.class),
	;
	
	/** 所有模块的构造函数 */
	private static Constructor<?>[] moduleConstructors = new Constructor<?>[EModType.values().length];
	
	/** 模块完整类名 */
	private Class<?> modClass;
	
	static {
		ClassLoader classLoader = new ModClassLoader();
		for (EModType modType : EModType.values()) {
			modType.updateModuleConstructor(classLoader);
		}
	}
	
	private EModType(Class<?> modClass) {
		this.modClass = modClass;
	}
	
	/**
	 * 热更模块，把旧模块的构造函数替换成新的
	 * @param classLoader
	 */
	public void updateModuleConstructor(ClassLoader classLoader) {
		Class<?> clazz;
		try {
			clazz = classLoader.loadClass(modClass.getName() + "Impl");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("类不存在", e);
		}
		
		try {
			moduleConstructors[ordinal()] = clazz.getConstructor(Human.class);
		} catch (Exception e) {
			throw new RuntimeException("缺少必须的构造函数", e);
		}
	}
	
	/**
	 * 创建指令类型的模块实例
	 * @param human
	 * @return
	 */
	public ModBase createModule(Human human) {
		try {
			return (ModBase) moduleConstructors[ordinal()].newInstance(human);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 检测新模块类是否满足热更条件
	 * @param classLoader 新的类加载器
	 * @return 满足的话，返回新模块类的拷贝构造函数
	 */
	public Constructor<?> checkHotfix(ClassLoader classLoader) {
		Class<?> clazz;
		try {
			clazz = classLoader.loadClass(modClass.getName() + "Impl");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("类不存在", e);
		}
		
		try {
			return clazz.getConstructor(modClass);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("输入类缺少拷贝构造函数", clazz.getSimpleName()), e);
		}
	}

}