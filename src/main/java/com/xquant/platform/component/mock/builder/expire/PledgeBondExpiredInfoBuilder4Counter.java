/**
 * 
 */
package com.xquant.platform.component.mock.builder.expire;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.itf.cfets.common.util.StringToNumber;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.cfets.MockCollateralDialogueQuoteResMessageBody;
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
public class PledgeBondExpiredInfoBuilder4Counter
		extends AbstractMockMessageInfoBuilder4Expired<CfetsTradeCollateralDialogueQuoteReceiveMessage> {

	@Override
	public QuoteBizTypeEnum supportQuote() {
		return QuoteBizTypeEnum.PLEDGEREPO_DLG;
	}

	@Override
	public boolean isSelfQuote() {
		return false;
	}

	@Override
	public CfetsTradeCollateralDialogueQuoteReceiveMessage getReturnMessage(IMockCfetsMessageBody messageBody) {
		CfetsTradeCollateralDialogueQuoteReceiveMessage message = new CfetsTradeCollateralDialogueQuoteReceiveMessage();
		CfetsTradeCollateralDialogueQuoteReceiveMessageBody body = new CfetsTradeCollateralDialogueQuoteReceiveMessageBody();
		MockCollateralDialogueQuoteResMessageBody mockbody = (MockCollateralDialogueQuoteResMessageBody) messageBody;
		body.setQuoteID(mockbody.getQuoteID());
		body.setTransactTime(mockbody.getValidUntilTime());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setClientID(mockbody.getClientId());
		body.setClOrdIDClientID(mockbody.getClOrdIDClientID());
		body.setIsQuoteResponse(false);
		body.setRepoMethod(mockbody.getRepoMethod());
		body.setQuoteType(StringToNumber.toInteger(mockbody.getQuoteType()));
		body.setSecurityType(mockbody.getSecurityType());
		body.setMarketIndicator(mockbody.getMarketIndicator());
		body.setDeliveryType(StringToNumber.toInteger(mockbody.getDeliveryType()));
		body.setDeliveryType2(StringToNumber.toInteger(mockbody.getDeliveryType2()));
		body.setClearingMethod(StringToNumber.toInteger(mockbody.getClearingMethod()));
		body.setCashHoldingDays(0);
		body.setPartyID(mockbody.getPartyID());
		body.setPartyName(mockbody.getPartyName());
		body.setTraderID(mockbody.getTraderID());
		body.setTraderName(mockbody.getTraderName());
		body.setCounterPartyID(mockbody.getCounterPartyID());
		body.setCounterPartyName(mockbody.getCounterPartyName());
		body.setCounterTraderID(mockbody.getCounterTraderID());
		body.setCounterTraderName(mockbody.getCounterTraderName());
		body.setValidUntilTime(mockbody.getValidUntilTime());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeCollateralDialogueQuoteReceiveMessage message) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValidUntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValidUntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public String getQuoteId(CfetsTradeCollateralDialogueQuoteReceiveMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	protected void setHeader(CfetsTradeCollateralDialogueQuoteReceiveMessage message,
			IMockCfetsMessageBody messageBody) {
		MockCollateralDialogueQuoteResMessageBody mockbody = (MockCollateralDialogueQuoteResMessageBody) messageBody;
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
