/**
 * 
 */
package com.xquant.platform.component.mock.service.push.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.cfets.trade.protocol.util.CfetsTradeSerializeUtil;
import com.xquant.platform.component.mock.dao.CfetsTradePushMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dao.XswapTradePushMessageDao;
import com.xquant.platform.component.mock.dto.CfetsTradePushMessage;
import com.xquant.platform.component.mock.dto.XswapTradePushMessage;
import com.xquant.platform.component.mock.service.push.TradeMessagePushService;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;

import xquant.xswap.protocol.XSwapMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.push.impl
 * @author: guanglai.zhou
 * @date: 2018-08-17 17:32:35
 */
@Service
public class TradeMessagePushServiceImpl implements TradeMessagePushService {

	@Autowired
	private CfetsTradePushMessageDao cfetsTradePushMessageDao;

	@Autowired
	private XswapTradePushMessageDao xswapTradePushMessageDao;

	@Autowired
	private DatabaseQueryDao databaseQueryDao;

	@Override
	public boolean addMessage(Object message) {
		if (CfetsTradeMessage.class.isInstance(message)) {
			CfetsTradeMessage pushMessage = (CfetsTradeMessage) message;
			Long pid = databaseQueryDao.getNextCfetsTradePid();
			CfetsTradePushMessage cfetsTradePushMessage = new CfetsTradePushMessage();
			cfetsTradePushMessage.setPid(String.valueOf(pid));
			cfetsTradePushMessage.setAction(pushMessage.getHeader().getAction());
			cfetsTradePushMessage.setClientId(pushMessage.getHeader().getClientID());
			cfetsTradePushMessage.setMessage(CfetsTradeSerializeUtil.toXml(pushMessage));
			cfetsTradePushMessage.setUpdatetime(MockDatePatternUtil.DATE_TIME_PATTERN.format(new Date()));
			return cfetsTradePushMessageDao.insert(cfetsTradePushMessage) == 1;

		} else if (XSwapMessage.class.isInstance(message)) {
			XSwapMessage pushMessage = (XSwapMessage) message;
			Long pid = databaseQueryDao.getNextXswapTradePid();
			XswapTradePushMessage xswapTradePushMessage = new XswapTradePushMessage();
			xswapTradePushMessage.setPid(String.valueOf(pid));
			xswapTradePushMessage.setAction(pushMessage.getHeader().getAction());
			xswapTradePushMessage.setClientId(pushMessage.getHeader().getClientID());
			xswapTradePushMessage.setMessage(CfetsTradeSerializeUtil.toXml(pushMessage));
			xswapTradePushMessage.setUpdatetime(MockDatePatternUtil.DATE_TIME_PATTERN.format(new Date()));
			return xswapTradePushMessageDao.insert(xswapTradePushMessage) == 1;
		}
		return false;
	}

}
