package com.xquant.platform.component.mock.builder.clickdeal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.common.CfetsTradeBondLeg;
import com.xquant.cfets.trade.protocol.common.CfetsTradeBondLegList;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.enums.quote.SideEnum;
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
public class MarketMakerMessageExpireInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondMarketMakingQuoteResMessage> {

	@Override
	public CfetsTradeBondMarketMakingQuoteResMessage getReturnMessage(
			CfetsTradeBondMarketMakingQuoteResMessage messageTempldate,
			CfetsTradeBondMarketMakingQuoteResMessage originMessage) {
		CfetsTradeBondMarketMakingQuoteResMessage message = new CfetsTradeBondMarketMakingQuoteResMessage();
		CfetsTradeBondMarketMakingQuoteResMessageBody body = new CfetsTradeBondMarketMakingQuoteResMessageBody();
		body.setClOrdIDClientID(originMessage.getBody().getClOrdIDClientID());
		body.setSecurityType(originMessage.getBody().getSecurityType());
		body.setMarketIndicator(originMessage.getBody().getMarketIndicator());
		body.setQuoteType(originMessage.getBody().getQuoteType());
		body.setValidUntilTime(originMessage.getBody().getValidUntilTime());
		body.setTransactTime(originMessage.getBody().getValidUntilTime());
		body.setAnonymousIndicator(originMessage.getBody().getAnonymousIndicator());
		body.setSecurityID(originMessage.getBody().getSecurityID());
		body.setPartyID(originMessage.getBody().getPartyID());
		body.setTraderID(originMessage.getBody().getTraderID());
		body.setQuoteID(originMessage.getBody().getQuoteID());
		body.setTransactTime(originMessage.getBody().getValidUntilTime());
		body.setQid(originMessage.getBody().getQid());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setErrorCode("0");
		CfetsTradeBondLegList bondLegList = new CfetsTradeBondLegList();
		ArrayList<CfetsTradeBondLeg> legList = new ArrayList<CfetsTradeBondLeg>();
		CfetsTradeBondLeg buyPart = new CfetsTradeBondLeg();
		buyPart.setLegSide(SideEnum.PURCHASE.getValue());
		buyPart.setLegSecurityID(body.getSecurityID());
		legList.add(buyPart);
		CfetsTradeBondLeg sellPart = new CfetsTradeBondLeg();
		sellPart.setLegSide(SideEnum.SELL.getValue());
		sellPart.setLegSecurityID(body.getSecurityID());
		legList.add(sellPart);
		bondLegList.setBondLegList(legList);
		body.setBondLegList(bondLegList);
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondMarketMakingQuoteResMessage message, Date triggerTime) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValidUntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValidUntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public String getQuoteId(CfetsTradeBondMarketMakingQuoteResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondMarketMakingQuoteResMessage message, Date triggerTime) {

	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondMarketMakingQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_MARKETMAKE_EXPIRED;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondMarketMakingQuoteResMessage message) {
	}
}
