package org.jow.hotfix.classloader.module.test;

import org.jow.hotfix.classloader.module.ModBase;

/**
 * 用来测试热更的模块
 * @author gaopan
 */
public interface ModTest extends ModBase {
	
	void doSomeThing();
	
	int getI();

}
