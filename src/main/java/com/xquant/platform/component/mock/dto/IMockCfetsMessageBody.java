package com.xquant.platform.component.mock.dto;

import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dto.cfets
 * @author: guanglai.zhou
 * @date: 2018-08-22 11:48:05
 */
public interface IMockCfetsMessageBody {

	String COUNTER_CLORDID_SIGN = "CFETS-";
	
	QuoteBizTypeEnum getQuoteBizType();

	boolean isSelfQuote();
}
