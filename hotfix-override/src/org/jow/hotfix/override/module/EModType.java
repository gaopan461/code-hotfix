package org.jow.hotfix.override.module;

import java.lang.reflect.Constructor;

import org.jow.hotfix.override.Human;
import org.jow.hotfix.override.module.test.ModTest;

/**
 * 全部模块枚举定义
 * @author gaopan
 */
public enum EModType {
	ModTest(ModTest.class),
	;
	
	/** 所有模块的构造函数 */
	private static Constructor<?>[] moduleConstructors = new Constructor<?>[EModType.values().length];
	
	/** 模块的原始类 */
	private final Class<?> originalClass;
	
	static {
		for (EModType modType : EModType.values()) {
			modType.updateModuleConstructor(modType.originalClass);
		}
	}
	
	private EModType(Class<?> originalClass) {
		this.originalClass = originalClass;
	}
	
	/**
	 * 热更模块，把旧模块的构造函数替换成新的
	 * @param clazz
	 */
	public void updateModuleConstructor(Class<?> clazz) {
		try {
			moduleConstructors[ordinal()] = clazz.getConstructor(Human.class);
		} catch (Exception e) {
			throw new RuntimeException("缺少必须的构造函数");
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
	 * @param clazz 新模块类
	 * @return 满足的话，返回新模块类的拷贝构造函数
	 */
	public Constructor<?> checkHotfix(Class<?> clazz) {
		if (!originalClass.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException(String.format("输入类不是%s的子类", clazz.getSimpleName(), originalClass.getSimpleName()));
		}
		
		try {
			return clazz.getConstructor(originalClass);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("输入类缺少拷贝构造函数", clazz.getSimpleName()));
		}
	}

}