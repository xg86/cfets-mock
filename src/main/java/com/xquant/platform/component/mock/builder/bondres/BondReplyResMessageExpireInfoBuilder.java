package com.xquant.platform.component.mock.builder.bondres;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

/**
 * 
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.bondres 
 * @author: guanglai.zhou   
 * @date: 2018-08-20 08:40:54
 * 本方请求回复报价过期
 */
@Component
public class BondReplyResMessageExpireInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondRFQReplyResMessage> {
	
	@Override
	public CfetsTradeBondRFQReplyResMessage getReturnMessage(CfetsTradeBondRFQReplyResMessage messageTempldate,
			CfetsTradeBondRFQReplyResMessage originMessage) {
		CfetsTradeBondRFQReplyResMessage message = new CfetsTradeBondRFQReplyResMessage();
		CfetsTradeBondRFQReplyResMessageBody body = new CfetsTradeBondRFQReplyResMessageBody();
		body.setClOrdIDClientID(originMessage.getBody().getClOrdIDClientID());
		body.setQuoteReqID(originMessage.getBody().getQuoteReqID());
		body.setQuoteID(originMessage.getBody().getQuoteID());
		body.setTransactTime(originMessage.getBody().getValidUntilTime());
		body.setValidUntilTime(originMessage.getBody().getValidUntilTime());
		body.setMarketIndicator(originMessage.getBody().getMarketIndicator());
		body.setSecurityType(originMessage.getBody().getSecurityType());
		body.setQuoteType(originMessage.getBody().getQuoteType());
		body.setSecurityID(originMessage.getBody().getSecurityID());
		body.setSide(originMessage.getBody().getSide());
		body.setQid(originMessage.getBody().getQid());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setErrorCode("0");
		body.setPartyName(originMessage.getBody().getPartyName());
		body.setTraderName(originMessage.getBody().getTraderName());
		body.setCounterPartyName(originMessage.getBody().getCounterPartyName());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQReplyResMessage message, Date triggerTime) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValidUntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValidUntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public String getQuoteId(CfetsTradeBondRFQReplyResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondRFQReplyResMessage message, Date triggerTime) {
		
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondRFQReplyResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_RFQREPLY_EXPIRED;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondRFQReplyResMessage message) {
		
	}

}
