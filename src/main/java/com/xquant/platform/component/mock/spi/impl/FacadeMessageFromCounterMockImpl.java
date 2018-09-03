package com.xquant.platform.component.mock.spi.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.cfets.trade.protocol.util.CfetsTradeSerializeUtil;
import com.xquant.platform.component.mock.MessageMockManager;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.spi.FacadeMessageFromCounterMock;
import com.xquant.platform.component.mock.spi.FacadeMessageHandleBefore;

/**
 * 
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.spi.impl
 * @author: guanglai.zhou
 * @date: 2018-07-25 15:41:48
 */
@Component
public class FacadeMessageFromCounterMockImpl extends MessageMockManager implements FacadeMessageFromCounterMock {

	public static final Map<String, Class<?>> SUPPORT_RECEIVED_MESSAGE = new ConcurrentHashMap<String, Class<?>>();

	static {
		/**
		 * 对手新增 修改 撤销对话报价
		 */
		SUPPORT_RECEIVED_MESSAGE.put("2004", CfetsTradeBondDialogueQuoteReceiveMessage.class);
		/**
		 * 对手成交 拒绝对话报价
		 */
		SUPPORT_RECEIVED_MESSAGE.put("2000", CfetsTradeBondDialogueQuoteResMessage.class);
		/**
		 * 对手新增 修改 撤销质押式回购对话报价
		 */
		SUPPORT_RECEIVED_MESSAGE.put("2005", CfetsTradeCollateralDialogueQuoteReceiveMessage.class);
		/**
		 * 对手成交 拒绝质押式回购报价
		 */
		SUPPORT_RECEIVED_MESSAGE.put("2001", CfetsTradeCollateralDialogueQuoteResMessage.class);
		/**
		 * 对手新增 撤销请求报价
		 */
		SUPPORT_RECEIVED_MESSAGE.put("4004", CfetsTradeBondRFQReceiveMessage.class);
		/**
		 * 对手新增 修改 撤销请求回复报价
		 */
		SUPPORT_RECEIVED_MESSAGE.put("4005", CfetsTradeBondRFQReplyReceiveMessage.class);
		/**
		 * 对手成交请求回复
		 */
		SUPPORT_RECEIVED_MESSAGE.put("4001", CfetsTradeBondRFQReplyResMessage.class);
	}

	@Autowired(required = false)
	private FacadeMessageHandleBefore facadeMessageHandleBefore;

	@Override
	public void handleMessage(String xmlMessage) {
		
		try {
			String action = StringUtils.substringBetween(xmlMessage, "<action>", "</action>");
			if (StringUtils.isBlank(action)) {
				throw new RuntimeException("不是有效报文,不存在action字段或者为空");
			}
			Class<?> type = SUPPORT_RECEIVED_MESSAGE.get(action);
			if (type == null) {
				throw new RuntimeException("不支持的报价类型" + action);
			}
			CfetsTradeMessage message = (CfetsTradeMessage) CfetsTradeSerializeUtil.fromXml(type, xmlMessage);
			if (facadeMessageHandleBefore != null) {
				message = facadeMessageHandleBefore.handleBefore(message);
			}

			if (logger.isInfoEnabled()) {
				logger.info("received message from counter " + CfetsTradeSerializeUtil.toXml(message));
			}
			@SuppressWarnings("rawtypes")
			MockMessageInfo messageInfo = buildMessageInfoNoNeedBuild(message);
			if (logger.isInfoEnabled()) {
				logger.info("received message from counter ,and build " + messageInfo);
			}
			executeTask(messageInfo);
		} catch (Exception e) {
			logger.error("FacadeMessageFromCounterMock exception ", e);
		}
	}

}
