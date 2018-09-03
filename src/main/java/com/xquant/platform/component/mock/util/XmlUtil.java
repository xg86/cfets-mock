package com.xquant.platform.component.mock.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.xquant.cfets.trade.protocol.util.CfetsTradeSerializeUtil;

import xquant.xswap.util.XSwapSerializeUtil;

/**   
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.cfets.util 
 * @author: guanglai.zhou   
 * @date: 2018-06-12 11:14:44
 */
public class XmlUtil {
	
	/**
	 * 通过以下两个标记从文件路径中分离中对象类型
	 */
	public final static String LEFT_SIGN = "_";
	public final static String RIGHT_SIGN = ".xml";

	public final static String MESSAGE_PACKAGE_NAME = "com.xquant.cfets.trade.protocol.message";
	public final static String XSWAP_MESSAGE_PACKAGE_NAME = "xquant.xswap.protocol";
	
	public static String getStringFromXml(String fileName) {
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(fileName);
			InputSource inputSource = new InputSource(inputStream);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputSource);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer newTransformer = tf.newTransformer();
			newTransformer.setOutputProperty("encoding", "UTF-8");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			newTransformer.transform(new DOMSource(doc), new StreamResult(bos));
			return bos.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 从xml文件中读出按cfets协议包解析的对象
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Object getInstanceFormXml(String fileName) {
		String xmlStr = getStringFromXml(fileName);
		String typeString = MESSAGE_PACKAGE_NAME + "." + StringUtils.substringBetween(fileName, LEFT_SIGN, RIGHT_SIGN);
		Class<?> type = null;
		try {
			type = ClassUtils.getClass(typeString);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return CfetsTradeSerializeUtil.fromXml(type, xmlStr);
	}
	
	/**
	 * 从xml文件中读出按cfets协议包解析的对象
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Object getInstanceFormXml4Xswap(String fileName) {
		String xmlStr = getStringFromXml(fileName);
		String typeString = XSWAP_MESSAGE_PACKAGE_NAME + "." + StringUtils.substringBetween(fileName, LEFT_SIGN, RIGHT_SIGN);
		Class<?> type = null;
		try {
			type = ClassUtils.getClass(typeString);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return XSwapSerializeUtil.fromXml(type, xmlStr);
	}
	
	public static void main(String[] args) throws Exception {
	    String stringXml = getStringFromXml("cfets/recv/11101_CfetsTradeBondDialogueQuoteReceiveMessage.xml");
	    System.out.println(stringXml);
	}

}
