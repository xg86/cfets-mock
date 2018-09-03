package com.xquant.platform.component.mock.netty.event;

import org.springframework.context.ApplicationEvent;

import com.xquant.platform.component.mock.dto.MockMessageInfo;

import io.netty.channel.Channel;

/**
 * 广播消息事件（本方发送报价异步返回成功）
 * @author Administrator
 *
 */
public class MockMessageBroadCastSuccEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1581924464115331206L;

	@SuppressWarnings("rawtypes")
	private MockMessageInfo messageInfo;
	
	private Channel channel;

	@SuppressWarnings("rawtypes")
	public MockMessageBroadCastSuccEvent(MockMessageInfo message,Channel channel) {
		super(message);
		this.messageInfo = message;
		this.channel = channel;
	}

	@SuppressWarnings("rawtypes")
	public MockMessageInfo getMessageInfo() {
		return messageInfo;
	}

	@SuppressWarnings("rawtypes")
	public void setMessageInfo(MockMessageInfo messageInfo) {
		this.messageInfo = messageInfo;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}  
}
