package com.xquant.platform.component.mock.listener;

import java.util.Date;
import java.util.EnumSet;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.itf.cfets.xswap.api.enums.XSwapActionEnum;
import com.xquant.platform.component.mock.MessageMockManager;
import com.xquant.platform.component.mock.builder.md.XSwapMDQuoteResMessageBuilder;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.ResolverMessageInfo;
import com.xquant.platform.component.mock.netty.event.XswapShortLiveResponseEvent;

import xquant.xswap.protocol.XSwapMDQuoteReqMessage;
import xquant.xswap.protocol.XSwapMessage;
import xquant.xswap.util.XSwapSerializeUtil;

/**
 * 用于接收同步发送成功的消息
 * 
 * @author Administrator
 *
 */
@Component
public class XswapShortLiveResponseEventListener extends MessageMockManager
		implements ApplicationListener<XswapShortLiveResponseEvent> {

	@Override
	public void onApplicationEvent(final XswapShortLiveResponseEvent event) {

		threadService.execute(new Runnable() {

			@Override
			public void run() {

				if (logger.isInfoEnabled()) {
					logger.info("received xswap quote and sync-respond successfully:\n"
							+ XSwapSerializeUtil.toXml(event.getMessage()));
				}

				XSwapMessage originMessage = event.getMessage();
				if (isQuote(originMessage)) {
					/*
					 * 1. 进行解析 获取报价类型和消息模板
					 */
					ResolverMessageInfo resolverMessageInfo = new ResolverMessageInfo(null, originMessage, null,
							TargetSystemEnum.XSWAP);
					messageResolverManager.resolveOptypeAndFillTempIfNull(resolverMessageInfo);

					/*
					 * 2. 根据报价类型 消息模板 原消息获取新的报文消息
					 */
					@SuppressWarnings("rawtypes")
					MockMessageInfo messageInfo = messageMessageInfoBuilderManager.build(
							resolverMessageInfo.getMessageTempldate(), originMessage, resolverMessageInfo.getOptype(),
							new Date());

					if (logger.isInfoEnabled()) {
						logger.info("build xswap asyn-response message successfully:\n"
								+ XSwapSerializeUtil.toXml(messageInfo.getMessage()));
					}

					/*
					 * 进行消息的处理(入库和发送)
					 */
					executeTask(messageInfo);
				} else {

					// 市场行情
					if (XSwapActionEnum.MD_QUOTE_REQ.getValue().equals(originMessage.getHeader().getAction())) {
						XSwapMDQuoteReqMessage message = (XSwapMDQuoteReqMessage) originMessage;
						@SuppressWarnings("rawtypes")
						MockMessageInfo messageInfo = new XSwapMDQuoteResMessageBuilder().build(message);
						executeTask(messageInfo);
					}

				}

			}

			private boolean isQuote(XSwapMessage originMessage) {
				EnumSet<XSwapActionEnum> supportSet = EnumSet.of(XSwapActionEnum.ORDER_CREATE,
						XSwapActionEnum.ORDER_CANCEL);
				XSwapActionEnum currEnum = XSwapActionEnum.getEnumName(originMessage.getHeader().getAction());
				return supportSet.contains(currEnum);
			}
		});

	}

}
