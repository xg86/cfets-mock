package com.xquant.platform.component.mock.persist.manager;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.resolver.XSwapTradeMessageKeyResolver;
import com.xquant.platform.component.mock.service.xswap.XswapOrderService;

import xquant.xswap.protocol.XSwapMessage;
import xquant.xswap.protocol.XSwapOrderActionResMessage;
import xquant.xswap.protocol.XSwapOrderResMessage;

@Component
public class XSwapMessagePersistManager
		implements IMessagePersistServiceManager<XSwapMessage> {

	@Autowired
	private XSwapTradeMessageKeyResolver xSwapTradeMessageKeyResolver;
	
	@Resource
	private XswapOrderService xswapOrderService;

	@Override
	public boolean handle(XSwapMessage message) {
		
		String key = xSwapTradeMessageKeyResolver.generateUniqueKey(message);
		if("1202".equals(key)) {
			return xswapOrderService.addMessage((XSwapOrderResMessage)message);
		}else if("1203".equals(key)) {
			return xswapOrderService.cancelMessage((XSwapOrderActionResMessage)message);
		}
		throw new RuntimeException(
				"当前的message对象找不到匹配的业务处理方式,对应的key = " + key );
	}

	@Override
	public boolean support(Class<?> cls) {
		return XSwapMessage.class.isAssignableFrom(cls);
	}

}
