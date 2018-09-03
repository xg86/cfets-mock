package com.xquant.platform.component.mock.builder.clickdeal;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;

/**
 * 本方新增报价
 * 
 * @author Administrator
 *
 */
@Component
public class ClickDealMessageAddInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondClickDealQuoteResMessage> {

	@Override
	public CfetsTradeBondClickDealQuoteResMessage getReturnMessage(
			CfetsTradeBondClickDealQuoteResMessage messageTempldate,
			CfetsTradeBondClickDealQuoteResMessage originMessage) {
		CfetsTradeBondClickDealQuoteResMessage message = originMessage;
		CfetsTradeBondClickDealQuoteResMessageBody body = message.getBody();
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus(5);
		body.setErrorCode("0");
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondClickDealQuoteResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(CfetsTradeBondClickDealQuoteResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondClickDealQuoteResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4SelfAdd(triggerTime);
		message.getBody().setQuoteID(generateParms.get(QUOTEID));
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondClickDealQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_CLICKDEAL_ADD;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondClickDealQuoteResMessage message) {
		fillWithCompute4ClickDeal(computeService, message.getBody());
	}
}
