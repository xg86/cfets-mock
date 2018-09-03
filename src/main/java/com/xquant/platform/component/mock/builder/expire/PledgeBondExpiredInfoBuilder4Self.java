/**
 * 
 */
package com.xquant.platform.component.mock.builder.expire;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
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
public class PledgeBondExpiredInfoBuilder4Self
		extends AbstractMockMessageInfoBuilder4Expired<CfetsTradeCollateralDialogueQuoteResMessage> {

	@Override
	public QuoteBizTypeEnum supportQuote() {
		return QuoteBizTypeEnum.PLEDGEREPO_DLG;
	}

	@Override
	public boolean isSelfQuote() {
		return true;
	}

	@Override
	public CfetsTradeCollateralDialogueQuoteResMessage getReturnMessage(IMockCfetsMessageBody messageBody) {
		CfetsTradeCollateralDialogueQuoteResMessage message = new CfetsTradeCollateralDialogueQuoteResMessage();
		CfetsTradeCollateralDialogueQuoteResMessageBody body = new CfetsTradeCollateralDialogueQuoteResMessageBody();
		MockCollateralDialogueQuoteResMessageBody mockbody = (MockCollateralDialogueQuoteResMessageBody) messageBody;
		body.setClOrdIDClientID(mockbody.getClOrdIDClientID());
		body.setQuoteID(mockbody.getQuoteID());
		body.setTransactTime(mockbody.getValidUntilTime());
		body.setValidUntilTime(mockbody.getValidUntilTime());
		body.setQuoteType(mockbody.getQuoteType());
		body.setRepoMethod(mockbody.getRepoMethod());
		body.setSecurityType(mockbody.getSecurityType());
		body.setMarketIndicator(mockbody.getMarketIndicator());
		body.setPartyID(mockbody.getPartyID());
		body.setTraderID(mockbody.getTraderID());
		body.setCounterPartyID(mockbody.getCounterPartyID());
		body.setCounterTraderName(mockbody.getCounterPartyName());
		body.setCounterTraderID(mockbody.getCounterTraderID());
		body.setQid(mockbody.getQid());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setErrorCode("0");
		body.setPartyName(mockbody.getPartyName());
		body.setTraderName(mockbody.getTraderName());
		body.setCounterPartyName(mockbody.getCounterPartyName());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeCollateralDialogueQuoteResMessage message) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValidUntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValidUntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public String getQuoteId(CfetsTradeCollateralDialogueQuoteResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	protected void setHeader(CfetsTradeCollateralDialogueQuoteResMessage message, IMockCfetsMessageBody messageBody) {
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
