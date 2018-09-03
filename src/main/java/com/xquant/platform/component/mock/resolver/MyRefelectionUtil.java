package com.xquant.platform.component.mock.resolver;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.Assert;

public class MyRefelectionUtil {

	/**
	 * 判断该对象是否包含该属性（不区分大小写） 如果存在该属性则返回属性 否则返回null
	 * 
	 * @param obj
	 * @param filedName
	 * @return
	 */
	public static Field hasFieldIgnoreCase(Object obj, String filedName) {
		Assert.notNull(obj, "obj can not be null");
		Assert.notNull(filedName, "filedName can not be null");
		Field[] allFields = FieldUtils.getAllFields(obj.getClass());
		for (Field field : allFields) {
			if (filedName.equalsIgnoreCase(field.getName())) {
				return field;
			}
		}
		return null;
	}
	
	/**
	 * 存在该属性则取当前值 否则为0
	 * 
	 * @param body
	 * @param filedName
	 * @return
	 */
	public static String getFiledValue(Object body, String filedName) {
		Field hasField = MyRefelectionUtil.hasFieldIgnoreCase(body, filedName);
		if (hasField != null) {
			try {
				Object value = FieldUtils.readField(body, hasField.getName(), true);
				if(value == null) {
					return "0";
				}
				String strValue =  value.toString();
				if(StringUtils.isBlank(strValue)) {
					return "0";
				}
				return strValue;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("解析对象中的属性出现异常,对象类型和属性名称为" + body.getClass().getName() + "," + hasField.getName());
		} else {
			return "0";
		}
	}
}
