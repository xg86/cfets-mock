/**
 * 
 */
package com.xquant.platform.component.mock.builder.expire;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQReplyResMessageBody;
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
public class BondReplyExpiredInfoBuilder4Counter
		extends AbstractMockMessageInfoBuilder4Expired<CfetsTradeBondRFQReplyReceiveMessage> {

	@Override
	public QuoteBizTypeEnum supportQuote() {
		return QuoteBizTypeEnum.BOND_RFQ_REPLY;
	}

	@Override
	public boolean isSelfQuote() {
		return false;
	}

	@Override
	public CfetsTradeBondRFQReplyReceiveMessage getReturnMessage(IMockCfetsMessageBody messageBody) {
		CfetsTradeBondRFQReplyReceiveMessage message = new CfetsTradeBondRFQReplyReceiveMessage();
		CfetsTradeBondRFQReplyReceiveMessageBody body = new CfetsTradeBondRFQReplyReceiveMessageBody();
		MockBondRFQReplyResMessageBody mockbody = (MockBondRFQReplyResMessageBody) messageBody;
		body.setQid(mockbody.getQid());
		body.setQuoteID(mockbody.getQuoteID());
		body.setTransactTime(mockbody.getValidUntilTime());
		body.setValiduntilTime(mockbody.getValidUntilTime());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setClientID(mockbody.getClientId());
		body.setClOrdIDClientID(mockbody.getClOrdIDClientID());
		body.setMarketIndicator(mockbody.getMarketIndicator());
		body.setSecurityType(mockbody.getSecurityType());
		body.setQuoteType(mockbody.getQuoteType());
		body.setSecurityID(mockbody.getSecurityID());
		body.setSide(mockbody.getSide());
		body.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQReplyReceiveMessage message) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValiduntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValiduntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public String getQuoteId(CfetsTradeBondRFQReplyReceiveMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	protected void setHeader(CfetsTradeBondRFQReplyReceiveMessage message, IMockCfetsMessageBody messageBody) {
		MockBondRFQReplyResMessageBody mockbody = (MockBondRFQReplyResMessageBody) messageBody;
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
