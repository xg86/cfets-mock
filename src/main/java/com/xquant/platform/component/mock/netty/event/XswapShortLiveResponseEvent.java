package com.xquant.platform.component.mock.netty.event;

import org.springframework.context.ApplicationEvent;

import xquant.xswap.protocol.XSwapMessage;

/**
 * 同步返回事件
 * 
 * @author Administrator
 *
 */
public class XswapShortLiveResponseEvent extends ApplicationEvent {

	private static final long serialVersionUID = 5512438275505873982L;

	private XSwapMessage message;

	public XswapShortLiveResponseEvent(XSwapMessage message) {
		super(message);
		this.message = message;
	}

	public XSwapMessage getMessage() {
		return message;
	}

	public void setMessage(XSwapMessage message) {
		this.message = message;
	}
}
