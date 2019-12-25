package org.jow.hotfix.override.module;

import java.lang.reflect.Constructor;

import org.jow.hotfix.override.module.test.ModTest;

public enum EModType {
	ModTest(ModTest.class),
	;
	
	private final Class<?> originalClass;
	
	private EModType(Class<?> originalClass) {
		this.originalClass = originalClass;
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