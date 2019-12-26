package org.jow.hotfix.classloader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.jow.hotfix.classloader.module.EModType;
import org.jow.hotfix.classloader.module.ModBase;

/**
 * 玩家对象
 * @author gaopan
 */
public class Human {
	/** 玩家身上挂载的全部模块 */
	private ModBase[] modules = new ModBase[EModType.values().length];
	
	public Human() {
		createModules();
	}
	
	private void createModules() {
		for (EModType modType : EModType.values()) {
			modules[modType.ordinal()] = modType.createModule(this);
		}
	}
	
	public void hotfixModule(EModType modType, Constructor<?> copyConstructor) {
		ModBase oldMod = modules[modType.ordinal()];
		try {
			modules[modType.ordinal()] = (ModBase) copyConstructor.newInstance(oldMod);
		} catch (Exception e) {
			throw new RuntimeException("热更旧模块的实例失败", e);
		}
	}
	
	public void doSomeThing() {
		ModBase modTest = modules[EModType.ModTest.ordinal()];
		try {
			Method method = modTest.getClass().getMethod("doSomeThing");
			method.invoke(modTest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
