package org.jow.hotfix.override;

import java.lang.reflect.Constructor;

import org.jow.hotfix.override.module.EModType;
import org.jow.hotfix.override.module.ModBase;
import org.jow.hotfix.override.module.test.ModTest;

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
			throw new RuntimeException("热更旧模块的实例失败");
		}
	}
	
	public ModTest getModTest() {
		return (ModTest) modules[EModType.ModTest.ordinal()];
	}

}
