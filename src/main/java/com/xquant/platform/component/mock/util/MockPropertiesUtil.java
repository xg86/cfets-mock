package com.xquant.platform.component.mock.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import io.netty.util.CharsetUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.back.util
 * @author: guanglai.zhou
 * @date: 2018-08-16 09:00:29
 */
public class MockPropertiesUtil {

	public static final String FILE_NAME = "conf" + File.separator + "myMockConfig.properties";

	/**
	 * 传入properties文件中key获取该文件中对应的value值,并加一进行保存
	 * 
	 * @param key
	 * @return
	 */
	public static String getValueAndThenIncrease(String key) {
		String oldValue = readProperties(key);
		if (oldValue == null) {
			oldValue = "0";
		}
		String newValue = updateNumPartOfString(oldValue, 1);
		// 如果新值的长度大于原来的长度 可能整数部分超过了位数 需要在原来值的基础上将整数部分置零 重新开始
		if (newValue.length() > oldValue.length()) {
			newValue = emptyNumPartOfString(oldValue);
		}
		updateProperties(key, newValue);
		return newValue;
	}

	public static String readProperties(String key) {
		File file = new File(FILE_NAME);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileSystemResource resource = new FileSystemResource(file);
		EncodedResource encodeResource = new EncodedResource(resource, CharsetUtil.ISO_8859_1);
		try {
			Properties properties = PropertiesLoaderUtils.loadProperties(encodeResource);
			return (String) properties.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void updateProperties(String key, String value) {
		try {
			File file = new File(FILE_NAME);
			Properties properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(file));
			properties.put(key, value);
			properties.store(new FileOutputStream(file), "udpate value");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static String getValueWithKey(String key, int addend) {
		File file = new File("myconfig.ini");
		ObjectInputStream inputObject = null;
		ObjectOutputStream outObject = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			inputObject = new ObjectInputStream(fileInputStream);
			Map<String, String> map = (Map<String, String>) inputObject.readObject();
			String value = map.get(key);
			value = updateNumPartOfString(value, addend);
			map.put(key, value);
			FileOutputStream fileOutStream = new FileOutputStream(file);
			outObject = new ObjectOutputStream(fileOutStream);
			outObject.writeObject(map);
			return map.get(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputObject != null) {
					inputObject.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (outObject != null) {
					outObject.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void init(String key, String value) {
		File file = new File("myconfig.ini");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			file.setReadable(true);
			file.setWritable(true);
			file.setExecutable(true);
		}

		FileOutputStream fileOutStream = null;
		ObjectOutputStream outObject = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put(key, value);
			fileOutStream = new FileOutputStream(file);
			outObject = new ObjectOutputStream(fileOutStream);
			outObject.writeObject(map);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outObject != null) {
					outObject.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void putValueWithKey(String key, String value) {
		File file = new File("myconfig.ini");
		ObjectInputStream inputObject = null;
		ObjectOutputStream outObject = null;
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			file.setReadable(true);
			file.setWritable(true);
			file.setExecutable(true);
		}
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			inputObject = new ObjectInputStream(fileInputStream);
			Map<String, String> map = (Map<String, String>) inputObject.readObject();
			map.put(key, value);
			FileOutputStream fileOutStream = new FileOutputStream(file);
			outObject = new ObjectOutputStream(fileOutStream);
			outObject.writeObject(map);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputObject != null) {
					inputObject.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (outObject != null) {
					outObject.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将字符串最后部分转化为0
	 * 
	 * @param numString
	 * @return
	 */
	public static String emptyNumPartOfString(String numString) {
		char[] cs = numString.toCharArray();
		for (int i = cs.length - 1; i >= 0; i--) {
			if (Character.isDigit(cs[i])) {
				cs[i] = '0';
			} else {
				break;
			}
		}
		return new String(cs);
	}

	/**
	 * 将一个后部分包含数字的字符串进行数字部分的数字按num递增 如果后面的不为数字 则不处理
	 * 
	 * @param numStr
	 * @param num
	 * @return
	 */
	public static String updateNumPartOfString(String numString, int addend) {
		char[] cs = numString.toCharArray();
		int index = 0;
		int start = cs.length - 1;
		for (int i = start; i >= 0; i--) {
			if (Character.isWhitespace(cs[i]) || Character.isAlphabetic(cs[i])) {
				break;
			} else if (Character.isDigit(cs[i])) {
				index++;
			} else {
				break;
			}
		}
		String digitPart = null;
		String leftPart = null;
		int digitLength = 0;
		if (index != 0) {

			digitPart = numString.substring(cs.length - index, cs.length);
			leftPart = numString.substring(0, cs.length - index);
			digitLength = digitPart.length();
		}
		int num = 0;
		if (StringUtils.isNoneBlank(digitPart)) {
			num = Integer.parseInt(digitPart);
			num += addend;
		}
		if (num != 0) {
			digitPart = String.valueOf(num);
			int newlength = digitPart.length();
			if (newlength < digitLength) {
				digitPart = String.format("%0" + digitLength + "d", num);
			}
			numString = leftPart + digitPart;
		}
		return numString;
	}

	public static void main(String[] args) {
		System.out.println(getValueAndThenIncrease("serialNo"));

		// updateProperties("serialNo", "123456");

		// init("serialNo", "10000");
		// String value = getValueWithKey("serialNo", 1);
		// System.out.println(value);
	}
}
