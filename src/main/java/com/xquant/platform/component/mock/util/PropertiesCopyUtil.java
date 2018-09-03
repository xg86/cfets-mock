package com.xquant.platform.component.mock.util;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessageBody;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-06-18 10:09:37
 */
public class PropertiesCopyUtil {

	/**
	 * 将一个对象中的参数拷贝到另一个对象中 参数名称和参数类型一致的情况下
	 * 
	 * @param source
	 * @param target
	 * @return 已经拷贝的属性名称集合
	 */
	public static Set<String> fieldCopyIfNull(Object source, Object target) {

		Set<String> fieldFilled = new HashSet<String>();
		Set<String> needCopyPropertySet = new HashSet<String>();
		Set<String> sourceFields = new HashSet<String>();
		Field[] allsourceFields = FieldUtils.getAllFields(source.getClass());
		Field[] alltargetFields = FieldUtils.getAllFields(target.getClass());
		for (Field field : allsourceFields) {
			sourceFields.add(field.getName());
		}
		for (Field field : alltargetFields) {
			// 名称相同
			if (sourceFields.contains(field.getName())) {
				Field sourcefield = FieldUtils.getField(source.getClass(), field.getName(), true);
				if (sourcefield != null) {
					// 处理基本数据类型 类型相同
					if (ClassUtils.isPrimitiveOrWrapper(field.getType()) && sourcefield.getType() == field.getType()) {
						needCopyPropertySet.add(field.getName());
					} else if (field.getType() == String.class && sourcefield.getType() == String.class) {
						needCopyPropertySet.add(field.getName());
					} else {
						// 处理引用数据类型
						try {
							Object srcObjectOfField = FieldUtils.readField(source, field.getName(), true);
							Object tarObjectOfField = FieldUtils.readField(target, field.getName(), true);
							if (srcObjectOfField != null) {
								if (tarObjectOfField == null) {
									tarObjectOfField = field.getClass().newInstance();
								}
								fieldFilled.addAll(fieldCopyIfNull(srcObjectOfField, tarObjectOfField));
							}

						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		for (String fieldName : needCopyPropertySet) {
			String filledField = fieldCopy(source, target, fieldName);
			if (StringUtils.isNoneBlank(filledField)) {
				fieldFilled.add(filledField);
			}
		}
		return fieldFilled;
	}

	/**
	 * @param source
	 * @param target
	 * @param fieldName
	 */
	private static String fieldCopy(Object source, Object target, String fieldName) {
		// TODO Auto-generated method stub
		try {
			Object sourceValue = FieldUtils.readField(source, fieldName, true);
			if (sourceValue != null) {
				// 不处理空字符串
				if (TypeUtils.isInstance(sourceValue, String.class)) {
					String sourceString = (String) sourceValue;
					if (StringUtils.isBlank(sourceString)) {
						return null;
					}
				}
				FieldUtils.writeField(target, fieldName, sourceValue, true);
				return fieldName;
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取一个类中不为空的属性值集合
	 * 
	 * @param obj
	 * @param downTrace
	 *            是否继续往下进行查询引用型对象属性的属性值
	 * @return
	 */
	public static Set<String> extractFieldsNotNull(Object obj, boolean downTrace) {
		Set<String> notNullSet = new HashSet<String>();
		Field[] allFields = FieldUtils.getAllFields(obj.getClass());
		for (Field field : allFields) {
			try {
				if (ClassUtils.isPrimitiveOrWrapper(field.getType()) || String.class == field.getType()) {
					if (FieldUtils.readField(obj, field.getName(), true) != null) {
						notNullSet.add(field.getName());
					}
				} else if (downTrace) {
					Object readObject = FieldUtils.readField(obj, field.getName(), true);
					if (readObject != null) {
						notNullSet.addAll(extractFieldsNotNull(readObject, true));
						notNullSet.add(field.getName());
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return notNullSet;
	}

	/**
	 * 获取属性为空的属性集合
	 * 
	 * @param obj
	 * @param downTrace
	 * @return
	 */
	public static Set<String> extractFieldsNull(Object obj, boolean downTrace) {
		Set<String> nullSet = new HashSet<String>();
		Field[] allFields = FieldUtils.getAllFields(obj.getClass());
		for (Field field : allFields) {
			try {
				if (ClassUtils.isPrimitiveOrWrapper(field.getType()) || String.class == field.getType()) {
					if (FieldUtils.readField(obj, field.getName(), true) == null) {
						nullSet.add(field.getName());
					}
				} else if (downTrace) {
					Object readObject = FieldUtils.readField(obj, field.getName(), true);
					if (readObject != null) {
						nullSet.addAll(extractFieldsNull(readObject, downTrace));
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return nullSet;
	}

	public static void main(String[] args) throws Exception {
		CfetsTradeBondDialogueQuoteResMessage reMessage = new CfetsTradeBondDialogueQuoteResMessage();
		CfetsTradeBondDialogueQuoteResMessageBody resBody = new CfetsTradeBondDialogueQuoteResMessageBody();
		reMessage.setBody(resBody);
		CfetsTradeBondDialogueQuoteMessage message = new CfetsTradeBondDialogueQuoteMessage();
		CfetsTradeBondDialogueQuoteMessageBody body = new CfetsTradeBondDialogueQuoteMessageBody();
		message.setBody(body);
		body.setPartyID("123456");
		resBody.setQuoteID("54321");
		// BeanUtils.copyProperties(message, reMessage);
		// BeanUtils.copyProperties(body, resBody);
		Set<String> fieldCopyIfNotNull = fieldCopyIfNull(message, reMessage);
		System.out.println(resBody.getPartyID());
		System.out.println(resBody.getQuoteID());
		System.out.println(fieldCopyIfNotNull);

		System.out.println(extractFieldsNotNull(message, true));
		System.out.println(extractFieldsNull(message, true));

		Set<String> set1 = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();
		set1.add("1");
		set1.add("2");
		set1.add("3");
		set1.add("4");
		set1.add("5");
		set2.add("2");
		set2.add("3");
		set2.add("5");
		set2.add("6");
		System.out.println(set1);
		System.out.println(set2);
		set1.retainAll(set2);
		System.out.println(set1);
		System.out.println(set2);
		// CollectionUtils.retainAll(needCopyPropertySet, fieldNotNullInSource);
	}

}
