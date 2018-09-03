/**
 * 
 */
package com.xquant.platform.component.mock.service.bondres.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyConfirmResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyConfirmResMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeRfqResMessageDao;
import com.xquant.platform.component.mock.service.bondres.BondRFQReplyConfirmResMessageService;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondres.impl
 * @author: guanglai.zhou
 * @date: 2018-07-23 20:03:35
 */
@Service
public class BondRFQReplyConfirmResMessageServiceImpl implements BondRFQReplyConfirmResMessageService {

	@Autowired
	private CommonCfetsTradeRfqResMessageDao commonCfetsTradeRfqResMessageDao;

	@Override
	public boolean confirmBySelf(CfetsTradeBondRFQReplyConfirmResMessage message) {
		CfetsTradeBondRFQReplyConfirmResMessageBody body = message.getBody();
		return commonCfetsTradeRfqResMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.FULL_DEALED.getValue(),
				body.getTransactTime(), null, SendOrRecvFlag.SEND) == 1;
	}

}
