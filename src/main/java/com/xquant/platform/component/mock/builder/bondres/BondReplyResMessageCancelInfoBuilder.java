package com.xquant.platform.component.mock.builder.bondres;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;

/**
 * 
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.bondres 
 * @author: guanglai.zhou   
 * @date: 2018-08-20 08:58:30
 * 本方撤销请求回复报价
 */
@Component
public class BondReplyResMessageCancelInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondRFQReplyResMessage> {

	@Override
	public CfetsTradeBondRFQReplyResMessage getReturnMessage(CfetsTradeBondRFQReplyResMessage messageTempldate,
			CfetsTradeBondRFQReplyResMessage originMessage) {
		CfetsTradeBondRFQReplyResMessage message = originMessage;
		CfetsTradeBondRFQReplyResMessageBody body = message.getBody();
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus(5);
		body.setErrorCode("0");
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQReplyResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(CfetsTradeBondRFQReplyResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondRFQReplyResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4OtherOpbit(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondRFQReplyResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_RFQREPLY_CANCEL;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondRFQReplyResMessage message) {
	}

}
