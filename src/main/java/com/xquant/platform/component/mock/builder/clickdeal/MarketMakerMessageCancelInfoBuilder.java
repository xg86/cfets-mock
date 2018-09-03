package com.xquant.platform.component.mock.builder.clickdeal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.common.CfetsTradeBondLeg;
import com.xquant.cfets.trade.protocol.common.CfetsTradeBondLegList;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.skeleton.enums.quote.SideEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;

@Component
public class MarketMakerMessageCancelInfoBuilder
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
		CfetsTradeBondLegList bondLegList = new CfetsTradeBondLegList();
		ArrayList<CfetsTradeBondLeg> legList = new ArrayList<CfetsTradeBondLeg>();
		CfetsTradeBondLeg buyPart = new CfetsTradeBondLeg();
		buyPart.setLegSide(SideEnum.PURCHASE.getValue());
		buyPart.setLegSecurityID(body.getSecurityID());
		legList.add(buyPart);
		CfetsTradeBondLeg sellPart = new CfetsTradeBondLeg();
		sellPart.setLegSide(SideEnum.SELL.getValue());
		sellPart.setLegSecurityID(body.getSecurityID());
		legList.add(sellPart);
		bondLegList.setBondLegList(legList);
		body.setBondLegList(bondLegList);
		body.setErrorCode("0");
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
		Map<String, String> generateParms = generateParms4OtherOpbit(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));

	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondMarketMakingQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_MARKETMAKE_CANCEL;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondMarketMakingQuoteResMessage message) {
		
	}
}
