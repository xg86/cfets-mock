package com.xquant.platform.component.mock.builder.bonddlg;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessageBody;
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
public class BondDlgResMessageExpireInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondDialogueQuoteResMessage> {

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondDialogueQuoteResMessage message, Date triggerTime) {

	}

	@Override
	public CfetsTradeBondDialogueQuoteResMessage getReturnMessage(
			CfetsTradeBondDialogueQuoteResMessage messageTempldate,
			CfetsTradeBondDialogueQuoteResMessage originMessage) {
		CfetsTradeBondDialogueQuoteResMessage message = new CfetsTradeBondDialogueQuoteResMessage();
		CfetsTradeBondDialogueQuoteResMessageBody body = new CfetsTradeBondDialogueQuoteResMessageBody();
		body.setClOrdIDClientID(originMessage.getBody().getClOrdIDClientID());
		body.setQuoteID(originMessage.getBody().getQuoteID());
		body.setValidUntilTime(originMessage.getBody().getValidUntilTime());
		body.setTransactTime(originMessage.getBody().getValidUntilTime());
		body.setQuoteType(originMessage.getBody().getQuoteType());
		body.setSecurityType(originMessage.getBody().getSecurityType());
		body.setMarketIndicator(originMessage.getBody().getMarketIndicator());
		body.setPartyID(originMessage.getBody().getPartyID());
		body.setTraderID(originMessage.getBody().getTraderID());
		body.setCounterPartyID(originMessage.getBody().getCounterPartyID());
		body.setCounterTraderName(originMessage.getBody().getCounterPartyName());
		body.setCounterTraderID(originMessage.getBody().getCounterTraderID());
		body.setQid(originMessage.getBody().getQid());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setErrorCode("0");
		body.setPartyName(originMessage.getBody().getPartyName());
		body.setTraderName(originMessage.getBody().getTraderName());
		body.setCounterPartyName(originMessage.getBody().getCounterPartyName());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondDialogueQuoteResMessage message, Date triggerTime) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValidUntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValidUntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public String getQuoteId(CfetsTradeBondDialogueQuoteResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondDialogueQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_DIALOGUE_EXPIRED;
	}


	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondDialogueQuoteResMessage message) {
		
	}

}
