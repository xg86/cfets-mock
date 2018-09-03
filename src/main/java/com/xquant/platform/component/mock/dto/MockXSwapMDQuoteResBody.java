/**
 * 
 */
package com.xquant.platform.component.mock.dto;

import xquant.xswap.protocol.XSwapMDQuoteResBody;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dto
 * @author: guanglai.zhou
 * @date: 2018-08-18 15:44:00
 */
@SuppressWarnings("serial")
public class MockXSwapMDQuoteResBody extends XSwapMDQuoteResBody {

	private String clientId;
	private String updateTime;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
