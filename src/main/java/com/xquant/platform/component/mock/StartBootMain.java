package com.xquant.platform.component.mock;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class StartBootMain {

	public static void main(String[] args) {
		GenericApplicationContext context = new GenericApplicationContext();

		/*
		 * 在容器外注册bean
		 */
		// context.getBeanFactory().registerSingleton(beanName, singletonObject);
		// DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// beanFactory.registerBeanDefinition(beanName, beanDefinition);
		// beanFactory.registerSingleton(beanName, singletonObject);

		new XmlBeanDefinitionReader(context).loadBeanDefinitions("classpath:applicaitonMockContext.xml");
		context.refresh();
		// ClassPathXmlApplicationContext ctx = new
		// ClassPathXmlApplicationContext("classpath:applicaitonMockContext.xml");

		try {
			System.out.println("==============Spring container startup success===================");
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// ctx.close();
	}

}
