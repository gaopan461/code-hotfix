package org.jow.hotfix.instrumentation.module;

import java.util.function.Function;

import org.jow.hotfix.instrumentation.Human;

/**
 * 全部模块枚举定义
 * @author gaopan
 */
public enum EModType {
	ModTest(org.jow.hotfix.instrumentation.module.test.ModTest::new),
	;
	
	private Function<Human, ModBase> constructor;
	
	private EModType(Function<Human, ModBase> constructor) {
		this.constructor = constructor;
	}
	
	/**
	 * 创建指令类型的模块实例
	 * @param human
	 * @return
	 */
	public ModBase createModule(Human human) {
		return constructor.apply(human);
	}

}