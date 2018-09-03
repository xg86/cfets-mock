package com.xquant.platform.component.mock.netty;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyConfirmResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.netty.MyCfetsShortLiveNettyServer.CfetsRequestHandler;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.cfets.netty
 * @author: guanglai.zhou
 * @date: 2018-05-28 11:08:38
 */
public class MyRequestHandler4CfetsProtocol implements CfetsRequestHandler {

	private static Logger logger = LoggerFactory.getLogger(MyRequestHandler4CfetsProtocol.class);
	
	@Override
	public CfetsTradeMessage handleRequest(CfetsTradeMessage request) {

		Class<?> type = reqMessageCfetsMap.get(request.getClass().getSimpleName());
		if (type != null) {
			try {
				Object message = type.newInstance();
				MethodUtils.invokeMethod(message, "setHeader", request.getHeader());
				Object requestBody = MethodUtils.invokeMethod(request, "getBody");
				String messageBodyName = type.getName() + "Body";
				Object messageBody = ClassUtils.getClass(messageBodyName, true).newInstance();
				BeanUtils.copyProperties(messageBody, requestBody);
				MethodUtils.invokeMethod(message, "setBody", messageBody);
				((CfetsTradeMessage) message).getHeader().setMsgType(MsgTypeEnum.ACK.getValue());
				((CfetsTradeMessage) message).getHeader().setVersion(MockVersion.VERSION);
				return (CfetsTradeMessage) message;
			} catch (InstantiationException e) {
				logger.error("MyRequestHandler4CfetsProtocol exception ", e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				logger.error("MyRequestHandler4CfetsProtocol exception ", e);
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				logger.error("MyRequestHandler4CfetsProtocol exception ", e);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				logger.error("MyRequestHandler4CfetsProtocol exception ", e);
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				logger.error("MyRequestHandler4CfetsProtocol exception ", e);
				e.printStackTrace();
			}
		}
		return null;
	}

	public static final Map<String, Class<?>> reqMessageCfetsMap = new ConcurrentHashMap<String, Class<?>>();

	static {
		try {
			reqMessageCfetsMap.put("CfetsTradeBondDialogueQuoteMessage", CfetsTradeBondDialogueQuoteResMessage.class);
			reqMessageCfetsMap.put("CfetsTradeBondDialogueQuoteReplyMessage",
					CfetsTradeBondDialogueQuoteReplyResMessage.class);
			reqMessageCfetsMap.put("CfetsTradeCollateralDialogueQuoteMessage",
					CfetsTradeCollateralDialogueQuoteResMessage.class);
			reqMessageCfetsMap.put("CfetsTradeCollateralDialogueQuoteReplyMessage",
					CfetsTradeCollateralDialogueQuoteReplyResMessage.class);
			reqMessageCfetsMap.put("CfetsTradeBondClickDealQuoteMessage", CfetsTradeBondClickDealQuoteResMessage.class);
			reqMessageCfetsMap.put("CfetsTradeBondPriceLimitQuoteMessage",
					CfetsTradeBondPriceLimitQuoteResMessage.class);
			reqMessageCfetsMap.put("CfetsTradeBondMarketMakingQuoteMessage",
					CfetsTradeBondMarketMakingQuoteResMessage.class);
			reqMessageCfetsMap.put("CfetsTradeBondRFQMessage", CfetsTradeBondRFQResMessage.class);
			reqMessageCfetsMap.put("CfetsTradeBondRFQRejectMessage", CfetsTradeBondRFQRejectResMessage.class);
			reqMessageCfetsMap.put("CfetsTradeBondRFQReplyMessage", CfetsTradeBondRFQReplyResMessage.class);
			reqMessageCfetsMap.put("CfetsTradeBondRFQReplyConfirmMessage",
					CfetsTradeBondRFQReplyConfirmResMessage.class);

		} catch (Exception e) {
			logger.error("MyRequestHandler4CfetsProtocol exception ", e);
			e.printStackTrace();
		}
	}
}
