package com.xquant.platform.component.mock.builder.bonddlg;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

@Component
public class BondDlgReceiveMessageExpireInfo
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondDialogueQuoteReceiveMessage> {

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondDialogueQuoteReceiveMessage message, Date triggerTime) {

	}

	@Override
	public CfetsTradeBondDialogueQuoteReceiveMessage getReturnMessage(
			CfetsTradeBondDialogueQuoteReceiveMessage messageTempldate,
			CfetsTradeBondDialogueQuoteReceiveMessage originMessage) {
		CfetsTradeBondDialogueQuoteReceiveMessage message = new CfetsTradeBondDialogueQuoteReceiveMessage();
		CfetsTradeBondDialogueQuoteReceiveMessageBody body = new CfetsTradeBondDialogueQuoteReceiveMessageBody();
		body.setQuoteID(originMessage.getBody().getQuoteID());
		body.setTransactTime(originMessage.getBody().getValidUntilTime());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setClientID(originMessage.getBody().getClientID());
		body.setClOrdIDClientID(originMessage.getBody().getClOrdIDClientID());
		body.setIsQuoteResponse(false);
		body.setQuoteType(originMessage.getBody().getQuoteType());
		body.setSecurityType(originMessage.getBody().getSecurityType());
		body.setMarketIndicator(originMessage.getBody().getMarketIndicator());
		body.setDeliveryType(originMessage.getBody().getDeliveryType());
		body.setClearingMethod(originMessage.getBody().getClearingMethod());
		body.setPartyID(originMessage.getBody().getPartyID());
		body.setPartyName(originMessage.getBody().getPartyName());
		body.setTraderID(originMessage.getBody().getTraderID());
		body.setTraderName(originMessage.getBody().getTraderName());
		body.setCounterPartyID(originMessage.getBody().getCounterPartyID());
		body.setCounterPartyName(originMessage.getBody().getCounterPartyName());
		body.setCounterTraderID(originMessage.getBody().getCounterTraderID());
		body.setCounterTraderName(originMessage.getBody().getCounterTraderName());
		body.setValidUntilTime(originMessage.getBody().getValidUntilTime());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondDialogueQuoteReceiveMessage message, Date triggerTime) {
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
	public String getQuoteId(CfetsTradeBondDialogueQuoteReceiveMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondDialogueQuoteReceiveMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_DIALOGUE_EXPIRED;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondDialogueQuoteReceiveMessage message) {
		
	}
}
