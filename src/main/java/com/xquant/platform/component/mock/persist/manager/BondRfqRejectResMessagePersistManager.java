package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.bondrfq.BondRFQRejectResMessageService;

@Component
public class BondRfqRejectResMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondRFQRejectResMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private BondRFQRejectResMessageService bondRFQRejectResMessageService;

	@Override
	public boolean handle(CfetsTradeBondRFQRejectResMessage message) {
		
		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if("4002-0-5-0".equals(key)) {
			return bondRFQRejectResMessageService.rejectMessageBySelf(message);
		}
		throw new RuntimeException(
				"当前的message对象找不到匹配的业务处理方式,对应的key = " + key );
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondRFQRejectResMessage.class);
	}

}
