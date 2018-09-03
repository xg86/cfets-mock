package com.xquant.platform.component.mock.builder.bondrfq;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessage;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

@Component
public class BondRfqReceiveMessageAddInfo extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondRFQReceiveMessage> {

	@Override
	public CfetsTradeBondRFQReceiveMessage getReturnMessage(CfetsTradeBondRFQReceiveMessage messageTempldate,
			CfetsTradeBondRFQReceiveMessage originMessage) {
		return messageTempldate;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQReceiveMessage message, Date triggerTime) {
		Assert.notNull(triggerTime, "triggerTime can not be null");
		return triggerTime;
	}

	@Override
	public String getQuoteId(CfetsTradeBondRFQReceiveMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondRFQReceiveMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4CounterAdd(triggerTime);
		message.getBody().setQuoteID(generateParms.get(QUOTEID));
		message.getBody().setClOrdIDClientID(generateParms.get(CLORDIDCLIENTID));
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setValidUntilTime(generateParms.get(VALIDUNTILTIME));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondRFQReceiveMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_RFQREQ_ADD;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondRFQReceiveMessage message) {
		
	}

}
