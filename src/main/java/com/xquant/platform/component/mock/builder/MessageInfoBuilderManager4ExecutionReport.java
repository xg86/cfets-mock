package com.xquant.platform.component.mock.builder;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.dto.MockMessageInfo;

@Component
public class MessageInfoBuilderManager4ExecutionReport {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("rawtypes")
	@Autowired
	private List<IMockMessageInfoBuilder4ExcutionReport> mockMessageInfoBuilders;

	/**
	 * 根据模板 原报价信息 报价类型 触发时间等构造一个mockmessageinfo对象
	 * 
	 * @param messageTempldate
	 *            模板
	 * @param originMessage
	 *            原报价信息
	 * @param optype
	 *            报价类型
	 * @param triggerTime
	 *            触发时间
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MockMessageInfo build(Object messageTempldate, Object originMessage, OpTypeEnum optype, Date triggerTime) {
		try {
			checkBeforeBuild(messageTempldate, originMessage, optype);
			IMockMessageInfoBuilder4ExcutionReport builder = getSupportBuilder(messageTempldate.getClass(), optype);
			MockMessageInfo mockMessageInfo = builder.build(messageTempldate, originMessage, triggerTime, optype);
			checkBeforeReturn(mockMessageInfo);
			return mockMessageInfo;
		} catch (Exception e) {
			logger.error("MessagePersistManagerDelegate Exception ", e);
			throw e;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private IMockMessageInfoBuilder4ExcutionReport getSupportBuilder(Class<?> type, OpTypeEnum optype) {
		for (IMockMessageInfoBuilder4ExcutionReport iMockMessageInfoBuilder : mockMessageInfoBuilders) {
			if (iMockMessageInfoBuilder.matches(type, optype)) {
				return iMockMessageInfoBuilder;
			}
		}
		throw new RuntimeException(
				"根据当前信息无法获取Object的构造器,对应的报价对象类型和报价类型为 " + type.getSimpleName() + ",+opType = " + optype);
	}

	/**
	 * @param messageTempldate
	 * @param originMessage
	 */
	private void checkBeforeBuild(Object messageTempldate, Object originMessage, OpTypeEnum optype) {
		Assert.notNull(messageTempldate, "messageTempldate can not be null ");
		Assert.notNull(originMessage, "originMessage can not be null ");
		Assert.notNull(optype, "optype can not be null ");
	}

	/**
	 * 用于检查返回时各参数必须存在
	 * 
	 * @param mockMessageInfo
	 */
	@SuppressWarnings("rawtypes")
	private void checkBeforeReturn(MockMessageInfo mockMessageInfo) {
		Assert.notNull(mockMessageInfo, "mockMessageInfo can not be null");
		Assert.notNull(mockMessageInfo.getMessage(), "message in mockMessageInfo can not be null");
		Assert.notNull(mockMessageInfo.getDelay(), "delay in mockMessageInfo can not be null");
		Assert.notNull(mockMessageInfo.getOptype(), "optype in mockMessageInfo can not be null");
		Assert.notNull(mockMessageInfo.getQuoteId(), "quoteid in mockMessageInfo can not be null");
		Assert.notNull(mockMessageInfo.getTimeUnit(), "timeunit in mockMessageInfo can not be null");
	}

}
