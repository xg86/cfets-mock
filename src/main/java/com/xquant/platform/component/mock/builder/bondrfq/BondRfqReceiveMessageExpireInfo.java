package com.xquant.platform.component.mock.builder.bondrfq;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

@Component
public class BondRfqReceiveMessageExpireInfo extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondRFQReceiveMessage> {

	@Override
	public CfetsTradeBondRFQReceiveMessage getReturnMessage(CfetsTradeBondRFQReceiveMessage messageTempldate,
			CfetsTradeBondRFQReceiveMessage originMessage) {
		CfetsTradeBondRFQReceiveMessage message = new CfetsTradeBondRFQReceiveMessage();
		CfetsTradeBondRFQReceiveMessageBody body = new CfetsTradeBondRFQReceiveMessageBody();
		body.setQid(originMessage.getBody().getQid());
		body.setQuoteID(originMessage.getBody().getQuoteID());
		body.setTransactTime(originMessage.getBody().getValidUntilTime());
		body.setValidUntilTime(originMessage.getBody().getValidUntilTime());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setClientID(originMessage.getBody().getClientID());
		body.setClOrdIDClientID(originMessage.getBody().getClOrdIDClientID());
		body.setMarketIndicator(originMessage.getBody().getMarketIndicator());
		body.setSecurityType(originMessage.getBody().getSecurityType());
		body.setQuoteType(originMessage.getBody().getQuoteType());
		body.setQuoteType(originMessage.getBody().getQuoteType());
		body.setSecurityID(originMessage.getBody().getSecurityID());
		body.setPartyID(originMessage.getBody().getPartyID());
		body.setPartyName(originMessage.getBody().getPartyName());
		body.setTraderID(originMessage.getBody().getTraderID());
		body.setTraderName(originMessage.getBody().getTraderName());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQReceiveMessage message, Date triggerTime) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValidUntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValidUntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
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

	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondRFQReceiveMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_RFQREQ_EXPIRED;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondRFQReceiveMessage message) {

	}
}
