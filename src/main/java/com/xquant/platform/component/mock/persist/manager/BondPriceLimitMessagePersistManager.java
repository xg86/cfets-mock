package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.clickdeal.BondPriceLimitMessageService;

@Component
public class BondPriceLimitMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondPriceLimitQuoteResMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private BondPriceLimitMessageService bondPriceLimitMessageService;

	@Override
	public boolean handle(CfetsTradeBondPriceLimitQuoteResMessage message) {
		
		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if("3000-N-5-0".equals(key)) {
			return bondPriceLimitMessageService.addMessage(message);
		}else if("3000-C-5-0".equals(key)) {
			return bondPriceLimitMessageService.cancelMessage(message);
		}else if("3000-0-9-0".equals(key)) {
			return bondPriceLimitMessageService.expireMessage(message);
		}
		throw new RuntimeException(
				"当前的message对象找不到匹配的业务处理方式,对应的key = " + key );
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondPriceLimitQuoteResMessage.class);
	}

}
