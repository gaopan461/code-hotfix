package org.jow.hotfix.override.module;

import org.jow.hotfix.override.Human;

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
