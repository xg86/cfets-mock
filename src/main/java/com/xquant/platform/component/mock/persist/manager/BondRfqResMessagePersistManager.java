package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.bondrfq.BondRFQResMessageService;

@Component
public class BondRfqResMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondRFQResMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private BondRFQResMessageService bondRFQResMessageService;

	@Override
	public boolean handle(CfetsTradeBondRFQResMessage message) {
		
		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if("4000-N-5-0".equals(key)) {
			return bondRFQResMessageService.addMessageBySelf(message);
		}else if("4000-C-5-0".equals(key)) {
			return bondRFQResMessageService.cancelMessageBySelf(message);
		}else if("4000-0-9-0".equals(key)) {
			return bondRFQResMessageService.expireMessageBySelf(message);
		}
		throw new RuntimeException(
				"当前的message对象找不到匹配的业务处理方式,对应的key = " + key );
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondRFQResMessage.class);
	}

}
