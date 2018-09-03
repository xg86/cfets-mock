package com.xquant.platform.component.mock.builder.xswap;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartXswapMessageInfoBuilder;

import xquant.xswap.protocol.XSwapOrderResBody;
import xquant.xswap.protocol.XSwapOrderResMessage;

/**
 * 
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.xswap
 * @author: guanglai.zhou
 * @date: 2018-08-20 10:25:38 利率互换报价新增操作
 */
@Component
public class XswapMessageAddInfoBuilder extends AbstartXswapMessageInfoBuilder<XSwapOrderResMessage> {

	@Override
	public Class<?> getSupprotClass() {
		return XSwapOrderResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.XSWAP_ADD;
	}

	@Override
	public XSwapOrderResMessage getReturnMessage(XSwapOrderResMessage messageTempldate,
			XSwapOrderResMessage originMessage) {
		XSwapOrderResMessage message = originMessage;
		XSwapOrderResBody body = message.getBody();
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus("3");
		body.setErrorCode("0");
		return message;
	}

	@Override
	public Date resolveTriggerTime(XSwapOrderResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(XSwapOrderResMessage message) {
		return message.getBody().getFb_OrderID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public void setMessageBodyWithGenerateValue(XSwapOrderResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4SelfAdd(new Date());
		message.getBody().setFb_OrderID(generateParms.get(QUOTEID));
		message.getBody().setFb_TransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setOrderRequestID(generateParms.get(ORDERREQUESTID));
		message.getBody().setFb_OperateSeqNum(generateParms.get(OPERATESEQNUM));
		message.getBody().setSysReqID(generateParms.get(SYSREQID));
	}

}
