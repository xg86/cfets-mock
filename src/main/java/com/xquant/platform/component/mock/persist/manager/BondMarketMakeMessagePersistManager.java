package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.clickdeal.BondMarketMakeMessageService;

@Component
public class BondMarketMakeMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondMarketMakingQuoteResMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private BondMarketMakeMessageService bondMarketMakeMessageService;

	@Override
	public boolean handle(CfetsTradeBondMarketMakingQuoteResMessage message) {
		
		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if("3002-N-5-0".equals(key)) {
			return bondMarketMakeMessageService.addMessage(message);
		}else if("3002-C-5-0".equals(key)) {
			return bondMarketMakeMessageService.cancelMessage(message);
		}else if("3002-0-9-0".equals(key)) {
			return bondMarketMakeMessageService.expireMessage(message);
		}
		throw new RuntimeException(
				"当前的message对象找不到匹配的业务处理方式,对应的key = " + key );
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondMarketMakingQuoteResMessage.class);
	}

}
