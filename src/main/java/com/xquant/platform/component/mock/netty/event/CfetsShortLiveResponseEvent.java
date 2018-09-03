package com.xquant.platform.component.mock.netty.event;

import org.springframework.context.ApplicationEvent;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;

/**
 * 同步返回事件
 * 
 * @author Administrator
 *
 */
public class CfetsShortLiveResponseEvent extends ApplicationEvent {

	private static final long serialVersionUID = 5512438275505873982L;

	private CfetsTradeMessage message;

	public CfetsShortLiveResponseEvent(CfetsTradeMessage message) {
		super(message);
		this.message = message;
	}

	public CfetsTradeMessage getMessage() {
		return message;
	}

	public void setMessage(CfetsTradeMessage message) {
		this.message = message;
	}
}
