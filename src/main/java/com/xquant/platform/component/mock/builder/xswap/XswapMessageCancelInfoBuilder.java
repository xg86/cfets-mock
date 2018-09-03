package com.xquant.platform.component.mock.builder.xswap;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartXswapMessageInfoBuilder;

import xquant.xswap.protocol.XSwapOrderActionResBody;
import xquant.xswap.protocol.XSwapOrderActionResMessage;

/**
 * 
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.xswap 
 * @author: guanglai.zhou   
 * @date: 2018-08-20 10:25:16
 * 利率互换报价撤销操作
 */
@Component
public class XswapMessageCancelInfoBuilder extends AbstartXswapMessageInfoBuilder<XSwapOrderActionResMessage> {

	@Override
	public Class<?> getSupprotClass() {
		return XSwapOrderActionResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.XSWAP_CANCEL;
	}

	@Override
	public XSwapOrderActionResMessage getReturnMessage(XSwapOrderActionResMessage messageTempldate,
			XSwapOrderActionResMessage originMessage) {
		XSwapOrderActionResMessage message = originMessage;
		XSwapOrderActionResBody body = message.getBody();
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus("3");
		return message;
	}

	@Override
	public Date resolveTriggerTime(XSwapOrderActionResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(XSwapOrderActionResMessage message) {
		return message.getBody().getOrderID();
	}


	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}


	@Override
	public void setMessageBodyWithGenerateValue(XSwapOrderActionResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4OtherOpbit(triggerTime);
		message.getBody().setSysReqID(generateParms.get(SYSREQID));
	}

}
