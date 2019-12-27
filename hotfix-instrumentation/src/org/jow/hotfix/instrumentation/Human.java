package org.jow.hotfix.instrumentation;

import org.jow.hotfix.instrumentation.module.EModType;
import org.jow.hotfix.instrumentation.module.ModBase;
import org.jow.hotfix.instrumentation.module.test.ModTest;

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
	
	public void doSomeThing() {
		ModTest modTest = (ModTest) modules[EModType.ModTest.ordinal()];
		modTest.doSomeThing();
	}

}
