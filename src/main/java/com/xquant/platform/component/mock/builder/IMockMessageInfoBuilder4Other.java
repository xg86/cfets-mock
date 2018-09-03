package com.xquant.platform.component.mock.builder;

import com.xquant.platform.component.mock.dto.MockMessageInfo;

public interface IMockMessageInfoBuilder4Other<T> {

	/**
	 * 根据原报价构造新的报价信息
	 * @param originMessage
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	MockMessageInfo build(T originMessage);
	/**
	 * 支持动作编号
	 * @return
	 */
	String supportAction();
}
