/**
 * 
 */
package com.xquant.platform.component.mock.service.bondres.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.BondRfqResQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeRfqResMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQReplyResMessageBody;
import com.xquant.platform.component.mock.service.bondres.BondRFQReplyResMessageService;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondres.impl
 * @author: guanglai.zhou
 * @date: 2018-07-23 20:02:40
 */
@Service
public class BondRFQReplyResMessageServiceImpl implements BondRFQReplyResMessageService {

	@Autowired
	private BondRfqResQuoteResMessageDao bondRfqResQuoteResMessageDao;

	@Autowired
	private DatabaseQueryDao databaseQueryDao;

	@Autowired
	private CommonCfetsTradeRfqResMessageDao commonCfetsTradeRfqResMessageDao;

	@Override
	public boolean addBySelf(CfetsTradeBondRFQReplyResMessage message) {
		message.getBody().setQid(databaseQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		return bondRfqResQuoteResMessageDao.insert(buildMockMessageBody(message)) == 1;
	}

	@Override
	public boolean updateBySelf(CfetsTradeBondRFQReplyResMessage message) {
		message.getBody().setQid(databaseQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		if (commonCfetsTradeRfqResMessageDao.delete(message.getBody().getQuoteID()) == 1) {
			return bondRfqResQuoteResMessageDao.insert(buildMockMessageBody(message)) == 1;
		}
		return false;
	}

	@Override
	public boolean cancelBySelf(CfetsTradeBondRFQReplyResMessage message) {
		CfetsTradeBondRFQReplyResMessageBody body = message.getBody();
		return commonCfetsTradeRfqResMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.CANCELED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	@Override
	public boolean expiredBySelf(CfetsTradeBondRFQReplyResMessage message) {
		CfetsTradeBondRFQReplyResMessageBody body = message.getBody();
		return commonCfetsTradeRfqResMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.EXPIRED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	@Override
	public boolean confirmByCounter(CfetsTradeBondRFQReplyResMessage message) {
		CfetsTradeBondRFQReplyResMessageBody body = message.getBody();
		return commonCfetsTradeRfqResMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.FULL_DEALED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), SendOrRecvFlag.RECV) == 1;
	}

	private MockBondRFQReplyResMessageBody buildMockMessageBody(CfetsTradeBondRFQReplyResMessage message) {
		MockBondRFQReplyResMessageBody mockBody = new MockBondRFQReplyResMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setClientId(message.getHeader().getClientID());
		mockBody.setMyside(mockBody.getSide());
		mockBody.setSendOrRecv(SendOrRecvFlag.SEND);
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return mockBody;
	}

	@Override
	public List<MockBondRFQReplyResMessageBody> queryUnFinalValidQuote() {
		String formatTime = MockDatePatternUtil.DATE_TIME_PATTERN.format(new Date());
		int count = bondRfqResQuoteResMessageDao.countUnFinalQuoteAfterTime(formatTime);
		if (count > 0) {
			return bondRfqResQuoteResMessageDao.queryUnFinalQuoteAfterTime(formatTime);
		}
		return null;
	}

}
