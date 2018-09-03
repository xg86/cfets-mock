package com.xquant.platform.component.mock.builder.clickdeal;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

/**
 * 本方新增报价
 * 
 * @author Administrator
 *
 */
@Component
public class PriceLimitMessageExpireInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondPriceLimitQuoteResMessage> {

	@Override
	public CfetsTradeBondPriceLimitQuoteResMessage getReturnMessage(
			CfetsTradeBondPriceLimitQuoteResMessage messageTempldate,
			CfetsTradeBondPriceLimitQuoteResMessage originMessage) {
		CfetsTradeBondPriceLimitQuoteResMessage message = new CfetsTradeBondPriceLimitQuoteResMessage();
		CfetsTradeBondPriceLimitQuoteResMessageBody body = new CfetsTradeBondPriceLimitQuoteResMessageBody();
		body.setClOrdIDClientID(originMessage.getBody().getClOrdIDClientID());
		body.setSecurityType(originMessage.getBody().getSecurityType());
		body.setMarketIndicator(originMessage.getBody().getMarketIndicator());
		body.setSecurityID(originMessage.getBody().getSecurityID());
		body.setSide(originMessage.getBody().getSide());
		body.setSplitIndicator(originMessage.getBody().getSplitIndicator());
		body.setPartyID(originMessage.getBody().getPartyID());
		body.setTraderID(originMessage.getBody().getTraderID());
		body.setOrderID(originMessage.getBody().getOrderID());
		body.setTransactTime(originMessage.getBody().getExpireTime());
		body.setQid(originMessage.getBody().getQid());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setErrorCode("0");
		body.setExpireTime(originMessage.getBody().getExpireTime());
		body.setExpireTime(originMessage.getBody().getExpireTime());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondPriceLimitQuoteResMessage message, Date triggerTime) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getExpireTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getExpireTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public String getQuoteId(CfetsTradeBondPriceLimitQuoteResMessage message) {
		return message.getBody().getOrderID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondPriceLimitQuoteResMessage message, Date triggerTime) {

	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondPriceLimitQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_PRICELIMIT_EXPIRED;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondPriceLimitQuoteResMessage message) {

	}
}
