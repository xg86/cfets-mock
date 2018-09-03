/**
 * 
 */
package com.xquant.platform.component.mock.builder.expire;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.cfets.MockBondClickDealQuoteResMessageBody;
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
public class BondClickDealExpiredInfoBuilder4Self
		extends AbstractMockMessageInfoBuilder4Expired<CfetsTradeBondClickDealQuoteResMessage> {

	@Override
	public QuoteBizTypeEnum supportQuote() {
		return QuoteBizTypeEnum.BOND_CLICKDEAL;
	}

	@Override
	public boolean isSelfQuote() {
		return true;
	}

	@Override
	public CfetsTradeBondClickDealQuoteResMessage getReturnMessage(IMockCfetsMessageBody messageBody) {
		CfetsTradeBondClickDealQuoteResMessage message = new CfetsTradeBondClickDealQuoteResMessage();
		CfetsTradeBondClickDealQuoteResMessageBody body = new CfetsTradeBondClickDealQuoteResMessageBody();
		MockBondClickDealQuoteResMessageBody mockbody = (MockBondClickDealQuoteResMessageBody) messageBody;
		body.setClOrdIDClientID(mockbody.getClOrdIDClientID());
		body.setSecurityType(mockbody.getSecurityType());
		body.setMarketIndicator(mockbody.getMarketIndicator());
		body.setQuoteType(mockbody.getQuoteType());
		body.setValidUntilTime(mockbody.getValidUntilTime());
		body.setTransactTime(mockbody.getValidUntilTime());
		body.setAnonymousIndicator(mockbody.getAnonymousIndicator());
		body.setSecurityID(mockbody.getSecurityID());
		body.setSide(mockbody.getSide());
		body.setPartyID(mockbody.getPartyID());
		body.setTraderID(mockbody.getTraderID());
		body.setQuoteID(mockbody.getQuoteID());
		body.setTransactTime(mockbody.getValidUntilTime());
		body.setQid(mockbody.getQid());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setErrorCode("0");
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondClickDealQuoteResMessage message) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValidUntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValidUntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public String getQuoteId(CfetsTradeBondClickDealQuoteResMessage message) {
		return message.getBody().getQuoteID();
	}


	@Override
	protected void setHeader(CfetsTradeBondClickDealQuoteResMessage message, IMockCfetsMessageBody messageBody) {
		MockBondClickDealQuoteResMessageBody mockbody = (MockBondClickDealQuoteResMessageBody) messageBody;
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
