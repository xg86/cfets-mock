package com.xquant.platform.component.mock.listener;

import java.util.Date;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.cfets.trade.protocol.util.CfetsTradeSerializeUtil;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.mock.MessageMockManager;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.ResolverMessageInfo;
import com.xquant.platform.component.mock.netty.event.CfetsShortLiveResponseEvent;

/**
 * 用于接收同步发送成功的消息
 * 
 * @author Administrator
 *
 */
@Component
public class CfetsShortLiveResponseEventListener extends MessageMockManager
		implements ApplicationListener<CfetsShortLiveResponseEvent> {

	@Override
	public void onApplicationEvent(final CfetsShortLiveResponseEvent event) {

		threadService.execute(new Runnable() {

			@Override
			public void run() {

				if (logger.isInfoEnabled()) {
					logger.info("received cfets quote and sync-respond successfully:\n" + CfetsTradeSerializeUtil.toXml(event.getMessage()));
				}

				CfetsTradeMessage originMessage = event.getMessage();

				/*
				 * 1. 进行解析 获取报价类型和消息模板
				 */
				ResolverMessageInfo resolverMessageInfo = new ResolverMessageInfo(null, originMessage, null,
						TargetSystemEnum.CFETS);
				messageResolverManager.resolveOptypeAndFillTempIfNull(resolverMessageInfo);

				/*
				 * 2. 根据报价类型 消息模板 原消息获取新的报文消息
				 */
				@SuppressWarnings("rawtypes")
				MockMessageInfo messageInfo = messageMessageInfoBuilderManager.build(
						resolverMessageInfo.getMessageTempldate(), originMessage, resolverMessageInfo.getOptype(),
						new Date());

				if (logger.isInfoEnabled()) {
					logger.info("build cfets asyn-response message successfully:\n" + CfetsTradeSerializeUtil.toXml(messageInfo.getMessage()));
				}

				/*
				 * 进行消息的处理(入库和发送)
				 */
				executeTask(messageInfo);
			}
		});

	}

}
