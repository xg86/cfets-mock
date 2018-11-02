package com.xquant.platform.component.mock.persist;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeExecutionReportMessage;
import com.xquant.platform.component.mock.service.push.TradeMessagePushService;

@Component
public class MessagePersistManagerDelegate {

	@SuppressWarnings("rawtypes")
	@Autowired
	private List<IMessagePersistServiceManager> messageServiceManagerList;

	@Autowired
	private TradeMessagePushService TradeMessagePushService;

	protected Logger logger = LoggerFactory.getLogger(MessagePersistManagerDelegate.class);

	@SuppressWarnings("unchecked")
	public void handle(Object obj) {

		try {
			/**
			 * 成交回报消息不需要入库处理
			 */
			if (CfetsTradeExecutionReportMessage.class.isInstance(obj)) {
				return;
			}
			/**
			 * 进行入库处理
			 */
			getSupportManager(obj).handle(obj);
		} catch (Exception e) {
			logger.error("MessagePersistManagerDelegate Exception ", e);
		} finally {
			TradeMessagePushService.addMessage(obj);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public IMessagePersistServiceManager getSupportManager(Object obj) {
		for (IMessagePersistServiceManager iMessageServiceManager : messageServiceManagerList) {
			if (iMessageServiceManager.support(obj.getClass())) {
				return iMessageServiceManager;
			}
		}
		throw new RuntimeException("unsupported type" + obj.getClass());
	}

}
