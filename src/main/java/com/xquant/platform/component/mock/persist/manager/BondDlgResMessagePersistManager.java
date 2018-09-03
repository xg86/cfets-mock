package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.bonddlg.BondDlgResMessageService;

@Component
public class BondDlgResMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondDialogueQuoteResMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private BondDlgResMessageService bondDlgResMessageService;

	@Override
	public boolean handle(CfetsTradeBondDialogueQuoteResMessage message) {
		
		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if("2000-N-5-0".equals(key)) {
			return bondDlgResMessageService.addMessage(message);
		}else if("2000-R-5-0".equals(key)) {
			return bondDlgResMessageService.updateMessage(message);
		}else if("2000-C-5-0".equals(key)) {
			return bondDlgResMessageService.cancelMessage(message);
		}else if("2000-0-9-0".equals(key)) {
			return bondDlgResMessageService.expireMessage(message);
		}else if("2000-0-11-0".equals(key)) {
			return bondDlgResMessageService.confirmMessage(message);
		}else if("2000-0-12-0".equals(key)) {
			return bondDlgResMessageService.rejectMessage(message);
		}
		throw new RuntimeException(
				"当前的message对象找不到匹配的业务处理方式,对应的key = " + key );
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondDialogueQuoteResMessage.class);
	}

}
