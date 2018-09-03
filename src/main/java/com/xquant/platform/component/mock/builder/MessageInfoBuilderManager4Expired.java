/**
 * 
 */
package com.xquant.platform.component.mock.builder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;
import com.xquant.platform.component.mock.dto.MockMessageInfo;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder
 * @author: guanglai.zhou
 * @date: 2018-08-22 19:56:30
 */
@Component
public class MessageInfoBuilderManager4Expired {

	@Autowired
	private List<IMockMessageInfoBuilder4Expired> builders;

	@SuppressWarnings("rawtypes")
	public MockMessageInfo build(IMockCfetsMessageBody messageBody) {
		return getUsedBuilder(messageBody).build(messageBody);
	}

	private IMockMessageInfoBuilder4Expired getUsedBuilder(IMockCfetsMessageBody messageBody) {
		for (IMockMessageInfoBuilder4Expired builder : builders) {
			if (builder.supportQuote().equals(messageBody.getQuoteBizType())
					&& (builder.isSelfQuote() == messageBody.isSelfQuote())) {
				return builder;
			}
		}
		throw new RuntimeException("根据消息主题对象无法获取过期消息构造器,对应消息的类型为 " + messageBody.getQuoteBizType() + ",是否为本方的报价 = "
				+ messageBody.isSelfQuote());
	}
}
