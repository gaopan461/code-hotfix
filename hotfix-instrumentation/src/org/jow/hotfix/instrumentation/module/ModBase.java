package org.jow.hotfix.instrumentation.module;

import org.jow.hotfix.instrumentation.Human;

/**
 * 玩家模块的基类
 * @author gaopan
 */
public class ModBase {
	/** 所属玩家对象 */
	protected Human human;
	
	public ModBase(Human human) {
		this.human = human;
	}

}
