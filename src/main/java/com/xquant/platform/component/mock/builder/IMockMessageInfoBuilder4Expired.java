package com.xquant.platform.component.mock.builder;

import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;
import com.xquant.platform.component.mock.dto.MockMessageInfo;

public interface IMockMessageInfoBuilder4Expired {

	/**
	 * 根据body信息构造对应的过期报价信息
	 * 
	 * @param messageBody
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	MockMessageInfo build(IMockCfetsMessageBody messageBody);

	/**
	 * 支持的报价类型
	 * 
	 * @return
	 */
	QuoteBizTypeEnum supportQuote();

	/**
	 * 是否本方报价
	 * 
	 * @return
	 */
	boolean isSelfQuote();
}
