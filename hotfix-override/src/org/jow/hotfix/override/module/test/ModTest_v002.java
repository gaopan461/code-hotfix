package org.jow.hotfix.override.module.test;

import org.jow.hotfix.override.Human;

/**
 * 用来热更{@link ModTest}的第二个版本
 * @author gaopan
 */
public class ModTest_v002 extends ModTest_v001 {
	/** 热更类可以新增成员变量 */
	protected int i_v002 = 1;
	
	public ModTest_v002(Human human) {
		super(human);
	}
	
	/**
	 * 拷贝构造函数，方便热更时用旧的实例构造新的实例，这样旧实例的状态就保存下来了
	 * @param other
	 */
	public ModTest_v002(ModTest other) {
		super(other);
		if (other instanceof ModTest_v002) {
			this.i_v002 = ((ModTest_v002) other).i_v002;
		}
	}

	@Override
	public void doSomeThing() {
		super.doSomeThing();
		System.out.println("i_v002=" + i_v002);
	}

}
