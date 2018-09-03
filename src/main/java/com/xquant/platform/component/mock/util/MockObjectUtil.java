package com.xquant.platform.component.mock.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

public class MockObjectUtil {

	/**
	 * 将该对象中所有的空的字符串类型的属性设置为null
	 * 
	 * @param obj
	 */
	public static void makeStrFieldNullIfBlank(Object obj) {
		Field[] allFields = FieldUtils.getAllFields(obj.getClass());
		for (Field field : allFields) {
			try {
				if (String.class.equals(field.getType())) {
					String getMethodName = "get" + StringUtils.capitalize(field.getName());
					String setMethodName = "set" + StringUtils.capitalize(field.getName());
					String value = (String) MethodUtils.invokeMethod(obj, getMethodName);
					if (StringUtils.isBlank(value)) {
						MethodUtils.invokeMethod(obj, setMethodName, new Object[] { null }, new Class<?>[] { String.class });
					}
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void makeStrFieldNullIfBlank(Object obj,Set<String> ignoreFields) {
		Field[] allFields = FieldUtils.getAllFields(obj.getClass());
		for (Field field : allFields) {
			
			if(!ignoreFields.contains(field.getName())) {
				try {
					if (String.class.equals(field.getType())) {
						String getMethodName = "get" + StringUtils.capitalize(field.getName());
						String setMethodName = "set" + StringUtils.capitalize(field.getName());
						String value = (String) MethodUtils.invokeMethod(obj, getMethodName);
						if (StringUtils.isBlank(value)) {
							MethodUtils.invokeMethod(obj, setMethodName, new Object[] { null }, new Class<?>[] { String.class });
						}
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
