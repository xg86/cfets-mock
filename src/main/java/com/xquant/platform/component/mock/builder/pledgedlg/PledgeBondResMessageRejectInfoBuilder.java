package com.xquant.platform.component.mock.builder.pledgedlg;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

/**
 * 本方新增报价
 * 
 * @author Administrator
 *
 */
@Component
public class PledgeBondResMessageRejectInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeCollateralDialogueQuoteResMessage> {

	@Override
	public CfetsTradeCollateralDialogueQuoteResMessage getReturnMessage(
			CfetsTradeCollateralDialogueQuoteResMessage messageTempldate,
			CfetsTradeCollateralDialogueQuoteResMessage originMessage) {
		CfetsTradeCollateralDialogueQuoteResMessage message = new CfetsTradeCollateralDialogueQuoteResMessage();
		CfetsTradeCollateralDialogueQuoteResMessageBody body = new CfetsTradeCollateralDialogueQuoteResMessageBody();
		CfetsTradeCollateralDialogueQuoteResMessageBody originBody = originMessage.getBody();
		body.setClOrdIDClientID(originBody.getClOrdIDClientID());
		body.setQuoteID(originBody.getQuoteID());
		body.setRepoMethod(originBody.getRepoMethod());
		body.setQuoteType(originBody.getQuoteType());
		body.setSecurityType(originBody.getSecurityType());
		body.setMarketIndicator(originBody.getMarketIndicator());
		body.setPartyID(originBody.getCounterPartyID());
		body.setPartyName(originBody.getCounterPartyName());
		body.setTraderID(originBody.getCounterTraderID());
		body.setTraderName(originBody.getCounterTraderName());
		body.setCounterPartyID(originBody.getPartyID());
		body.setCounterTraderID(originBody.getTraderID());
		body.setCounterTraderName(originBody.getTraderName());
		body.setCounterPartyName(originBody.getPartyName());
		body.setNegotiationCount(0);
		body.setStatus(QuoteStatusEnum.REJECTED.getValue());
		body.setErrorCode("0");
		body.setCashHoldingDays(0);
		body.setValidUntilTime(originBody.getValidUntilTime());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeCollateralDialogueQuoteResMessage message, Date triggerTime) {
		Assert.notNull(triggerTime, "triggerTime can not be null");
		return triggerTime;
	}

	@Override
	public String getQuoteId(CfetsTradeCollateralDialogueQuoteResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeCollateralDialogueQuoteResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4SelfAdd(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeCollateralDialogueQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.PLEDGEREPO_DIALOGUE_REJECT;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeCollateralDialogueQuoteResMessage message) {
	}

}
