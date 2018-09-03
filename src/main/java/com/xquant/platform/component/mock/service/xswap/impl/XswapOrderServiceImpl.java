package com.xquant.platform.component.mock.service.xswap.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.platform.component.itf.cfets.xswap.api.enums.XSwapQuoteStatusEnum;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dao.XSwapOrderResMessageDao;
import com.xquant.platform.component.mock.service.xswap.XswapOrderService;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

import xquant.xswap.protocol.XSwapOrderActionResMessage;
import xquant.xswap.protocol.XSwapOrderResMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.xswap
 * @author: guanglai.zhou
 * @date: 2018-07-24 17:00:14
 */
@Service
public class XswapOrderServiceImpl implements XswapOrderService {

	@Autowired
	private XSwapOrderResMessageDao xSwapOrderResMessageDao;

	@Autowired
	private DatabaseQueryDao sequenceQueryDao;

	@Override
	public boolean addMessage(XSwapOrderResMessage message) {
		message.getBody().setSysReqID(sequenceQueryDao.getNextCfetsTradeQid().toString());
		Set<String> ignoreFields = new HashSet<String>();
		ignoreFields.add("xSwapReqID");
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody(), ignoreFields);
		if (xSwapOrderResMessageDao.insert(message.getBody()) == 1) {
			return xSwapOrderResMessageDao.updateStatus(message.getHeader().getClientID(),
					message.getBody().getFb_OrderID(), XSwapQuoteStatusEnum.CONFIRMED.getValue(),
					message.getBody().getFb_TransactTime()) == 1;
		}
		return false;
	}

	@Override
	public boolean cancelMessage(XSwapOrderActionResMessage message) {
		return xSwapOrderResMessageDao.updateStatus(null, message.getBody().getOrderID(),
				XSwapQuoteStatusEnum.CANCELED.getValue(), MockDateTimeUtil.getTransactimeOfNow()) == 1;
	}

}
