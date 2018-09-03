/**
 * 
 */
package com.xquant.platform.component.mock.builder.expire;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.OpBitEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.OpCategoryEnum;
import com.xquant.platform.component.mock.builder.IMockMessageInfoBuilder4Expired;
import com.xquant.platform.component.mock.dto.CfetsMockMessageInfo;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.expire
 * @author: guanglai.zhou
 * @date: 2018-08-22 16:25:04
 */
public abstract class AbstractMockMessageInfoBuilder4Expired<T> implements IMockMessageInfoBuilder4Expired {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MockMessageInfo build(IMockCfetsMessageBody messageBody) {
		MockMessageInfo mockMessageInfo = new CfetsMockMessageInfo();
		T message = getReturnMessage(messageBody);
		Date triggerTime = resolveTriggerTime(message);
		Long delay = resolveDelay(new Date(), triggerTime, getTimeUnit());
		setHeader(message, messageBody);
		mockMessageInfo.setDelay(delay);
		mockMessageInfo.setMessage(message);
		mockMessageInfo.setOptype(getOptype());
		mockMessageInfo.setQuoteId(getQuoteId(message));
		mockMessageInfo.setTargetSys(TargetSystemEnum.CFETS);
		mockMessageInfo.setTimeUnit(getTimeUnit());
		return mockMessageInfo;
	}

	public OpTypeEnum getOptype() {
		String value = OpCategoryEnum.QUOTE.getValue().concat(supportQuote().getValue())
				.concat(OpBitEnum.EXPIRE.getValue());
		return OpTypeEnum.getOpTypeEnum(value);
	}

	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	public TimeUnit getTimeUnit() {
		return TimeUnit.SECONDS;
	}

	protected abstract void setHeader(T message, IMockCfetsMessageBody messageBody);

	protected abstract T getReturnMessage(IMockCfetsMessageBody messageBody);

	protected abstract Date resolveTriggerTime(T message);

	protected abstract String getQuoteId(T message);

}
