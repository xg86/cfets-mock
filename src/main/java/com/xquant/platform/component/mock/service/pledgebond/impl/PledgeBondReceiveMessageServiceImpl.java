package com.xquant.platform.component.mock.service.pledgebond.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.common.CfetsTradeCollateralBond;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeDlgMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dao.MockCfetsTradePledgeBondDao;
import com.xquant.platform.component.mock.dao.PledgeBondQuoteReceiveMessageDao;
import com.xquant.platform.component.mock.dto.cfets.MockCfetsTradePledgeBond;
import com.xquant.platform.component.mock.dto.cfets.MockCollateralDialogueQuoteReceiveMessageBody;
import com.xquant.platform.component.mock.service.pledgebond.PledgeBondReceiveMessageService;
import com.xquant.platform.component.mock.util.CfetsTradeSideUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

@Service
public class PledgeBondReceiveMessageServiceImpl implements PledgeBondReceiveMessageService {

	@Autowired
	private PledgeBondQuoteReceiveMessageDao pledgeBondQuoteReceiveMessageDao;

	@Autowired
	private CommonCfetsTradeDlgMessageDao commonCfetsTradeDlgMessageDao;

	@Autowired
	private DatabaseQueryDao sequenceQueryDao;

	@Autowired
	private MockCfetsTradePledgeBondDao mockCfetsTradePledgeBondDao;

	@Override
	public boolean addMessage(CfetsTradeCollateralDialogueQuoteReceiveMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		if (pledgeBondQuoteReceiveMessageDao.insert(buildMockMessageBody(message)) == 1) {
			List<MockCfetsTradePledgeBond> resolvePledgeBonds = resolvePledgeBonds(message);
			for (MockCfetsTradePledgeBond mockCfetsTradePledgeBond : resolvePledgeBonds) {
				mockCfetsTradePledgeBondDao.insert(mockCfetsTradePledgeBond);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean updateMessage(CfetsTradeCollateralDialogueQuoteReceiveMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		if (commonCfetsTradeDlgMessageDao.delete(message.getBody().getQuoteID()) == 1) {
			mockCfetsTradePledgeBondDao.delete(message.getBody().getQuoteID());
			if (pledgeBondQuoteReceiveMessageDao.insert(buildMockMessageBody(message)) == 1) {
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
	public boolean cancelMessage(CfetsTradeCollateralDialogueQuoteReceiveMessage message) {
		CfetsTradeCollateralDialogueQuoteReceiveMessageBody body = message.getBody();
		return commonCfetsTradeDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.CANCELED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public boolean expireMessage(CfetsTradeCollateralDialogueQuoteReceiveMessage message) {
		CfetsTradeCollateralDialogueQuoteReceiveMessageBody body = message.getBody();
		return commonCfetsTradeDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.EXPIRED.getValue(),
				body.getTransactTime(), body.getQuoteTransType()) == 1;
	}

	@Override
	public boolean updateByCounterClient(CfetsTradeCollateralDialogueQuoteReceiveMessage message) {
		return updateMessage(message);
	}

	private MockCollateralDialogueQuoteReceiveMessageBody buildMockMessageBody(
			CfetsTradeCollateralDialogueQuoteReceiveMessage message) {
		MockCollateralDialogueQuoteReceiveMessageBody mockBody = new MockCollateralDialogueQuoteReceiveMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setMyside(CfetsTradeSideUtil.changeSide(mockBody.getSide()));
		mockBody.setSendOrRecv(SendOrRecvFlag.RECV);
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return mockBody;
	}

	private List<MockCfetsTradePledgeBond> resolvePledgeBonds(CfetsTradeCollateralDialogueQuoteReceiveMessage message) {
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
