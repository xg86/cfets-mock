package com.xquant.platform.component.mock.service.clickdeal.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessage;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.BondPriceLimitQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dto.cfets.MockBondPriceLimitQuoteResMessageBody;
import com.xquant.platform.component.mock.service.clickdeal.BondPriceLimitMessageService;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

@Service
public class BondPriceLimitMessageServiceImpl implements BondPriceLimitMessageService {

	@Autowired
	private BondPriceLimitQuoteResMessageDao bondPriceLimitQuoteResMessageDao;

	@Autowired
	private DatabaseQueryDao sequenceQueryDao;

	@Override
	public boolean addMessage(CfetsTradeBondPriceLimitQuoteResMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		return bondPriceLimitQuoteResMessageDao.insert(buildMockMessageBody(message)) == 1;
	}

	@Override
	public boolean cancelMessage(CfetsTradeBondPriceLimitQuoteResMessage message) {
		return bondPriceLimitQuoteResMessageDao.updateStatus(message.getBody().getOrderID(),
				QuoteStatusEnum.CANCELED.getValue(), message.getBody().getTransactTime(),
				message.getBody().getQuoteTransType()) == 1;
	}

	@Override
	public boolean expireMessage(CfetsTradeBondPriceLimitQuoteResMessage message) {
		return bondPriceLimitQuoteResMessageDao.updateStatus(message.getBody().getOrderID(),
				QuoteStatusEnum.EXPIRED.getValue(), message.getBody().getTransactTime(),
				message.getBody().getQuoteTransType()) == 1;
	}

	private MockBondPriceLimitQuoteResMessageBody buildMockMessageBody(
			CfetsTradeBondPriceLimitQuoteResMessage message) {
		MockBondPriceLimitQuoteResMessageBody mockBody = new MockBondPriceLimitQuoteResMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setClientId(message.getHeader().getClientID());
		mockBody.setSendOrRecv(SendOrRecvFlag.SEND);
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return mockBody;
	}

	@Override
	public List<MockBondPriceLimitQuoteResMessageBody> queryUnFinalValidQuote() {
		String formatTime = MockDatePatternUtil.DATE_TIME_PATTERN.format(new Date());
		int count = bondPriceLimitQuoteResMessageDao.countUnFinalQuoteAfterTime(formatTime);
		if (count > 0) {
			return bondPriceLimitQuoteResMessageDao.queryUnFinalQuoteAfterTime(formatTime);
		}
		return null;
	}

}
