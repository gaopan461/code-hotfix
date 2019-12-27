package org.jow.hotfix.classloader;

import java.lang.reflect.Constructor;
import java.util.Scanner;

import org.jow.hotfix.classloader.module.EModType;
import org.jow.hotfix.classloader.module.ModClassLoader;

public class Main {

	public static void main(String[] args) {
		Human human = new Human();
		human.doSomeThing();
		
		Scanner scanner = new Scanner(System.in);
		String str;
		do {
			System.out.println("请输入需要热更的模块名:");
			str = scanner.nextLine();
			if (str == null || str.trim().isEmpty()) {
				break;
			}
			
			EModType modType;
			try {
				modType = EModType.valueOf(str);
			} catch (IllegalArgumentException e) {
				System.err.println(String.format("热更失败：输入的模块名%s不正确", str));
				continue;
			}
			
			// 创建新的类加载器，用来重新加载热更类
			ClassLoader classLoader = new ModClassLoader();
			// 检测指定类是否满足热更条件
			@SuppressWarnings("unused")
			Constructor<?> copyConstructor;
			try {
				copyConstructor = modType.checkHotfix(classLoader);
			} catch (Exception e) {
				System.err.println(String.format("热更失败：模块%s，原因：%s", str, e.getMessage()));
				continue;
			}
			
			try {
				// 这一步更新模块的构造函数，以后用新的函数构造模块，所以经过这一步，新创建的对象都使用的热更后的新类型
				modType.updateModuleConstructor(classLoader);
				// 这一步升级旧模块的实例；这里无法升级成功，会报参数类型不匹配的异常
//				human.hotfixModule(modType, copyConstructor);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(String.format("热更失败：模块%s，原因：%s", str, e.getMessage()));
				continue;
			}
			
			System.out.println(String.format("\n=======%s热更成功===========\n", str));
			
			// 看看旧实例是否更新成功
			System.out.println("============调用旧实例的doSomeThing方法===================");
			human.doSomeThing();
			// 看看新实例是否更新成功
			System.out.println("============调用新实例的doSomeThing方法===================");
			new Human().doSomeThing();
		} while (true);
		
		scanner.close();
	}

}
