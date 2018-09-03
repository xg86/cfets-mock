package com.xquant.platform.component.mock.service.bonddlg.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.BondDlgQuoteReceiveMessageDao;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeDlgMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dto.cfets.MockBondDialogueQuoteReceiveMessageBody;
import com.xquant.platform.component.mock.service.bonddlg.BondDlgReceiveMessageService;
import com.xquant.platform.component.mock.util.CfetsTradeSideUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

@Service
public class BondDlgReceiveMessageServiceImpl implements BondDlgReceiveMessageService {

	@Resource
	private BondDlgQuoteReceiveMessageDao bondDlgQuoteReceiveMessageDao;

	@Autowired
	private DatabaseQueryDao sequenceQueryDao;
	
	@Autowired
	private CommonCfetsTradeDlgMessageDao commonBondDlgMessageDao;

	@Override
	public boolean addMessage(CfetsTradeBondDialogueQuoteReceiveMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		return bondDlgQuoteReceiveMessageDao.insert(buildMockMessageBody(message)) == 1;

	}

	@Override
	public boolean updateMessage(CfetsTradeBondDialogueQuoteReceiveMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		if (commonBondDlgMessageDao.delete(message.getBody().getQuoteID()) == 1) {
			return bondDlgQuoteReceiveMessageDao.insert(buildMockMessageBody(message)) == 1;
		}
		return false;
	}

	@Override
	public boolean cancelMessage(CfetsTradeBondDialogueQuoteReceiveMessage message) {
		CfetsTradeBondDialogueQuoteReceiveMessageBody body = message.getBody();
		return commonBondDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.CANCELED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public boolean expireMessage(CfetsTradeBondDialogueQuoteReceiveMessage message) {
		CfetsTradeBondDialogueQuoteReceiveMessageBody body = message.getBody();
		return commonBondDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.EXPIRED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public boolean updateByCounterClient(CfetsTradeBondDialogueQuoteReceiveMessage message) {
		return updateMessage(message);
	}

	private MockBondDialogueQuoteReceiveMessageBody buildMockMessageBody(
			CfetsTradeBondDialogueQuoteReceiveMessage message) {
		MockBondDialogueQuoteReceiveMessageBody mockBody = new MockBondDialogueQuoteReceiveMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setClientId(message.getHeader().getClientID());
		mockBody.setMyside(CfetsTradeSideUtil.changeSide(mockBody.getSide()));
		mockBody.setSendOrRecv(SendOrRecvFlag.RECV);
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return mockBody;
	}

}
