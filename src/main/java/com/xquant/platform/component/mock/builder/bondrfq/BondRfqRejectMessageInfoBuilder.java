package com.xquant.platform.component.mock.builder.bondrfq;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;

/**
 * 
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.bondrfq
 * @author: guanglai.zhou
 * @date: 2018-08-20 08:30:34 本方拒绝请求报价
 */
@Component
public class BondRfqRejectMessageInfoBuilder extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondRFQRejectResMessage> {

	@Override
	public CfetsTradeBondRFQRejectResMessage getReturnMessage(CfetsTradeBondRFQRejectResMessage messageTempldate,
			CfetsTradeBondRFQRejectResMessage originMessage) {
		CfetsTradeBondRFQRejectResMessage message = originMessage;
		CfetsTradeBondRFQRejectResMessageBody body = message.getBody();
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus(5);
		body.setErrorCode("0");
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQRejectResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(CfetsTradeBondRFQRejectResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondRFQRejectResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4OtherOpbit(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondRFQRejectResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_RFQREQ_REJECT;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondRFQRejectResMessage message) {

	}

}
