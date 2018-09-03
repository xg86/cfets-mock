package com.xquant.platform.component.mock;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.mock.builder.MessageInfoBuilderManager4Quote;
import com.xquant.platform.component.mock.dto.CfetsMockMessageInfo;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.ResolverMessageInfo;
import com.xquant.platform.component.mock.netty.MyCfetsLongLiveNettyServer;
import com.xquant.platform.component.mock.netty.MyXswapLongLiveNettyServer;
import com.xquant.platform.component.mock.persist.MessagePersistManagerDelegate;
import com.xquant.platform.component.mock.registry.SheduledTaskRegister;
import com.xquant.platform.component.mock.resolver.MessageResolverManager;

@Component
public class MessageMockManager implements InitializingBean {

	protected Logger logger = LoggerFactory.getLogger(MessageMockManager.class);

	/**
	 * 用于消息的发送
	 */
	@Qualifier("cfetsLongLiveNettySever")
	@Autowired
	protected MyCfetsLongLiveNettyServer cfetsLongLiveNettySever;

	@Qualifier("xswapLongLiveNettySever")
	@Autowired
	protected MyXswapLongLiveNettyServer xswapLongLiveNettyServer;

	@Autowired
	protected MessageResolverManager messageResolverManager;

	@Autowired
	protected MessageInfoBuilderManager4Quote messageMessageInfoBuilderManager;

	/**
	 * 用于消息的保存
	 */
	@Autowired
	protected MessagePersistManagerDelegate messageServiceManagerDelegate;

	protected ScheduledExecutorService sheduledService = Executors
			.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

	protected ExecutorService threadService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	/**
	 * 进行消息的注册 开始定时任务 注册任务
	 * 
	 * @param messageInfo
	 */
	@SuppressWarnings("rawtypes")
	public void executeTask(MockMessageInfo messageInfo) {
		ScheduledFuture<?> future = doExecuteTask(messageInfo);
		if (StringUtils.isNotEmpty(messageInfo.getQuoteId())) {
			SheduledTaskRegister.registerTask(messageInfo.getQuoteId(), future);
		}
	}

	@SuppressWarnings("rawtypes")
	private ScheduledFuture<?> doExecuteTask(MockMessageInfo messageInfo) {
		// 生成消息处理任务
		MessagePersistAndSenderTask sheduledTask = new MessagePersistAndSenderTask(messageInfo,
				messageServiceManagerDelegate, cfetsLongLiveNettySever, xswapLongLiveNettyServer);
		if (logger.isInfoEnabled()) {
			logger.info(" schedule pesist and send task " + messageInfo);
		}
		// 进行消息任务调度
		return sheduledService.schedule(sheduledTask, messageInfo.getDelay(), messageInfo.getTimeUnit());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(messageServiceManagerDelegate);
		Assert.notNull(cfetsLongLiveNettySever);
	}

	/**
	 * 直接进行推送的消息不需要进行重构 构造一个messageInfo对象
	 * 
	 * @param message
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected MockMessageInfo buildMessageInfoNoNeedBuild(CfetsTradeMessage message) {
		CfetsTradeMessage messageTempldate = message;
		ResolverMessageInfo resolverMessageInfo = new ResolverMessageInfo();
		resolverMessageInfo.setMessageTempldate(messageTempldate);
		resolverMessageInfo.setTargetSys(TargetSystemEnum.CFETS);
		messageResolverManager.resolveOptypeAndFillTempIfNull(resolverMessageInfo);
		MockMessageInfo messageInfo = new CfetsMockMessageInfo();
		messageInfo.setDelay(0L);
		messageInfo.setTimeUnit(TimeUnit.SECONDS);
		messageInfo.setMessage(messageTempldate);
		messageInfo.setOptype(resolverMessageInfo.getOptype());
		messageInfo.setTargetSys(TargetSystemEnum.CFETS);
		try {
			Object body = MethodUtils.invokeMethod(messageTempldate, "getBody");
			String quoteId = (String) MethodUtils.invokeMethod(body, "getQuoteID");
			messageInfo.setQuoteId(quoteId);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return messageInfo;
	}
}
