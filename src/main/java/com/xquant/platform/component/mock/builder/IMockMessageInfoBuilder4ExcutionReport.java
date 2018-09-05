package com.xquant.platform.component.mock.builder;

import java.util.Date;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.dto.MockMessageInfo;

public interface IMockMessageInfoBuilder4ExcutionReport<Q, T> {

	/**
	 * 根据报价对象类型以及报价类型 动作类型进行构造器的选择
	 * 
	 * @param cls
	 *            报价对象类型
	 * @param quoteBizType
	 *            报价类型
	 * @param opbit
	 *            动作类型
	 * @return 如果匹配成功 则返回true 否则返回false
	 */
	boolean matches(Class<?> cls, OpTypeEnum optype);

	/**
	 * 根据各种参数构造一个消息对象
	 * 
	 * @param messageTempldate
	 *            原消息模板
	 * @param originMessage
	 *            原报价消息 只有在接收客户端报价时才会有
	 * @param triggerTime
	 *            本报价发送给客户端的时间
	 * @param quoteBizType
	 *            报价类型
	 * @param opbit
	 *            动作类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	MockMessageInfo build(Q messageTempldate, T originMessage, Date triggerTime, OpTypeEnum optype);
}
