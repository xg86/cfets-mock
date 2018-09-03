package com.xquant.platform.component.mock.builder.clickdeal;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessageBody;
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
public class PriceLimitMessageAddInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondPriceLimitQuoteResMessage> {

	@Override
	public CfetsTradeBondPriceLimitQuoteResMessage getReturnMessage(
			CfetsTradeBondPriceLimitQuoteResMessage messageTempldate,
			CfetsTradeBondPriceLimitQuoteResMessage originMessage) {
		CfetsTradeBondPriceLimitQuoteResMessage message = originMessage;
		CfetsTradeBondPriceLimitQuoteResMessageBody body = message.getBody();
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus(5);
		body.setErrorCode("0");
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondPriceLimitQuoteResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(CfetsTradeBondPriceLimitQuoteResMessage message) {
		return message.getBody().getOrderID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondPriceLimitQuoteResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4SelfAdd(triggerTime);
		message.getBody().setOrderID(generateParms.get(QUOTEID));
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondPriceLimitQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_PRICELIMIT_ADD;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondPriceLimitQuoteResMessage message) {
		fillWithCompute4PriceLimit(computeService, message.getBody());
	}
}
