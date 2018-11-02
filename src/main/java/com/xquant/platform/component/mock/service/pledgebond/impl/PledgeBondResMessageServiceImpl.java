package com.xquant.platform.component.mock.service.pledgebond.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.common.CfetsTradeCollateralBond;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeDlgMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dao.MockCfetsTradePledgeBondDao;
import com.xquant.platform.component.mock.dao.PledgeBondQuoteResMessageDao;
import com.xquant.platform.component.mock.dto.cfets.MockCfetsTradePledgeBond;
import com.xquant.platform.component.mock.dto.cfets.MockCollateralDialogueQuoteResMessageBody;
import com.xquant.platform.component.mock.service.pledgebond.PledgeBondResMessageService;
import com.xquant.platform.component.mock.util.CfetsTradeSideUtil;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

@Service
public class PledgeBondResMessageServiceImpl implements PledgeBondResMessageService {

	@Autowired
	private PledgeBondQuoteResMessageDao pledgeBondQuoteResMessageDao;

	@Autowired
	private CommonCfetsTradeDlgMessageDao commonCfetsTradeDlgMessageDao;

	@Autowired
	private DatabaseQueryDao sequenceQueryDao;

	@Autowired
	private MockCfetsTradePledgeBondDao mockCfetsTradePledgeBondDao;

	@Override
	public boolean addMessage(CfetsTradeCollateralDialogueQuoteResMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		if (pledgeBondQuoteResMessageDao.insert(buildMockMessageBody(message)) == 1) {
			List<MockCfetsTradePledgeBond> resolvePledgeBonds = resolvePledgeBonds(message);
			for (MockCfetsTradePledgeBond mockCfetsTradePledgeBond : resolvePledgeBonds) {
				mockCfetsTradePledgeBondDao.insert(mockCfetsTradePledgeBond);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean updateMessage(CfetsTradeCollateralDialogueQuoteResMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		if (commonCfetsTradeDlgMessageDao.delete(message.getBody().getQuoteID()) == 1) {
			mockCfetsTradePledgeBondDao.delete(message.getBody().getQuoteID());
			if (pledgeBondQuoteResMessageDao.insert(buildMockMessageBody(message)) == 1) {
				List<MockCfetsTradePledgeBond> resolvePledgeBonds = resolvePledgeBonds(message);
				for (MockCfetsTradePledgeBond mockCfetsTradePledgeBond : resolvePledgeBonds) {
					mockCfetsTradePledgeBondDao.insert(mockCfetsTradePledgeBond);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean cancelMessage(CfetsTradeCollateralDialogueQuoteResMessage message) {
		CfetsTradeCollateralDialogueQuoteResMessageBody body = message.getBody();
		return commonCfetsTradeDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.CANCELED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public boolean expireMessage(CfetsTradeCollateralDialogueQuoteResMessage message) {
		CfetsTradeCollateralDialogueQuoteResMessageBody body = message.getBody();
		return commonCfetsTradeDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.EXPIRED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public boolean confirmMessage(CfetsTradeCollateralDialogueQuoteResMessage message) {
		CfetsTradeCollateralDialogueQuoteResMessageBody body = message.getBody();
		return commonCfetsTradeDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.FULL_DEALED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public boolean rejectMessage(CfetsTradeCollateralDialogueQuoteResMessage message) {
		CfetsTradeCollateralDialogueQuoteResMessageBody body = message.getBody();
		return commonCfetsTradeDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.REJECTED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public List<MockCollateralDialogueQuoteResMessageBody> queryUnFinalValidQuote() {
		String formatTime = MockDatePatternUtil.DATE_TIME_PATTERN.format(new Date());
		int count = pledgeBondQuoteResMessageDao.countUnFinalQuoteAfterTime(formatTime);
		if (count > 0) {
			return pledgeBondQuoteResMessageDao.queryUnFinalQuoteAfterTime(formatTime);
		}
		return null;
	}

	private MockCollateralDialogueQuoteResMessageBody buildMockMessageBody(
			CfetsTradeCollateralDialogueQuoteResMessage message) {
		MockCollateralDialogueQuoteResMessageBody mockBody = new MockCollateralDialogueQuoteResMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setMyside(mockBody.getSide());
		mockBody.setSendOrRecv(SendOrRecvFlag.SEND);
		mockBody.setClientId(message.getHeader().getClientID());
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return mockBody;
	}

	private List<MockCfetsTradePledgeBond> resolvePledgeBonds(CfetsTradeCollateralDialogueQuoteResMessage message) {
		List<MockCfetsTradePledgeBond> pledgeBonds = new ArrayList<MockCfetsTradePledgeBond>();
		if (message.getBody().getCollateralBondList() != null
				&& CollectionUtils.isNotEmpty(message.getBody().getCollateralBondList().getCollateralBondList())) {
			ArrayList<CfetsTradeCollateralBond> collateralBondList = message.getBody().getCollateralBondList()
					.getCollateralBondList();
			String updateTime = MockDateTimeUtil.getTransactimeOfNow();
			for (CfetsTradeCollateralBond cfetsTradeCollateralBond : collateralBondList) {
				MockCfetsTradePledgeBond pledgeBond = new MockCfetsTradePledgeBond();
				pledgeBond.setClientId(message.getHeader().getClientID());
				pledgeBond.setClordid(message.getBody().getClOrdID());
				pledgeBond.setClordidClientId(message.getBody().getClOrdIDClientID());
				pledgeBond.setQid(message.getBody().getQid());
				pledgeBond.setQuoteId(message.getBody().getQuoteID());
				pledgeBond.setUnderlyingQty(cfetsTradeCollateralBond.getUnderlyingQty());
				pledgeBond.setUnderlyingSecurityid(cfetsTradeCollateralBond.getUnderlyingSecurityID());
				pledgeBond.setUnderlyingStipvalue(cfetsTradeCollateralBond.getUnderlyingStipValue());
				pledgeBond.setUnderlyingSymbol(cfetsTradeCollateralBond.getUnderlyingSymbol());
				pledgeBond.setUpdateTime(updateTime);
				pledgeBonds.add(pledgeBond);
			}
		}
		return pledgeBonds;
	}

}
