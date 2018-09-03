package com.xquant.platform.component.mock.resolver;

import org.apache.commons.lang3.StringUtils;

import xquant.xswap.protocol.XSwapMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.resolver
 * @author: guanglai.zhou
 * @date: 2018-07-24 15:52:22
 */
public class XswapKeyGeneratorStrategy implements IMessageKeyGeneratorStrategy<XSwapMessage> {

	@Override
	public String generateKey(XSwapMessage message) {
		String action = message.getHeader().getAction();
		if (StringUtils.isBlank(action)) {
			throw new RuntimeException("该报文头信息不存在action?");
		}
		return action;
	}

}
