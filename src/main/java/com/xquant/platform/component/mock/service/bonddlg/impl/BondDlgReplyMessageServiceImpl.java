package com.xquant.platform.component.mock.service.bonddlg.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReplyResMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeDlgMessageDao;
import com.xquant.platform.component.mock.service.bonddlg.BondDlgReplyMessageService;

@Service
public class BondDlgReplyMessageServiceImpl implements BondDlgReplyMessageService {

	@Autowired
	private CommonCfetsTradeDlgMessageDao commonBondDlgMessageDao;

	@Override
	public boolean confirmMessage(CfetsTradeBondDialogueQuoteReplyResMessage message) {
		CfetsTradeBondDialogueQuoteReplyResMessageBody body = message.getBody();
		return commonBondDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.FULL_DEALED.getValue(),
				body.getTransactTime(), null) == 1;
	}

	@Override
	public boolean rejectMessage(CfetsTradeBondDialogueQuoteReplyResMessage message) {
		CfetsTradeBondDialogueQuoteReplyResMessageBody body = message.getBody();
		return commonBondDlgMessageDao.updateStatus(body.getQuoteID(), QuoteStatusEnum.REJECTED.getValue(),
				body.getTransactTime(), null) == 1;
	}

}
