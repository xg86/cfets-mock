package com.xquant.platform.component.mock.builder.pledgedlg;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

@Component
public class PledgeBondReceiveMessageAddInfo
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeCollateralDialogueQuoteReceiveMessage> {

	@Override
	public CfetsTradeCollateralDialogueQuoteReceiveMessage getReturnMessage(
			CfetsTradeCollateralDialogueQuoteReceiveMessage messageTempldate,
			CfetsTradeCollateralDialogueQuoteReceiveMessage originMessage) {
		CfetsTradeCollateralDialogueQuoteReceiveMessageBody body = messageTempldate.getBody();
		body.setSettlDate(MockDateTimeUtil.timeAfter(MockDateTimeUtil.YYYYMMDD.format(new Date()), "yyyyMMdd",
				Calendar.DAY_OF_MONTH, 1));
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(body.getTradeLimitDays()) + 1);
		body.setSettlDate2(MockDateTimeUtil.YYYYMMDD.format(calendar.getTime()));
		return messageTempldate;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeCollateralDialogueQuoteReceiveMessage message, Date triggerTime) {
		Assert.notNull(triggerTime, "triggerTime can not be null");
		return triggerTime;
	}

	@Override
	public String getQuoteId(CfetsTradeCollateralDialogueQuoteReceiveMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeCollateralDialogueQuoteReceiveMessage message,
			Date triggerTime) {
		Map<String, String> generateParms = generateParms4CounterAdd(triggerTime);
		message.getBody().setQuoteID(generateParms.get(QUOTEID));
		message.getBody().setClOrdIDClientID(generateParms.get(CLORDIDCLIENTID));
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setValidUntilTime(generateParms.get(VALIDUNTILTIME));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeCollateralDialogueQuoteReceiveMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.PLEDGEREPO_DIALOGUE_ADD;
	}

	@Override
	public void fillWithCompute(ComputeService computeService,
			CfetsTradeCollateralDialogueQuoteReceiveMessage message) {

	}

}
