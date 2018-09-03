package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.pledgebond.PledgeBondResMessageService;

@Component
public class PledgeBondResMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeCollateralDialogueQuoteResMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private PledgeBondResMessageService pledgeBondResMessageService;

	@Override
	public boolean handle(CfetsTradeCollateralDialogueQuoteResMessage message) {
		
		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if("2001-N-5-0".equals(key)) {
			return pledgeBondResMessageService.addMessage(message);
		}else if("2001-R-5-0".equals(key)) {
			return pledgeBondResMessageService.updateMessage(message);
		}else if("2001-C-5-0".equals(key)) {
			return pledgeBondResMessageService.cancelMessage(message);
		}else if("2001-0-9-0".equals(key)) {
			return pledgeBondResMessageService.expireMessage(message);
		}else if("2001-0-11-0".equals(key)) {
			return pledgeBondResMessageService.confirmMessage(message);
		}else if("2001-0-12-0".equals(key)) {
			return pledgeBondResMessageService.rejectMessage(message);
		}
		throw new RuntimeException(
				"当前的message对象找不到匹配的业务处理方式,对应的key = " + key );
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeCollateralDialogueQuoteResMessage.class);
	}

}
