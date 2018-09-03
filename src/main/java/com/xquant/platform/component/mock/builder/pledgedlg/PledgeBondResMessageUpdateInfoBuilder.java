package com.xquant.platform.component.mock.builder.pledgedlg;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

@Component
public class PledgeBondResMessageUpdateInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeCollateralDialogueQuoteResMessage> {

	@Override
	public CfetsTradeCollateralDialogueQuoteResMessage getReturnMessage(
			CfetsTradeCollateralDialogueQuoteResMessage messageTempldate,
			CfetsTradeCollateralDialogueQuoteResMessage originMessage) {
		CfetsTradeCollateralDialogueQuoteResMessage message = originMessage;
		CfetsTradeCollateralDialogueQuoteResMessageBody body = message.getBody();
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus(5);
		body.setErrorCode("0");
		body.setCashHoldingDays(Integer.parseInt(body.getTradeLimitDays()));
		body.setSettlDate(MockDateTimeUtil.timeAfter(MockDateTimeUtil.YYYYMMDD.format(new Date()), "yyyyMMdd",
				Calendar.DAY_OF_MONTH, 1));
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(body.getTradeLimitDays()) + 1);
		body.setSettlDate2(MockDateTimeUtil.YYYYMMDD.format(calendar.getTime()));
		fillWithCompute4PledgeBond(computeService, body);
		body.setPartyName(PARTY_NAME);
		body.setTraderName(TRADER_NAME);
		body.setCustodianInstitutionName(CUSTODIAN_INSTITUTION_NAME);
		body.setCashBankName(CASH_BANK_NAME);
		body.setCounterPartyName(COUNTER_PARTY_NAME);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeCollateralDialogueQuoteResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(CfetsTradeCollateralDialogueQuoteResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeCollateralDialogueQuoteResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4OtherOpbit(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeCollateralDialogueQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.PLEDGEREPO_DIALOGUE_UPDATE;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeCollateralDialogueQuoteResMessage message) {
		fillWithCompute4PledgeBond(computeService, message.getBody());

	}
}
