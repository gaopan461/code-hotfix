package org.jow.hotfix.instrumentation;

import java.io.File;
import java.util.Scanner;

import org.jow.hotfix.instrumentation.agent.ClassReloaderAgent;

public class Main {

	public static void main(String[] args) {
		Human human = new Human();
		human.doSomeThing();
		
		Scanner scanner = new Scanner(System.in);
		String str;
		do {
			System.out.println("请输入需要热更的类名:");
			str = scanner.nextLine();
			if (str == null || str.trim().isEmpty()) {
				break;
			}
			
			String filePath = System.getProperty("user.dir") + "/" + str.replace('.', '/') + ".class";
			File file = new File(filePath);
			if (!file.exists()) {
				System.err.println(String.format("热更失败：输入的类%s不存在", str));
				continue;
			}
			
			try {
				Class<?> clazz = Class.forName(str);
				ClassReloaderAgent.reload(clazz, file);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(String.format("热更失败：原因：%s", e.getMessage()));
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
