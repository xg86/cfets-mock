package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyResMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.pledgebond.PledgeBondReplyMessageService;

@Component
public class PledgeBondReplyPersistManager implements IMessagePersistServiceManager<CfetsTradeCollateralDialogueQuoteReplyResMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private PledgeBondReplyMessageService pledgeBondReplyMessageService;

	@Override
	public boolean handle(CfetsTradeCollateralDialogueQuoteReplyResMessage message) {
		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if ("2003-0-5-1".equals(key)) {
			return pledgeBondReplyMessageService.confirmMessage(message);
		} else if ("2003-0-5-7".equals(key)) {
			return pledgeBondReplyMessageService.rejectMessage(message);
		}
		throw new RuntimeException("当前的message对象找不到匹配的业务处理方式,对应的key = " + key);
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeCollateralDialogueQuoteReplyResMessage.class);
	}

}
