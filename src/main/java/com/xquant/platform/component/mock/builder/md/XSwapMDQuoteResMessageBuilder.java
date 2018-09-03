package com.xquant.platform.component.mock.builder.md;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.itf.cfets.xswap.api.enums.XSwapActionEnum;
import com.xquant.platform.component.mock.builder.IMockMessageInfoBuilder4Other;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.XswapMockMessageInfo;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockGenerateUtil;
import com.xquant.platform.component.mock.util.MockPropertiesUtil;

import xquant.xswap.protocol.XSwapHeader;
import xquant.xswap.protocol.XSwapMDQuoteReqBody;
import xquant.xswap.protocol.XSwapMDQuoteReqMessage;
import xquant.xswap.protocol.XSwapMDQuoteResBody;
import xquant.xswap.protocol.XSwapMDQuoteResMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.md
 * @author: guanglai.zhou
 * @date: 2018-08-18 12:31:47
 */
public class XSwapMDQuoteResMessageBuilder implements IMockMessageInfoBuilder4Other<XSwapMDQuoteReqMessage> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MockMessageInfo build(XSwapMDQuoteReqMessage originMessage) {
		XSwapMDQuoteReqBody reqBody = originMessage.getBody();
		XSwapHeader reqHeader = originMessage.getHeader();
		XSwapMDQuoteResMessage resMessage = new XSwapMDQuoteResMessage();
		XSwapHeader resHeader = new XSwapHeader();
		resHeader.setAction(reqHeader.getAction());
		resHeader.setClientID(reqHeader.getClientID());
		resHeader.setVersion(MockVersion.VERSION);
		resHeader.setSerialNo(MockGenerateUtil.getNextSerialNoByDate());
		resHeader.setMsgType(MsgTypeEnum.ACK.getValue());
		resHeader.setSendingTime(MockDateTimeUtil.getTransactimeOfNow());
		resHeader.setErrorCode("0");
		XSwapMDQuoteResBody resBody = new XSwapMDQuoteResBody();
		BeanUtils.copyProperties(reqBody, resBody);
		resBody.setSerialNo(reqHeader.getSerialNo());
		resBody.setStatus("3");
		resBody.setSysReqID(MockPropertiesUtil.getValueAndThenIncrease("sysReqID"));
		resMessage.setHeader(resHeader);
		resMessage.setBody(resBody);
		MockMessageInfo messageInfo = new XswapMockMessageInfo();
		messageInfo.setDelay(0L);
		messageInfo.setMessage(resMessage);
		messageInfo.setOptype(OpTypeEnum.XSWAP_MD_SUBSCRIPTION);
		messageInfo.setTargetSys(TargetSystemEnum.XSWAP);
		messageInfo.setTimeUnit(TimeUnit.MINUTES);
		return messageInfo;
	}

	@Override
	public String supportAction() {
		return XSwapActionEnum.MD_QUOTE_REQ.getValue();
	}

}
