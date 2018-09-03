package com.xquant.platform.component.mock;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeRegisterMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeRegisterPushAddressMessage;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.itf.cfets.transport.netty.MessageReceiveListener;
import com.xquant.platform.component.itf.cfets.transport.netty.NettyLongLiveClient;
import com.xquant.platform.component.itf.cfets.transport.netty.NettyLongLiveClientFactory;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.ResolverMessageInfo;
import com.xquant.platform.component.mock.holder.NettyLongLiveClientHolder;
import com.xquant.platform.component.mock.netty.MyCfetsChannelGroup;
import com.xquant.platform.component.mock.registry.SheduledTaskRegister;
import com.xquant.platform.component.mock.util.CfetsConstantUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.ResourceUtil4Cfets;

@Component
public class CfetsRecvMessageMockManager extends MessageMockManager implements SmartLifecycle {

	private static final int TRRIGER_TIME = 0;

	@Qualifier("cfetsNettyLongLiveClientFactory")
	@Autowired
	private NettyLongLiveClientFactory cfetsNettyLongLiveClientFactory;

	public void mock() {

		System.out.println("========CfetsRecvMessageMockManager start======================");

		if (!CfetsConstantUtil.useCenterServer()) {

			// 是否启动自动发送报价
			if (CfetsConstantUtil.counterMessageAutoSent()) {
				
				if (logger.isInfoEnabled()) {
					logger.info("====  use counter message auto send mode ====");
				}
				
				// 5分钟触发一次
				sheduledService.scheduleWithFixedDelay(new Runnable() {
					@Override
					public void run() {
						// 接收对手新增报价
						if (MyCfetsChannelGroup.getChannelCount() > 0) {
							addBondDlgRecvAddTask(TRRIGER_TIME);
							addPledgeBondRecvAddTask(TRRIGER_TIME + 1);
							addBondRfqRecvAddTask(TRRIGER_TIME + 2);
//							addBondResRecvAddTask(TRRIGER_TIME + 3);
						}
					}
				}, CfetsConstantUtil.getAutoSentInitDelay(), CfetsConstantUtil.getAutoSentDelay(), TimeUnit.MINUTES);
			}

		} else {
			
			if (logger.isInfoEnabled()) {
				logger.info("====  use center netty server to transform message ====");
			}
			
			// 创建了一个长连接用于接收报价
			NettyLongLiveClient nettyLongLiveClient = cfetsNettyLongLiveClientFactory.create("CFETS",
					CfetsConstantUtil.getCenterServerHost(), CfetsConstantUtil.getCenterServerPort(),
					new MessageReceiveListener<Object>() {

						@SuppressWarnings({ "rawtypes" })
						@Override
						public void receive(Object message) {

							// 注册消息不进行其余处理
							if (CfetsTradeRegisterPushAddressMessage.class.isInstance(message)) {
								return;
							}

							if (CfetsTradeRegisterMessage.class.isInstance(message)) {
								return;
							}

							// 此时进行推送的消息不需要进行重构 在中央控制台已经完成转换 直接发送机即可
							if (CfetsTradeMessage.class.isInstance(message)) {
								MockMessageInfo messageInfo = buildMessageInfoNoNeedBuild((CfetsTradeMessage) message);
								executeTask(messageInfo);
							}
						}
					});

			// 保存长连接客户端用于发送报文
			if (nettyLongLiveClient != null) {
				nettyLongLiveClient.connectAndFailRetry(true);
				NettyLongLiveClientHolder.setClient(nettyLongLiveClient);
				if (logger.isInfoEnabled()) {
					logger.info("创建与中央服务器的连接成功 ！！");
				}
			}

		}
	}

	/**
	 * 接收对手新增对话报价
	 */
	public void addBondDlgRecvAddTask(int triggerTime) {
		doAddTask(ResourceUtil4Cfets.getBondDlgRecvMessage4Add(), triggerTime);
	}

	/**
	 * 接收对手新增质押式回购对话报价
	 */
	public void addPledgeBondRecvAddTask(int triggerTime) {
		doAddTask(ResourceUtil4Cfets.getPledgeDlgRecvMessage4Add(), triggerTime);
	}

	/**
	 * 接收对手新增请求报价
	 */
	public void addBondRfqRecvAddTask(int triggerTime) {
		doAddTask(ResourceUtil4Cfets.getBondRfqRecvMessage4Add(), triggerTime);
	}

	/**
	 * @param messageTempldate
	 */
	@SuppressWarnings("rawtypes")
	private void doAddTask(CfetsTradeMessage messageTempldate, int time) {
		ResolverMessageInfo resolverMessageInfo = new ResolverMessageInfo(messageTempldate, null, null,
				TargetSystemEnum.CFETS);
		super.messageResolverManager.resolveOptypeAndFillTempIfNull(resolverMessageInfo);
		MockMessageInfo messageInfo = messageMessageInfoBuilderManager.build(messageTempldate, null,
				resolverMessageInfo.getOptype(), MockDateTimeUtil.timeAfterSecond(time));
		executeTask(messageInfo);
	}

	private boolean isRunning;

	@Override
	public void start() {
		isRunning = true;
		mock();
	}

	@Override
	public void stop() {
		isRunning = false;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		SheduledTaskRegister.removeAllTask();
	}

}
