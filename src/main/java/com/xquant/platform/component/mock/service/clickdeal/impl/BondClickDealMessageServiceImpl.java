package com.xquant.platform.component.mock.service.clickdeal.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessage;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.BondClickDealQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dto.cfets.MockBondClickDealQuoteResMessageBody;
import com.xquant.platform.component.mock.service.clickdeal.BondClickDealMessageService;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

@Service
public class BondClickDealMessageServiceImpl implements BondClickDealMessageService {

	@Autowired
	private BondClickDealQuoteResMessageDao bondClickDealQuoteResMessageDao;

	@Autowired
	private DatabaseQueryDao sequenceQueryDao;

	@Override
	public boolean addMessage(CfetsTradeBondClickDealQuoteResMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		return bondClickDealQuoteResMessageDao.insert(buildMockMessageBody(message)) == 1;
	}

	@Override
	public boolean cancelMessage(CfetsTradeBondClickDealQuoteResMessage message) {
		return bondClickDealQuoteResMessageDao.updateStatus(message.getBody().getQuoteID(),
				QuoteStatusEnum.CANCELED.getValue(), message.getBody().getTransactTime(),
				message.getBody().getQuoteTransType()) == 1;
	}

	@Override
	public boolean expireMessage(CfetsTradeBondClickDealQuoteResMessage message) {
		return bondClickDealQuoteResMessageDao.updateStatus(message.getBody().getQuoteID(),
				QuoteStatusEnum.EXPIRED.getValue(), message.getBody().getTransactTime(),
				message.getBody().getQuoteTransType()) == 1;
	}

	@Override
	public List<MockBondClickDealQuoteResMessageBody> queryUnFinalValidQuote() {
		String formatTime = MockDatePatternUtil.DATE_TIME_PATTERN.format(new Date());
		int count = bondClickDealQuoteResMessageDao.countUnFinalQuoteAfterTime(formatTime);
		if (count > 0) {
			return bondClickDealQuoteResMessageDao.queryUnFinalQuoteAfterTime(formatTime);
		}
		return null;
	}

	private MockBondClickDealQuoteResMessageBody buildMockMessageBody(CfetsTradeBondClickDealQuoteResMessage message) {
		MockBondClickDealQuoteResMessageBody mockBody = new MockBondClickDealQuoteResMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setClientId(message.getHeader().getClientID());
		mockBody.setSendOrRecv(SendOrRecvFlag.SEND);
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return mockBody;
	}

}
