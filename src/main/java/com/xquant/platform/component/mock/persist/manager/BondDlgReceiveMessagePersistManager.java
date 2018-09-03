package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.bonddlg.BondDlgReceiveMessageService;

@Component
public class BondDlgReceiveMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondDialogueQuoteReceiveMessage> {

	@Resource
	private BondDlgReceiveMessageService bondDlgReceiveMessageService;
	
	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;

	@Override
	public boolean handle(CfetsTradeBondDialogueQuoteReceiveMessage message) {

		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if ("2004-N-5-0".equals(key)) {
			return bondDlgReceiveMessageService.addMessage(message);
		} else if ("2004-0-9-0".equals(key)) {
			return bondDlgReceiveMessageService.expireMessage(message);
		} else if ("2004-R-5-0".equals(key)) {
			return bondDlgReceiveMessageService.updateMessage(message);
		} else if ("2004-C-7-0".equals(key)) {
			return bondDlgReceiveMessageService.cancelMessage(message);
		} else if ("2004-0-5-0".equals(key)) {
			return bondDlgReceiveMessageService.updateByCounterClient(message);
		}
		throw new RuntimeException("当前的message对象找不到匹配的业务处理方式,对应的key = " + key);
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondDialogueQuoteReceiveMessage.class);
	}

}
