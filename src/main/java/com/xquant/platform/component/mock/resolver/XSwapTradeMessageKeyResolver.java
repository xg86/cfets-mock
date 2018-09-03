package com.xquant.platform.component.mock.resolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.mock.util.ResourceUtil4Xswap;

import xquant.xswap.protocol.XSwapMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-07-20 16:35:00 根据不同报价类型获取一个唯一的key值
 */
@Component
public class XSwapTradeMessageKeyResolver implements IMessageResolver<XSwapMessage> {

	/**
	 * 保存根据quoteTranstype和status生成的key以及对应的报价类型的map
	 */
	private static final Map<String, OpTypeEnum> TRAN_STATU_OPTYPE_MAP = new ConcurrentHashMap<String, OpTypeEnum>();
	/**
	 * 保存根据quoteTranstype和status生成的key以及对应的报价对象消息模板
	 */
	private static final Map<String, XSwapMessage> TRAN_STATU_MESSAGE_MAP = new ConcurrentHashMap<String, XSwapMessage>();
	
	private static final XswapKeyGeneratorStrategy xswapKeyGeneratorStrategy = new XswapKeyGeneratorStrategy();

	static {

		TRAN_STATU_OPTYPE_MAP.put("1202", OpTypeEnum.XSWAP_ADD);
		TRAN_STATU_MESSAGE_MAP.put("1202", ResourceUtil4Xswap.getXSwapMessage4AddResPush());
		TRAN_STATU_OPTYPE_MAP.put("1203", OpTypeEnum.XSWAP_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("1203", ResourceUtil4Xswap.getXSwapMessage4CancelResPush());

	}

	@Override
	public String generateUniqueKey(XSwapMessage message) {
		return xswapKeyGeneratorStrategy.generateKey(message);
	}

	@Override
	public OpTypeEnum getOptypeWithKey(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return TRAN_STATU_OPTYPE_MAP.get(key);
	}

	@Override
	public XSwapMessage getMessageWithKey(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return TRAN_STATU_MESSAGE_MAP.get(key);
	}

	@Override
	public TargetSystemEnum supportSys() {
		return TargetSystemEnum.XSWAP;
	}
}
