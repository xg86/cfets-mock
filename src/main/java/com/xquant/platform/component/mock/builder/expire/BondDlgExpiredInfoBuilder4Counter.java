/**
 * 
 */
package com.xquant.platform.component.mock.builder.expire;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.cfets.MockBondDialogueQuoteResMessageBody;
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
public class BondDlgExpiredInfoBuilder4Counter
		extends AbstractMockMessageInfoBuilder4Expired<CfetsTradeBondDialogueQuoteReceiveMessage> {

	@Override
	public QuoteBizTypeEnum supportQuote() {
		return QuoteBizTypeEnum.BOND_DLG;
	}

	@Override
	public boolean isSelfQuote() {
		return false;
	}

	@Override
	public CfetsTradeBondDialogueQuoteReceiveMessage getReturnMessage(IMockCfetsMessageBody messageBody) {
		CfetsTradeBondDialogueQuoteReceiveMessage message = new CfetsTradeBondDialogueQuoteReceiveMessage();
		CfetsTradeBondDialogueQuoteReceiveMessageBody body = new CfetsTradeBondDialogueQuoteReceiveMessageBody();
		MockBondDialogueQuoteResMessageBody mockBody = (MockBondDialogueQuoteResMessageBody) messageBody;
		body.setQuoteID(mockBody.getQuoteID());
		body.setTransactTime(mockBody.getValidUntilTime());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setClientID(mockBody.getClientId());
		body.setClOrdIDClientID(mockBody.getClOrdIDClientID());
		body.setIsQuoteResponse(false);
		body.setQuoteType(Integer.parseInt(mockBody.getQuoteType()));
		body.setSecurityType(mockBody.getSecurityType());
		body.setMarketIndicator(mockBody.getMarketIndicator());
		body.setDeliveryType(Integer.parseInt(mockBody.getDeliveryType()));
		body.setClearingMethod(Integer.parseInt(mockBody.getClearingMethod()));
		body.setPartyID(mockBody.getPartyID());
		body.setPartyName(mockBody.getPartyName());
		body.setTraderID(mockBody.getTraderID());
		body.setTraderName(mockBody.getTraderName());
		body.setCounterPartyID(mockBody.getCounterPartyID());
		body.setCounterPartyName(mockBody.getCounterPartyName());
		body.setCounterTraderID(mockBody.getCounterTraderID());
		body.setCounterTraderName(mockBody.getCounterTraderName());
		body.setValidUntilTime(mockBody.getValidUntilTime());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondDialogueQuoteReceiveMessage message) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValidUntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValidUntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public String getQuoteId(CfetsTradeBondDialogueQuoteReceiveMessage message) {
		return message.getBody().getQuoteID();
	}
	
	@Override
	protected void setHeader(CfetsTradeBondDialogueQuoteReceiveMessage message, IMockCfetsMessageBody messageBody) {
		MockBondDialogueQuoteResMessageBody mockbody = (MockBondDialogueQuoteResMessageBody) messageBody;
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
