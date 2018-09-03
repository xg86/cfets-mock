package com.xquant.platform.component.mock.builder.bonddlg;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReplyResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.skeleton.enums.common.MarketIndicatorEnum;
import com.xquant.platform.component.itf.cfets.skeleton.enums.quote.SecurityTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.dao.BondDlgQuoteReplyResMessageDao;
import com.xquant.platform.component.mock.util.PropertiesCopyUtil;

@Component
public class BondDialogueReplyMessageRejectInfo
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondDialogueQuoteReplyResMessage> {

	@Autowired
	private BondDlgQuoteReplyResMessageDao BondDlgQuoteReplyResMessageDao;

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondDialogueQuoteReplyResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4OtherOpbit(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public CfetsTradeBondDialogueQuoteReplyResMessage getReturnMessage(
			CfetsTradeBondDialogueQuoteReplyResMessage messageTempldate,
			CfetsTradeBondDialogueQuoteReplyResMessage originMessage) {
		CfetsTradeBondDialogueQuoteReplyResMessage message = originMessage;
		CfetsTradeBondDialogueQuoteReplyResMessageBody body = message.getBody();
		CfetsTradeBondDialogueQuoteReplyResMessageBody queryMessageBody = BondDlgQuoteReplyResMessageDao
				.queryByQuoteId(body.getQuoteID());
		PropertiesCopyUtil.fieldCopyIfNull(queryMessageBody, body);
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus(5);
		body.setErrorCode("0");
		body.setSecurityType(SecurityTypeEnum.BOND.getValue());
		body.setMarketIndicator(MarketIndicatorEnum.BOND.getValue().toString());
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondDialogueQuoteReplyResMessage message,Date triggerTime) {
		return new Date();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public String getQuoteId(CfetsTradeBondDialogueQuoteReplyResMessage message) {
		return message.getBody().getQuoteID();
	}


	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondDialogueQuoteReplyResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_DIALOGUE_REJECT;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondDialogueQuoteReplyResMessage message) {
		
	}

}
