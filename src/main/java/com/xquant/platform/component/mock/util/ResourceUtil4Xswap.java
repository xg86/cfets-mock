/**
 * 
 */
package com.xquant.platform.component.mock.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xquant.xswap.protocol.XSwapMessage;
import xquant.xswap.protocol.XSwapOrderActionResMessage;
import xquant.xswap.protocol.XSwapOrderResMessage;
import xquant.xswap.protocol.XSwapOrderStatusPushMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.cfets.util
 * @author: guanglai.zhou
 * @date: 2018-06-12 11:20:30
 */
public class ResourceUtil4Xswap {

	public static Map<String, XSwapMessage> typeMessageMap = new ConcurrentHashMap<String, XSwapMessage>();
	/**
	 * 本方新增利率互换报价同步返回
	 */
	private static final String XSWAP_ADD_ACK_FILE = "xswap/70101A_XSwapMessage.xml";
	/**
	 * 本方新增利率互换报价异步返回成功
	 */
	private static final String XSWAP_ADD_RES_FILE = "xswap/70102_XSwapOrderResMessage.xml";
	/**
	 * 本方新增利率互换报价异步状态推送
	 */
	private static final String XSWAP_ADD_PUSH_FILE = "xswap/70103_XSwapOrderStatusPushMessage.xml";
	/**
	 * 本方撤销利率互换报价同步返回
	 */
	private static final String XSWAP_CANCEL_ACK_FILE = "xswap/70201A_XSwapMessage.xml";
	/**
	 * 本方撤销利率互换报价异步返回成功
	 */
	private static final String XSWAP_CANCEL_RES_FILE = "xswap/70202_XSwapOrderActionResMessage.xml";
	/**
	 * 本方撤销利率互换报价异步状态推送
	 */
	private static final String XSWAP_CANCEL_PUSH_FILE = "xswap/70203_XSwapOrderStatusPushMessage.xml";

	/**
	 * 新增利率互换报价同步返回
	 * 
	 * @return @
	 */
	public static XSwapMessage getXSwapMessage4AddAck() {
		XSwapMessage message = null;

		String fileName = XSWAP_ADD_ACK_FILE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (XSwapMessage) typeMessageMap.get(fileName);
			}
		}
		message = (XSwapMessage) XmlUtil.getInstanceFormXml4Xswap(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 撤销利率互换报价同步返回
	 * 
	 * @return @
	 */
	public static XSwapMessage getXSwapMessage4CancelAck() {
		XSwapMessage message = null;

		String fileName = XSWAP_CANCEL_ACK_FILE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (XSwapMessage) typeMessageMap.get(fileName);
			}
		}
		message = (XSwapMessage) XmlUtil.getInstanceFormXml4Xswap(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 本方新增报价异步返回
	 * 
	 * @return @
	 */
	public static XSwapOrderResMessage getXSwapMessage4AddResPush() {
		XSwapOrderResMessage message = null;

		String fileName = XSWAP_ADD_RES_FILE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (XSwapOrderResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (XSwapOrderResMessage) XmlUtil.getInstanceFormXml4Xswap(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方新增利率互换报价异步状态推送
	 * 
	 * @return @
	 */
	public static XSwapOrderStatusPushMessage getXSwapMessage4AddStatusPush() {
		XSwapOrderStatusPushMessage message = null;

		String fileName = XSWAP_ADD_PUSH_FILE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (XSwapOrderStatusPushMessage) typeMessageMap.get(fileName);
			}
		}
		message = (XSwapOrderStatusPushMessage) XmlUtil.getInstanceFormXml4Xswap(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方撤销报价异步返回
	 * 
	 * @return @
	 */
	public static XSwapOrderActionResMessage getXSwapMessage4CancelResPush() {
		XSwapOrderActionResMessage message = null;

		String fileName = XSWAP_CANCEL_RES_FILE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (XSwapOrderActionResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (XSwapOrderActionResMessage) XmlUtil.getInstanceFormXml4Xswap(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方撤销利率互换报价异步状态推送
	 * 
	 * @return @
	 */
	public static XSwapOrderStatusPushMessage getXSwapMessage4CancelStatusPush() {
		XSwapOrderStatusPushMessage message = null;

		String fileName = XSWAP_CANCEL_PUSH_FILE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (XSwapOrderStatusPushMessage) typeMessageMap.get(fileName);
			}
		}
		message = (XSwapOrderStatusPushMessage) XmlUtil.getInstanceFormXml4Xswap(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

}
