package com.xquant.platform.component.mock.service.bondrfq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeRfqMessageDao;
import com.xquant.platform.component.mock.service.bondrfq.BondRFQRejectResMessageService;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondrfq.impl
 * @author: guanglai.zhou
 * @date: 2018-07-23 17:32:33
 */
@Service
public class BondRFQRejectResMessageServiceImpl implements BondRFQRejectResMessageService {

	@Autowired
	private CommonCfetsTradeRfqMessageDao CommonCfetsTradeRfqMessageDao;

	@Override
	public boolean rejectMessageBySelf(CfetsTradeBondRFQRejectResMessage message) {

		CfetsTradeBondRFQRejectResMessageBody body = message.getBody();
		return CommonCfetsTradeRfqMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.REJECTED.getValue(),
				body.getTransactTime(), null, SendOrRecvFlag.SEND) == 1;
	}

}
