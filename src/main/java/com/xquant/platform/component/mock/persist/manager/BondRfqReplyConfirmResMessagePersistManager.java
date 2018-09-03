package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyConfirmResMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.bondres.BondRFQReplyConfirmResMessageService;

@Component
public class BondRfqReplyConfirmResMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondRFQReplyConfirmResMessage> {

	protected Logger logger = LoggerFactory.getLogger(BondRfqReplyConfirmResMessagePersistManager.class);
	
	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private BondRFQReplyConfirmResMessageService bondRFQReplyConfirmResMessageService;

	@Override
	public boolean handle(CfetsTradeBondRFQReplyConfirmResMessage message) {
		
		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if("4003-0-5-0".equals(key)) {
			return bondRFQReplyConfirmResMessageService.confirmBySelf(message);
		}
		
		throw new RuntimeException(
				"当前的message对象找不到匹配的业务处理方式,对应的key = " + key );
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondRFQReplyConfirmResMessage.class);
	}

}
