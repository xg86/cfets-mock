package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.clickdeal.BondClickDealMessageService;

@Component
public class BondClickDealMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondClickDealQuoteResMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private BondClickDealMessageService bondClickDealMessageService;

	@Override
	public boolean handle(CfetsTradeBondClickDealQuoteResMessage message) {
		
		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if("3001-N-5-0".equals(key)) {
			return bondClickDealMessageService.addMessage(message);
		}else if("3001-C-5-0".equals(key)) {
			return bondClickDealMessageService.cancelMessage(message);
		}else if("3001-0-9-0".equals(key)) {
			return bondClickDealMessageService.expireMessage(message);
		}
		throw new RuntimeException(
				"当前的message对象找不到匹配的业务处理方式,对应的key = " + key );
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondClickDealQuoteResMessage.class);
	}

}
