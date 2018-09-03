package com.xquant.platform.component.mock.builder.bonddlg;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessage;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

@Component
public class BondDlgReceiveMessageAddInfo
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondDialogueQuoteReceiveMessage> {

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondDialogueQuoteReceiveMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4CounterAdd(triggerTime);
		message.getBody().setQuoteID(generateParms.get(QUOTEID));
		message.getBody().setClOrdIDClientID(generateParms.get(CLORDIDCLIENTID));
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setValidUntilTime(generateParms.get(VALIDUNTILTIME));
	}

	@Override
	public CfetsTradeBondDialogueQuoteReceiveMessage getReturnMessage(
			CfetsTradeBondDialogueQuoteReceiveMessage messageTempldate,
			CfetsTradeBondDialogueQuoteReceiveMessage originMessage) {
		return messageTempldate;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondDialogueQuoteReceiveMessage message,Date triggerTime) {
		Assert.notNull(triggerTime, "triggerTime can not be null");
		return triggerTime;
	}


	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public String getQuoteId(CfetsTradeBondDialogueQuoteReceiveMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondDialogueQuoteReceiveMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_DIALOGUE_ADD;
	}


	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondDialogueQuoteReceiveMessage message) {

	}

}
