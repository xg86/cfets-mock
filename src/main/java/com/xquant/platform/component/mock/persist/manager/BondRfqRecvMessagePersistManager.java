package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.bondrfq.BondRFQReceiveMessageService;

@Component
public class BondRfqRecvMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondRFQReceiveMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private BondRFQReceiveMessageService bondRFQReceiveMessageService;

	@Override
	public boolean handle(CfetsTradeBondRFQReceiveMessage message) {
		
		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if("4004-0-5-0".equals(key)) {
			return bondRFQReceiveMessageService.addMessageByCounter(message);
		}else if("4004-0-7-0".equals(key)) {
			return bondRFQReceiveMessageService.cancelMessageByCounter(message);
		}else if("4004-0-9-0".equals(key)) {
			return bondRFQReceiveMessageService.expiredMessageByCounter(message);
		}
		throw new RuntimeException(
				"当前的message对象找不到匹配的业务处理方式,对应的key = " + key );
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondRFQReceiveMessage.class);
	}

}
