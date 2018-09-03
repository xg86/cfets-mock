package com.xquant.platform.component.mock.builder.clickdeal;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessageBody;
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
public class MarketMakerMessageAddInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondMarketMakingQuoteResMessage> {

	@Override
	public CfetsTradeBondMarketMakingQuoteResMessage getReturnMessage(
			CfetsTradeBondMarketMakingQuoteResMessage messageTempldate,
			CfetsTradeBondMarketMakingQuoteResMessage originMessage) {
		CfetsTradeBondMarketMakingQuoteResMessage message = originMessage;
		CfetsTradeBondMarketMakingQuoteResMessageBody body = message.getBody();
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus(5);
		body.setErrorCode("0");
		// TODO 增加到期收益率的计算
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondMarketMakingQuoteResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(CfetsTradeBondMarketMakingQuoteResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondMarketMakingQuoteResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4SelfAdd(triggerTime);
		message.getBody().setQuoteID(generateParms.get(QUOTEID));
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondMarketMakingQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_MARKETMAKE_ADD;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondMarketMakingQuoteResMessage message) {
		fillWithCompute4MarketMaker(computeService, message.getBody());
	}
}
