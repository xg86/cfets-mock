package com.xquant.platform.component.mock.builder.bondrfq;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.bondrfq
 * @author: guanglai.zhou
 * @date: 2018-08-20 08:10:01 本方请求报价过期
 */
@Component
public class BondRfqResMessageExpireInfoBuilder extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondRFQResMessage> {

	@Override
	public CfetsTradeBondRFQResMessage getReturnMessage(CfetsTradeBondRFQResMessage messageTempldate,
			CfetsTradeBondRFQResMessage originMessage) {
		CfetsTradeBondRFQResMessage message = new CfetsTradeBondRFQResMessage();
		CfetsTradeBondRFQResMessageBody body = new CfetsTradeBondRFQResMessageBody();
		body.setClOrdIDClientID(originMessage.getBody().getClOrdIDClientID());
		body.setQuoteID(originMessage.getBody().getQuoteID());
		body.setTransactTime(originMessage.getBody().getValidUntilTime());
		body.setValidUntilTime(originMessage.getBody().getValidUntilTime());
		body.setMarketIndicator(originMessage.getBody().getMarketIndicator());
		body.setSecurityType(originMessage.getBody().getSecurityType());
		body.setQuoteType(originMessage.getBody().getQuoteType());
		body.setSecurityID(originMessage.getBody().getSecurityID());
		body.setPartyID(originMessage.getBody().getPartyID());
		body.setTraderID(originMessage.getBody().getTraderID());
		body.setQid(originMessage.getBody().getQid());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setErrorCode("0");
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQResMessage message, Date triggerTime) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValidUntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValidUntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public String getQuoteId(CfetsTradeBondRFQResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondRFQResMessage message, Date triggerTime) {

	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondRFQResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_RFQREQ_EXPIRED;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondRFQResMessage message) {

	}

}
