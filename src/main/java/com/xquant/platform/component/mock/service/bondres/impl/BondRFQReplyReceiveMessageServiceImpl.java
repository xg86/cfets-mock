package com.xquant.platform.component.mock.service.bondres.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.BondRfqResQuoteRecvMessageDao;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeRfqResMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQReplyReceiveMessageBody;
import com.xquant.platform.component.mock.service.bondres.BondRFQReplyReceiveMessageService;
import com.xquant.platform.component.mock.util.CfetsTradeSideUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondres.impl
 * @author: guanglai.zhou
 * @date: 2018-07-23 20:03:07
 */
@Service
public class BondRFQReplyReceiveMessageServiceImpl implements BondRFQReplyReceiveMessageService {

	@Autowired
	private BondRfqResQuoteRecvMessageDao bondRfqResQuoteRecvMessageDao;

	@Autowired
	private DatabaseQueryDao sequenceQueryDao;

	@Autowired
	private CommonCfetsTradeRfqResMessageDao commonCfetsTradeRfqResMessageDao;

	@Override
	public boolean addByCounter(CfetsTradeBondRFQReplyReceiveMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		return bondRfqResQuoteRecvMessageDao.insert(buildMockMessageBody(message)) == 1;
	}

	@Override
	public boolean updateByCounter(CfetsTradeBondRFQReplyReceiveMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		if (commonCfetsTradeRfqResMessageDao.delete(message.getBody().getQuoteID()) == 1) {
			return bondRfqResQuoteRecvMessageDao.insert(buildMockMessageBody(message)) == 1;
		}
		return false;
	}

	@Override
	public boolean cancelByCounter(CfetsTradeBondRFQReplyReceiveMessage message) {
		CfetsTradeBondRFQReplyReceiveMessageBody body = message.getBody();
		return commonCfetsTradeRfqResMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.CANCELED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	@Override
	public boolean expiredByCounter(CfetsTradeBondRFQReplyReceiveMessage message) {
		CfetsTradeBondRFQReplyReceiveMessageBody body = message.getBody();
		return commonCfetsTradeRfqResMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.EXPIRED.getValue(),
				body.getTransactTime(), body.getQuoteTransType(), null) == 1;
	}

	private MockBondRFQReplyReceiveMessageBody buildMockMessageBody(CfetsTradeBondRFQReplyReceiveMessage message) {
		MockBondRFQReplyReceiveMessageBody mockBody = new MockBondRFQReplyReceiveMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setMyside(CfetsTradeSideUtil.changeSide(mockBody.getSide()));
		mockBody.setSendOrRecv(SendOrRecvFlag.RECV);
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return mockBody;
	}

}
