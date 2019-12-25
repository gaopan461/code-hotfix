package org.jow.hotfix.override.module.test;

import org.jow.hotfix.override.Human;

/**
 * 用来热更{@link ModTest}的第一个版本
 * @author gaopan
 */
public class ModTest_v001 extends ModTest {
	/** 热更类可以新增成员变量 */
	protected int i_v001 = 1;
	
	public ModTest_v001(Human human) {
		super(human);
	}

	/**
	 * 拷贝构造函数，方便热更时用旧的实例构造新的实例，这样旧实例的状态就保存下来了
	 * @param other
	 */
	public ModTest_v001(ModTest other) {
		super(other);
		if (other instanceof ModTest_v001) {
			this.i_v001 = ((ModTest_v001) other).i_v001;
		}
	}

	@Override
	public void doSomeThing() {
		super.doSomeThing();
		System.out.println("i_v001=" + i_v001);
	}

}
