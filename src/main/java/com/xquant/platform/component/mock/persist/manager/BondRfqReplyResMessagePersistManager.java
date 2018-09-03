package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessage;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.CfetsTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.bondres.BondRFQReplyResMessageService;

@Component
public class BondRfqReplyResMessagePersistManager
		implements IMessagePersistServiceManager<CfetsTradeBondRFQReplyResMessage> {

	@Autowired
	private CfetsTradeMessageKeyResolver CfetsTradeMessageKeyResolver;
	
	@Resource
	private BondRFQReplyResMessageService bondRFQReplyResMessageService;

	@Override
	public boolean handle(CfetsTradeBondRFQReplyResMessage message) {

		String key = CfetsTradeMessageKeyResolver.generateUniqueKey(message);
		if ("4001-N-5-0".equals(key)) {
			return bondRFQReplyResMessageService.addBySelf(message);
		} else if ("4001-R-5-0".equals(key)) {
			return bondRFQReplyResMessageService.updateBySelf(message);
		} else if ("4001-C-5-0".equals(key) || "4001-C-7-0".equals(key)) {
			return bondRFQReplyResMessageService.cancelBySelf(message);
		} else if ("4001-0-9-0".equals(key)) {
			return bondRFQReplyResMessageService.expiredBySelf(message);
		} else if ("4001-0-11-0".equals(key)) {
			return bondRFQReplyResMessageService.confirmByCounter(message);
		}
		throw new RuntimeException("当前的message对象找不到匹配的业务处理方式,对应的key = " + key);
	}

	@Override
	public boolean support(Class<?> cls) {
		return cls.equals(CfetsTradeBondRFQReplyResMessage.class);
	}

}
