package com.xquant.platform.component.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.netty.MyCfetsLongLiveNettyServer;
import com.xquant.platform.component.mock.netty.MyXswapLongLiveNettyServer;
import com.xquant.platform.component.mock.persist.MessagePersistManagerDelegate;

/**
 * 负责对象的持久化以及进行消息的发送
 * 
 * @author Administrator
 */
public class MessagePersistAndSenderTask implements Runnable {

	private Logger logger = LoggerFactory.getLogger(MessagePersistAndSenderTask.class);

	@SuppressWarnings("rawtypes")
	private MockMessageInfo messageInfo;
	private MyCfetsLongLiveNettyServer cfetsLongLiveNettySever;
	private MessagePersistManagerDelegate messageServiceManagerDelegate;
	private MyXswapLongLiveNettyServer xswapLongLiveNettyServer;

	public MessagePersistAndSenderTask(@SuppressWarnings("rawtypes") MockMessageInfo messageInfo,
			MessagePersistManagerDelegate messageServiceManagerDelegate,
			MyCfetsLongLiveNettyServer cfetsLongLiveNettySever, MyXswapLongLiveNettyServer xswapLongLiveNettyServer) {
		super();
		this.messageInfo = messageInfo;
		this.cfetsLongLiveNettySever = cfetsLongLiveNettySever;
		this.messageServiceManagerDelegate = messageServiceManagerDelegate;
		this.xswapLongLiveNettyServer = xswapLongLiveNettyServer;
	}
	
	@SuppressWarnings("rawtypes")
	public MockMessageInfo getMessageInfo() {
		return messageInfo;
	}

	@Override
	public void run() {

		if (logger.isInfoEnabled()) {
			logger.info(" execute pesist and send task " + messageInfo);
		}
		messageServiceManagerDelegate.handle(messageInfo.getMessage());
		if (TargetSystemEnum.CFETS.equals(messageInfo.getTargetSys())) {
			cfetsLongLiveNettySever.sendPushMessage(messageInfo);
		} else if (TargetSystemEnum.XSWAP.equals(messageInfo.getTargetSys())) {
			xswapLongLiveNettyServer.sendPushMessage(messageInfo);
		}
	}

}
