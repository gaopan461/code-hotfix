package org.jow.hotfix.override;

import java.lang.reflect.Constructor;
import java.util.Scanner;

import org.jow.hotfix.override.module.EModType;

public class Main {

	public static void main(String[] args) {
		Human human = new Human();
		human.getModTest().doSomeThing();
		
		Scanner scanner = new Scanner(System.in);
		String str;
		do {
			System.out.println("请输入需要热更的模块名和新模块的类名:");
			str = scanner.nextLine();
			if (str == null || str.trim().isEmpty()) {
				break;
			}
			
			String[] strs = str.split(",");
			if (strs.length != 2) {
				System.err.println("热更失败：输入格式错误");
				continue;
			}
			
			EModType modType;
			try {
				modType = EModType.valueOf(strs[0]);
			} catch (IllegalArgumentException e) {
				System.err.println(String.format("热更失败：输入的模块名%s不正确", strs[0]));
				continue;
			}
			
			Class<?> clazz;
			try {
				clazz = Class.forName(strs[1]);
			} catch (ClassNotFoundException e) {
				System.err.println(String.format("热更失败：输入类%s，原因：不存在", strs[1]));
				continue;
			}
			
			// 检测指定类是否满足热更条件
			Constructor<?> copyConstructor;
			try {
				copyConstructor = modType.checkHotfix(clazz);
			} catch (Exception e) {
				System.err.println(String.format("热更失败：输入类%s，原因：%s", strs[1], e.getMessage()));
				continue;
			}
			
			try {
				// 这一步更新模块的构造函数，以后用新的函数构造模块，所以经过这一步，新创建的对象都使用的热更后的新类型
				Human.updateModuleConstructor(modType, clazz);
				// 这一步升级旧模块的实例
				human.hotfixModule(modType, copyConstructor);
			} catch (Exception e) {
				System.err.println(String.format("热更失败：输入类%s，原因：%s", strs[1], e.getMessage()));
			}
			
			System.out.println(String.format("\n=======%s热更成功===========\n", strs[1]));
			
			// 看看旧实例是否更新成功
			human.getModTest().doSomeThing();
			// 看看新实例是否更新成功
			new Human().getModTest().doSomeThing();
		} while (true);
		
		scanner.close();
	}

}
