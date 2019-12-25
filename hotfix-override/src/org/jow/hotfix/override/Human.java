package org.jow.hotfix.override;

import java.lang.reflect.Constructor;

import org.jow.hotfix.override.module.EModType;
import org.jow.hotfix.override.module.ModBase;
import org.jow.hotfix.override.module.test.ModTest;

public class Human {
	/** 模块构造函数 */
	private static Constructor<?>[] moduleConstructors = new Constructor<?>[EModType.values().length];
	
	/** 玩家身上挂载的全部模块 */
	private ModBase[] modules = new ModBase[EModType.values().length];
	
	static {
		updateModuleConstructor(EModType.ModTest, ModTest.class);
	}
	
	public static void updateModuleConstructor(EModType modType, Class<?> clazz) {
		try {
			moduleConstructors[modType.ordinal()] = clazz.getConstructor(Human.class);
		} catch (Exception e) {
			throw new RuntimeException("缺少必须的构造函数");
		}
	}
	
	public Human() {
		createModules();
	}
	
	private void createModules() {
		for (EModType modType : EModType.values()) {
			modules[modType.ordinal()] = createModule(modType);
		}
	}
	
	private ModBase createModule(EModType modType) {
		try {
			return (ModBase) moduleConstructors[modType.ordinal()].newInstance(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
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
