package com.xquant.platform.component.mock.service.bondrfq.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.BondRfqReqQuoteRecvMessageDao;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeRfqMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQReceiveMessageBody;
import com.xquant.platform.component.mock.service.bondrfq.BondRFQReceiveMessageService;
import com.xquant.platform.component.mock.util.CfetsTradeSideUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondrfq.impl
 * @author: guanglai.zhou
 * @date: 2018-07-23 17:35:20
 */
@Service
public class BondRFQReceiveMessageServiceImpl implements BondRFQReceiveMessageService {

	@Autowired
	private BondRfqReqQuoteRecvMessageDao bondRfqReqQuoteRecvMessageDao;

	@Autowired
	private DatabaseQueryDao databaseQueryDao;

	@Autowired
	private CommonCfetsTradeRfqMessageDao CommonCfetsTradeRfqMessageDao;

	@Override
	public boolean addMessageByCounter(CfetsTradeBondRFQReceiveMessage message) {
		message.getBody().setQid(databaseQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		return bondRfqReqQuoteRecvMessageDao.insert(buildMockMessageBody(message)) == 1;
	}

	@Override
	public boolean cancelMessageByCounter(CfetsTradeBondRFQReceiveMessage message) {
		CfetsTradeBondRFQReceiveMessageBody body = message.getBody();
		return CommonCfetsTradeRfqMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.CANCELED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	@Override
	public boolean expiredMessageByCounter(CfetsTradeBondRFQReceiveMessage message) {
		CfetsTradeBondRFQReceiveMessageBody body = message.getBody();
		return CommonCfetsTradeRfqMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.EXPIRED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	@Override
	public boolean allConfirmedMessage(CfetsTradeBondRFQReceiveMessage message) {
		CfetsTradeBondRFQReceiveMessageBody body = message.getBody();
		return CommonCfetsTradeRfqMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.FULL_DEALED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	@Override
	public boolean allRejectedMessage(CfetsTradeBondRFQReceiveMessage message) {
		CfetsTradeBondRFQReceiveMessageBody body = message.getBody();
		return CommonCfetsTradeRfqMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.REJECTED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	private MockBondRFQReceiveMessageBody buildMockMessageBody(CfetsTradeBondRFQReceiveMessage message) {
		MockBondRFQReceiveMessageBody mockBody = new MockBondRFQReceiveMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setMyside(CfetsTradeSideUtil.changeSide(mockBody.getSide()));
		mockBody.setSendOrRecv(SendOrRecvFlag.RECV);
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return mockBody;
	}

}
