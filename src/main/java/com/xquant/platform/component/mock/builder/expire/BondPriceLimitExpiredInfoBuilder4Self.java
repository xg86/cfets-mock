/**
 * 
 */
package com.xquant.platform.component.mock.builder.expire;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.cfets.MockBondPriceLimitQuoteResMessageBody;
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
public class BondPriceLimitExpiredInfoBuilder4Self
		extends AbstractMockMessageInfoBuilder4Expired<CfetsTradeBondPriceLimitQuoteResMessage> {

	@Override
	public QuoteBizTypeEnum supportQuote() {
		return QuoteBizTypeEnum.BOND_PRICELIMIT;
	}

	@Override
	public boolean isSelfQuote() {
		return true;
	}

	@Override
	public CfetsTradeBondPriceLimitQuoteResMessage getReturnMessage(IMockCfetsMessageBody messageBody) {
		CfetsTradeBondPriceLimitQuoteResMessage message = new CfetsTradeBondPriceLimitQuoteResMessage();
		CfetsTradeBondPriceLimitQuoteResMessageBody body = new CfetsTradeBondPriceLimitQuoteResMessageBody();
		MockBondPriceLimitQuoteResMessageBody mockbody = (MockBondPriceLimitQuoteResMessageBody) messageBody;
		body.setClOrdIDClientID(mockbody.getClOrdIDClientID());
		body.setSecurityType(mockbody.getSecurityType());
		body.setMarketIndicator(mockbody.getMarketIndicator());
		body.setSecurityID(mockbody.getSecurityID());
		body.setSide(mockbody.getSide());
		body.setSplitIndicator(mockbody.getSplitIndicator());
		body.setPartyID(mockbody.getPartyID());
		body.setTraderID(mockbody.getTraderID());
		body.setOrderID(mockbody.getOrderID());
		body.setTransactTime(mockbody.getExpireTime());
		body.setQid(mockbody.getQid());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setErrorCode("0");
		body.setExpireTime(mockbody.getExpireTime());
		body.setExpireTime(mockbody.getExpireTime());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondPriceLimitQuoteResMessage message) {
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
	protected void setHeader(CfetsTradeBondPriceLimitQuoteResMessage message, IMockCfetsMessageBody messageBody) {
		MockBondPriceLimitQuoteResMessageBody mockbody = (MockBondPriceLimitQuoteResMessageBody) messageBody;
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
