package com.xquant.platform.component.mock.builder.bonddlg;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;

@Component
public class BondDlgResMessageUpdateInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondDialogueQuoteResMessage> {

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondDialogueQuoteResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4OtherOpbit(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public CfetsTradeBondDialogueQuoteResMessage getReturnMessage(
			CfetsTradeBondDialogueQuoteResMessage messageTempldate,
			CfetsTradeBondDialogueQuoteResMessage originMessage) {
		CfetsTradeBondDialogueQuoteResMessage message = originMessage;
		CfetsTradeBondDialogueQuoteResMessageBody body = message.getBody();
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus(5);
		body.setErrorCode("0");
		body.setSettlDate(MockDatePatternUtil.YYYYMMDD.format(new Date()));
		body.setPartyName(PARTY_NAME);
		body.setTraderName(TRADER_NAME);
		body.setCustodianInstitutionName(CUSTODIAN_INSTITUTION_NAME);
		body.setCashBankName(CASH_BANK_NAME);
		body.setCounterPartyName(COUNTER_PARTY_NAME);
		return message;
	}


	@Override
	public Date resolveTriggerTime(CfetsTradeBondDialogueQuoteResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public String getQuoteId(CfetsTradeBondDialogueQuoteResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondDialogueQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_DIALOGUE_UPDATE;
	}


	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondDialogueQuoteResMessage message) {
		fillWithCompute4BondDlg(computeService, message.getBody());
	}

}
