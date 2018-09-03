/**
 * 
 */
package com.xquant.platform.component.mock.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.reflect.MethodUtils;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-07-25 16:39:22
 */
public class MyMethodUtils extends MethodUtils {

	/**
	 * 如果该类中存在方法 则进行设值 否则不进行处理 仅支持一个值
	 * 
	 * @param obj
	 * @param methodName
	 * @param value
	 * @param parameterTypes
	 */
	public static void setMethodValueIfExists(Object obj, String methodName, Object value, Class<?>... parameterTypes) {

		Method method = getAccessibleMethod(obj.getClass(), methodName, parameterTypes);
		if (method != null) {
			try {
				invokeMethod(obj, methodName, value);
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
