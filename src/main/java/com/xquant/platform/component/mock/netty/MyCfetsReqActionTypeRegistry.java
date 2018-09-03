package com.xquant.platform.component.mock.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReplyMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyConfirmMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMarketDataSubscribeMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeRegisterPushAddressMessage;

/**
 * 用于保存CfetsMessage中的action与类型的映射关系(由于在server端 只接收客户端的req消息 因此只针对请求报文类)
 * 
 * @author Administrator
 * 
 */
public class MyCfetsReqActionTypeRegistry {

	private static final Map<Integer, Class<?>> REQ_MAP = new ConcurrentHashMap<Integer, Class<?>>();

	static {
		REQ_MAP.put(2000, CfetsTradeBondDialogueQuoteMessage.class);
		REQ_MAP.put(2002, CfetsTradeBondDialogueQuoteReplyMessage.class);
		REQ_MAP.put(2001, CfetsTradeCollateralDialogueQuoteMessage.class);
		REQ_MAP.put(2003, CfetsTradeCollateralDialogueQuoteReplyMessage.class);
		REQ_MAP.put(4000, CfetsTradeBondRFQMessage.class);
		REQ_MAP.put(4002, CfetsTradeBondRFQRejectMessage.class);
		REQ_MAP.put(4001, CfetsTradeBondRFQReplyMessage.class);
		REQ_MAP.put(4003, CfetsTradeBondRFQReplyConfirmMessage.class);
		REQ_MAP.put(3002, CfetsTradeBondMarketMakingQuoteMessage.class);
		REQ_MAP.put(3000, CfetsTradeBondPriceLimitQuoteMessage.class);
		REQ_MAP.put(3001, CfetsTradeBondClickDealQuoteMessage.class);
		REQ_MAP.put(8003, CfetsTradeRegisterPushAddressMessage.class);
		REQ_MAP.put(5000, CfetsTradeMarketDataSubscribeMessage.class);
		REQ_MAP.put(8000, CfetsTradeMessage.class);
	}

	public static Class<?> getTypeViaAction(int action) {
		return REQ_MAP.get(action);
	}
}
