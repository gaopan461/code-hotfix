package org.jow.hotfix.classloader.module;

import org.jow.hotfix.classloader.Human;

/**
 * 玩家模块的基类
 * @author gaopan
 */
abstract public class ModBaseImpl implements ModBase {
	/** 所属玩家对象 */
	protected Human human;
	
	public ModBaseImpl(Human human) {
		this.human = human;
	}

	@Override
	public Human getHuman() {
		return human;
	}

}
