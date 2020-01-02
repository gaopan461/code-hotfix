package org.jow.hotfix.classloader.module.test;

import org.jow.hotfix.classloader.Human;
import org.jow.hotfix.classloader.module.ModBaseImpl;

/**
 * 用来测试热更的模块
 * @author gaopan
 */
public class ModTestImpl extends ModBaseImpl implements ModTest {
	/** 测试热更的成员变量，请不要申明成private，否则热更时无法修改 */
	protected int i = 1;
	
	public ModTestImpl(Human human) {
		super(human);
	}
	
	/**
	 * 拷贝构造函数，方便热更时用旧的实例构造新的实例，这样旧实例的状态就保存下来了
	 * @param other
	 */
	public ModTestImpl(ModTest other) {
		super(other.getHuman());
		this.i = other.getI();
	}
	
	/**
	 * 测试热更的成员函数，请不要申明成private，否则热更时无法修改
	 */
	@Override
	public void doSomeThing() {
		System.out.println("对象类型：" + this.getClass().getSimpleName() + ",类加载器:" + this.getClass().getClassLoader());
		System.out.println("i=" + i);
	}

	@Override
	public int getI() {
		return i;
	}

}
