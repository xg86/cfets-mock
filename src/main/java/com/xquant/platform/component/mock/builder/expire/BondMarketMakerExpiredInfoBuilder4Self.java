/**
 * 
 */
package com.xquant.platform.component.mock.builder.expire;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.common.CfetsTradeBondLeg;
import com.xquant.cfets.trade.protocol.common.CfetsTradeBondLegList;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.itf.cfets.skeleton.enums.quote.SideEnum;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.cfets.MockBondMarketMakingQuoteResMessageBody;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockGenerateUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.expire
 * @author: guanglai.zhou
 * @date: 2018-08-22 15:55:31
 */
@Component
public class BondMarketMakerExpiredInfoBuilder4Self
		extends AbstractMockMessageInfoBuilder4Expired<CfetsTradeBondMarketMakingQuoteResMessage> {

	@Override
	public QuoteBizTypeEnum supportQuote() {
		return QuoteBizTypeEnum.BOND_MARKETMAKE;
	}

	@Override
	public boolean isSelfQuote() {
		return true;
	}

	@Override
	public CfetsTradeBondMarketMakingQuoteResMessage getReturnMessage(IMockCfetsMessageBody messageBody) {
		CfetsTradeBondMarketMakingQuoteResMessage message = new CfetsTradeBondMarketMakingQuoteResMessage();
		CfetsTradeBondMarketMakingQuoteResMessageBody body = new CfetsTradeBondMarketMakingQuoteResMessageBody();
		MockBondMarketMakingQuoteResMessageBody mockbody = (MockBondMarketMakingQuoteResMessageBody) messageBody;
		body.setClOrdIDClientID(mockbody.getClOrdIDClientID());
		body.setSecurityType(mockbody.getSecurityType());
		body.setMarketIndicator(mockbody.getMarketIndicator());
		body.setQuoteType(mockbody.getQuoteType());
		body.setValidUntilTime(mockbody.getValidUntilTime());
		body.setTransactTime(mockbody.getValidUntilTime());
		body.setAnonymousIndicator(mockbody.getAnonymousIndicator());
		body.setSecurityID(mockbody.getSecurityID());
		body.setPartyID(mockbody.getPartyID());
		body.setTraderID(mockbody.getTraderID());
		body.setQuoteID(mockbody.getQuoteID());
		body.setTransactTime(mockbody.getValidUntilTime());
		body.setQid(mockbody.getQid());
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
	public Date resolveTriggerTime(CfetsTradeBondMarketMakingQuoteResMessage message) {
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
	protected void setHeader(CfetsTradeBondMarketMakingQuoteResMessage message, IMockCfetsMessageBody messageBody) {
		MockBondMarketMakingQuoteResMessageBody mockbody = (MockBondMarketMakingQuoteResMessageBody) messageBody;
		CfetsTradeMessageHeader header = new CfetsTradeMessageHeader();
		header.setAction(mockbody.getAction());
		header.setClientID(mockbody.getClientId());
		header.setErrorCode("0");
		header.setMsgType(MsgTypeEnum.PUSH.getValue());
		header.setSendingTime(MockDatePatternUtil.DATE_TIME_PATTERN.format(new Date()));
		header.setSerialNo(MockGenerateUtil.getNextSerialNoByDate());
		header.setVersion(MockVersion.VERSION);
		message.setHeader(header);
	}

}
