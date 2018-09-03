package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.pledgebond.PledgeBondReceiveMessageService;

@Component
public class PledgeBondReceiveMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeCollateralDialogueQuoteReceiveMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private PledgeBondReceiveMessageService pledgeBondReceiveMessageService;

	@Override
	public boolean handle(CfetsTradeCollateralDialogueQuoteReceiveMessage message) {

		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if ("2005-N-5-0".equals(key)) {
			return pledgeBondReceiveMessageService.addMessage(message);
		} else if ("2005-0-9-0".equals(key)) {
			return pledgeBondReceiveMessageService.expireMessage(message);
		} else if ("2005-R-5-0".equals(key)) {
			return pledgeBondReceiveMessageService.updateMessage(message);
		} else if ("2005-C-7-0".equals(key)) {
			return pledgeBondReceiveMessageService.cancelMessage(message);
		} else if ("2005-0-5-0".equals(key)) {
			return pledgeBondReceiveMessageService.updateByCounterClient(message);
		}
		throw new RuntimeException("当前的message对象找不到匹配的业务处理方式,对应的key = " + key);
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeCollateralDialogueQuoteReceiveMessage.class);
	}

}
