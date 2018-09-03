package com.xquant.platform.component.mock.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xquant.xswap.protocol.XSwapMDQuoteReqMessage;
import xquant.xswap.protocol.XSwapOrderActionReqMessage;
import xquant.xswap.protocol.XSwapOrderReqMessage;
import xquant.xswap.protocol.XSwapRegisterMessage;
import xquant.xswap.protocol.XSwapRiskLimitQueryReqMessage;
import xquant.xswap.protocol.XSwapRiskLimitSetReqMessage;
import xquant.xswap.protocol.XSwapRiskRatioQueryReqMessage;
import xquant.xswap.protocol.XSwapRiskRatioSetReqMessage;

/**
 * 用于保存CfetsMessage中的action与类型的映射关系(由于在server端 只接收客户端的req消息 因此只针对请求报文类)
 * 
 * @author Administrator
 * 
 */
public class MyXswapReqActionTypeRegistry {

	private static final Map<Integer, Class<?>> REQ_MAP = new ConcurrentHashMap<Integer, Class<?>>();

	static {
		// 新增报价
		REQ_MAP.put(1202, XSwapOrderReqMessage.class);
		// 撤销报价
		REQ_MAP.put(1203, XSwapOrderActionReqMessage.class);
		// 注册消息
		REQ_MAP.put(1500, XSwapRegisterMessage.class);
		// 市场行情订阅
		REQ_MAP.put(1301, XSwapMDQuoteReqMessage.class);	
		// 授信设置
		REQ_MAP.put(1101, XSwapRiskLimitSetReqMessage.class);
		// 授信查询
		REQ_MAP.put(1102, XSwapRiskLimitQueryReqMessage.class);	
		// 风险系数设置
		REQ_MAP.put(1103, XSwapRiskRatioSetReqMessage.class);	
		// 风险系数查询
		REQ_MAP.put(1104, XSwapRiskRatioQueryReqMessage.class);		
	}

	public static Class<?> getTypeViaAction(int action) {
		return REQ_MAP.get(action);
	}
}
