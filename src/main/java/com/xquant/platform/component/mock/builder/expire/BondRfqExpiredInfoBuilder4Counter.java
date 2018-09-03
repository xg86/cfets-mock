/**
 * 
 */
package com.xquant.platform.component.mock.builder.expire;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQResMessageBody;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockGenerateUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.expire
 * @author: guanglai.zhou
 * @date: 2018-08-22 15:55:31
 */
@Component
public class BondRfqExpiredInfoBuilder4Counter
		extends AbstractMockMessageInfoBuilder4Expired<CfetsTradeBondRFQReceiveMessage> {

	@Override
	public QuoteBizTypeEnum supportQuote() {
		return QuoteBizTypeEnum.BOND_RFQ_REQ;
	}

	@Override
	public boolean isSelfQuote() {
		return false;
	}

	@Override
	public CfetsTradeBondRFQReceiveMessage getReturnMessage(IMockCfetsMessageBody messageBody) {
		CfetsTradeBondRFQReceiveMessage message = new CfetsTradeBondRFQReceiveMessage();
		CfetsTradeBondRFQReceiveMessageBody body = new CfetsTradeBondRFQReceiveMessageBody();
		MockBondRFQResMessageBody mockbody = (MockBondRFQResMessageBody) messageBody;
		body.setClOrdIDClientID(mockbody.getClOrdIDClientID());
		body.setQuoteID(mockbody.getQuoteID());
		body.setValidUntilTime(mockbody.getValidUntilTime());
		body.setTransactTime(mockbody.getValidUntilTime());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setClientID(mockbody.getClientId());
		body.setMarketIndicator(mockbody.getMarketIndicator());
		body.setSecurityType(mockbody.getSecurityType());
		body.setSecurityID(mockbody.getSecurityID());
		body.setPartyID(mockbody.getPartyID());
		body.setPartyName(mockbody.getPartyName());
		body.setTraderID(mockbody.getTraderID());
		body.setTraderName(mockbody.getTraderName());
		body.setQuoteType(mockbody.getQuoteType());
		body.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQReceiveMessage message) {
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
	protected void setHeader(CfetsTradeBondRFQReceiveMessage message, IMockCfetsMessageBody messageBody) {
		MockBondRFQResMessageBody mockbody = (MockBondRFQResMessageBody) messageBody;
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
