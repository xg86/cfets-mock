package com.xquant.platform.component.mock.service.bondrfq.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.common.CfetsTradeCounterParty;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.BondRfqReqQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeRfqMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dto.MyCfetsTradeCounterParty;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQResMessageBody;
import com.xquant.platform.component.mock.service.bondrfq.BondRFQResMessageService;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondrfq.impl
 * @author: guanglai.zhou
 * @date: 2018-07-23 17:19:50
 */
@Service
public class BondRFQResMessageServiceImpl implements BondRFQResMessageService {

	@Autowired
	private BondRfqReqQuoteResMessageDao bondRfqReqQuoteResMessageDao;

	@Autowired
	private DatabaseQueryDao databaseQueryDao;

	@Autowired
	private CommonCfetsTradeRfqMessageDao CommonCfetsTradeRfqMessageDao;

	@Override
	public boolean addMessageBySelf(CfetsTradeBondRFQResMessage message) {
		message.getBody().setQid(databaseQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		if (bondRfqReqQuoteResMessageDao.insert(buildMockMessageBody(message)) == 1) {

			List<CfetsTradeCounterParty> counterPartyList = message.getBody().getCounterPartyList()
					.getCounterPartyList();
			MyCfetsTradeCounterParty myCfetsTradeCounterParty = new MyCfetsTradeCounterParty();
			myCfetsTradeCounterParty.setClientId(message.getHeader().getClientID());
			myCfetsTradeCounterParty.setClordid(message.getBody().getClOrdID());
			myCfetsTradeCounterParty.setClordidClientId(message.getBody().getClOrdIDClientID());
			myCfetsTradeCounterParty.setQid(message.getBody().getQid());
			myCfetsTradeCounterParty.setQuoteid(message.getBody().getQuoteID());
			for (CfetsTradeCounterParty cfetsTradeCounterParty : counterPartyList) {
				myCfetsTradeCounterParty.setCounterPartyID(cfetsTradeCounterParty.getCounterPartyID());
				myCfetsTradeCounterParty.setCounterPartyName(cfetsTradeCounterParty.getCounterPartyName());
				bondRfqReqQuoteResMessageDao.insertParties(myCfetsTradeCounterParty);
			}
		}
		return false;
	}

	@Override
	public boolean cancelMessageBySelf(CfetsTradeBondRFQResMessage message) {
		CfetsTradeBondRFQResMessageBody body = message.getBody();
		return CommonCfetsTradeRfqMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.CANCELED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	@Override
	public boolean expireMessageBySelf(CfetsTradeBondRFQResMessage message) {
		CfetsTradeBondRFQResMessageBody body = message.getBody();
		return CommonCfetsTradeRfqMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.EXPIRED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	@Override
	public boolean allConfirmedMessage(CfetsTradeBondRFQResMessage message) {
		CfetsTradeBondRFQResMessageBody body = message.getBody();
		return CommonCfetsTradeRfqMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.FULL_DEALED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	@Override
	public boolean allRejectedMessage(CfetsTradeBondRFQResMessage message) {
		CfetsTradeBondRFQResMessageBody body = message.getBody();
		return CommonCfetsTradeRfqMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.REJECTED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	@Override
	public List<MockBondRFQResMessageBody> queryUnFinalValidQuote() {
		String formatTime = MockDatePatternUtil.DATE_TIME_PATTERN.format(new Date());
		int count = bondRfqReqQuoteResMessageDao.countUnFinalQuoteAfterTime(formatTime);
		if (count > 0) {
			return bondRfqReqQuoteResMessageDao.queryUnFinalQuoteAfterTime(formatTime);
		}
		return null;
	}

	private MockBondRFQResMessageBody buildMockMessageBody(CfetsTradeBondRFQResMessage message) {
		MockBondRFQResMessageBody mockBody = new MockBondRFQResMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setClientId(message.getHeader().getClientID());
		mockBody.setMyside(mockBody.getSide());
		mockBody.setSendOrRecv(SendOrRecvFlag.SEND);
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return mockBody;
	}

}
