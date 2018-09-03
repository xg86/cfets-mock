package com.xquant.platform.component.mock.resolver;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.util.Assert;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.cfets.trade.protocol.util.CfetsTradeSerializeUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.resolver
 * @author: guanglai.zhou
 * @date: 2018-07-24 15:52:22
 */
public class CfetsKeyGeneratorStrategy implements IMessageKeyGeneratorStrategy<CfetsTradeMessage> {

	@Override
	public String generateKey(CfetsTradeMessage message) {
		if (message.getHeader()==null) {
			throw new RuntimeException("该报文头信息不存在header?" + CfetsTradeSerializeUtil.toXml(message));
		}
		String action = message.getHeader().getAction();
		String quoteTransType = null;
		String status = null;
		String resType = null;
		if (StringUtils.isBlank(action)) {
			throw new RuntimeException("该报文头信息不存在action?" + CfetsTradeSerializeUtil.toXml(message));
		}
		try {
			Object body = MethodUtils.invokeMethod(message, "getBody");
			quoteTransType = MyRefelectionUtil.getFiledValue(body, "quoteTransType");
			status = MyRefelectionUtil.getFiledValue(body, "status");
			resType = MyRefelectionUtil.getFiledValue(body, "resType");
			FieldUtils.getAllFields(body.getClass());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Assert.notNull(quoteTransType, "quoteTransType can not be null");
		Assert.notNull(status, "status can not be null");
		Assert.notNull(resType, "resType can not be null");
		return action + "-" + quoteTransType + "-" + status + "-" + resType;
	}

}
