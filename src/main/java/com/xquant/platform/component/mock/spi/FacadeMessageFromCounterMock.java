package com.xquant.platform.component.mock.spi;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.spi
 * @author: guanglai.zhou
 * @date: 2018-07-25 15:34:23
 */
public interface FacadeMessageFromCounterMock {

	/**
	 * 外部输入报价消息
	 * 
	 * @param xmlMessage
	 */
	public void handleMessage(String xmlMessage);
}
