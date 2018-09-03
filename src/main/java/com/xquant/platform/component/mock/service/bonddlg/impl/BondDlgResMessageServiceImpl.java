package com.xquant.platform.component.mock.service.bonddlg.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.BondDlgQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeDlgMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dto.cfets.MockBondDialogueQuoteResMessageBody;
import com.xquant.platform.component.mock.service.bonddlg.BondDlgResMessageService;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

@Service
public class BondDlgResMessageServiceImpl implements BondDlgResMessageService {

	@Autowired
	private BondDlgQuoteResMessageDao bondDlgQuoteResMessageDao;

	@Autowired
	private DatabaseQueryDao sequenceQueryDao;

	@Autowired
	private CommonCfetsTradeDlgMessageDao commonBondDlgMessageDao;

	@Override
	public boolean addMessage(CfetsTradeBondDialogueQuoteResMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		return bondDlgQuoteResMessageDao.insert(buildMockMessageBody(message)) == 1;
	}

	@Override
	public boolean updateMessage(CfetsTradeBondDialogueQuoteResMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		if (commonBondDlgMessageDao.delete(message.getBody().getQuoteID()) == 1) {
			return bondDlgQuoteResMessageDao.insert(buildMockMessageBody(message)) == 1;
		}
		return false;
	}

	@Override
	public boolean cancelMessage(CfetsTradeBondDialogueQuoteResMessage message) {
		CfetsTradeBondDialogueQuoteResMessageBody body = message.getBody();
		return commonBondDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.CANCELED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public boolean expireMessage(CfetsTradeBondDialogueQuoteResMessage message) {
		CfetsTradeBondDialogueQuoteResMessageBody body = message.getBody();
		return commonBondDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.EXPIRED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public boolean confirmMessage(CfetsTradeBondDialogueQuoteResMessage message) {
		CfetsTradeBondDialogueQuoteResMessageBody body = message.getBody();
		return commonBondDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.FULL_DEALED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public boolean rejectMessage(CfetsTradeBondDialogueQuoteResMessage message) {
		CfetsTradeBondDialogueQuoteResMessageBody body = message.getBody();
		return commonBondDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.REJECTED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	private MockBondDialogueQuoteResMessageBody buildMockMessageBody(CfetsTradeBondDialogueQuoteResMessage message) {
		MockBondDialogueQuoteResMessageBody mockBody = new MockBondDialogueQuoteResMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setClientId(message.getHeader().getClientID());
		mockBody.setMyside(mockBody.getSide());
		mockBody.setSendOrRecv(SendOrRecvFlag.SEND);
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return mockBody;
	}

	@Override
	public List<MockBondDialogueQuoteResMessageBody> queryUnFinalValidQuote() {
		String formatTime = MockDatePatternUtil.DATE_TIME_PATTERN.format(new Date());
		int count = bondDlgQuoteResMessageDao.countUnFinalQuoteAfterTime(formatTime);
		if (count > 0) {
			return bondDlgQuoteResMessageDao.queryUnFinalQuoteAfterTime(formatTime);
		}
		return null;
	}
}
