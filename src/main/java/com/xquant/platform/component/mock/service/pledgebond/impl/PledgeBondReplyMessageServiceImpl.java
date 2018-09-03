package com.xquant.platform.component.mock.service.pledgebond.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyResMessage;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeDlgMessageDao;
import com.xquant.platform.component.mock.service.pledgebond.PledgeBondReplyMessageService;

@Service
public class PledgeBondReplyMessageServiceImpl implements PledgeBondReplyMessageService {

	@Autowired
	private CommonCfetsTradeDlgMessageDao commonCfetsTradeDlgMessageDao;
	
	@Override
	public boolean confirmMessage(CfetsTradeCollateralDialogueQuoteReplyResMessage message) {
		return commonCfetsTradeDlgMessageDao.updateStatus(message.getBody().getQuoteID(), QuoteStatusEnum.FULL_DEALED.getValue(),
				message.getBody().getTransactTime(),null) == 1;
	}

	@Override
	public boolean rejectMessage(CfetsTradeCollateralDialogueQuoteReplyResMessage message) {
		return commonCfetsTradeDlgMessageDao.updateStatus(message.getBody().getQuoteID(), QuoteStatusEnum.REJECTED.getValue(),
				message.getBody().getTransactTime(),null) == 1;
	}

}
