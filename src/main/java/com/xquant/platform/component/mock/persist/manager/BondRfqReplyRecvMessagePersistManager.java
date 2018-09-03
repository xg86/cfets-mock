package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.bondres.BondRFQReplyReceiveMessageService;

@Component
public class BondRfqReplyRecvMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondRFQReplyReceiveMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private BondRFQReplyReceiveMessageService bondRFQReplyReceiveMessageService;

	@Override
	public boolean handle(CfetsTradeBondRFQReplyReceiveMessage message) {

		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if ("4005-N-5-0".equals(key)) {
			return bondRFQReplyReceiveMessageService.addByCounter(message);
		} else if ("4005-R-5-0".equals(key)) {
			return bondRFQReplyReceiveMessageService.updateByCounter(message);
		} else if ("4005-0-7-0".equals(key) || "4005-C-7-0".equals(key)) {
			return bondRFQReplyReceiveMessageService.cancelByCounter(message);
		} else if ("4005-0-9-0".equals(key)) {
			return bondRFQReplyReceiveMessageService.expiredByCounter(message);
		}
		throw new RuntimeException("当前的message对象找不到匹配的业务处理方式,对应的key = " + key);
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondRFQReplyReceiveMessage.class);
	}

}
