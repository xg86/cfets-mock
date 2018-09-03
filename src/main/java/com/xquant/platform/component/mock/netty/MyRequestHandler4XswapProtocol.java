package com.xquant.platform.component.mock.netty;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xquant.platform.component.mock.netty.MyXswapShortLiveNettyServer.XswapRequestHandler;

import xquant.xswap.protocol.XSwapMessage;
import xquant.xswap.protocol.XSwapOrderActionResMessage;
import xquant.xswap.protocol.XSwapOrderResMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.cfets.netty
 * @author: guanglai.zhou
 * @date: 2018-05-28 11:08:38
 */
public class MyRequestHandler4XswapProtocol implements XswapRequestHandler {

	private static Logger logger = LoggerFactory.getLogger(MyRequestHandler4XswapProtocol.class);
	
	@Override
	public XSwapMessage handleRequest(XSwapMessage request) {
		String simpleName = request.getClass().getSimpleName();
		Class<?> type = reqMessageXswapMap.get(simpleName);
		if (type != null) {
			try {
				Object message = type.newInstance();
				MethodUtils.invokeMethod(message, "setHeader", request.getHeader());
				Object requestBody = MethodUtils.invokeMethod(request, "getBody");
				String messageBodyName = type.getName().replace("Message", "") + "Body";
				Object messageBody = ClassUtils.getClass(messageBodyName, true).newInstance();
				BeanUtils.copyProperties(messageBody, requestBody);
				MethodUtils.invokeMethod(message, "setBody", messageBody);
				return (XSwapMessage) message;
			} catch (InstantiationException e) {
				logger.error("MyRequestHandler4XswapProtocol exception ", e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				logger.error("MyRequestHandler4XswapProtocol exception ", e);
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				logger.error("MyRequestHandler4XswapProtocol exception ", e);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				logger.error("MyRequestHandler4XswapProtocol exception ", e);
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				logger.error("MyRequestHandler4XswapProtocol exception ", e);
				e.printStackTrace();
			}
		}
		return null;
	}

	public static final Map<String, Class<?>> reqMessageXswapMap = new ConcurrentHashMap<String, Class<?>>();

	static {
		try {
			reqMessageXswapMap.put("XSwapOrderReqMessage", XSwapOrderResMessage.class);
			reqMessageXswapMap.put("XSwapOrderActionReqMessage", XSwapOrderActionResMessage.class);
		} catch (Exception e) {
			logger.error("MyRequestHandler4XswapProtocol exception ", e);
			e.printStackTrace();
		}
	}
}
