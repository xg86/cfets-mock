package com.xquant.platform.component.mock.builder.bondrfq;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.bondrfq
 * @author: guanglai.zhou
 * @date: 2018-08-20 08:22:40 本方撤销请求报价
 */
@Component
public class BondRfqResMessageCancelInfoBuilder extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondRFQResMessage> {

	@Override
	public CfetsTradeBondRFQResMessage getReturnMessage(CfetsTradeBondRFQResMessage messageTempldate,
			CfetsTradeBondRFQResMessage originMessage) {
		CfetsTradeBondRFQResMessage message = originMessage;
		CfetsTradeBondRFQResMessageBody body = message.getBody();
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus(5);
		body.setErrorCode("0");
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(CfetsTradeBondRFQResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondRFQResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4OtherOpbit(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondRFQResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_RFQREQ_CANCEL;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondRFQResMessage message) {

	}

}
