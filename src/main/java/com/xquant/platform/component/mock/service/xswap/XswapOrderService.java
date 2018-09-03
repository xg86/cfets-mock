package com.xquant.platform.component.mock.service.xswap;

import xquant.xswap.protocol.XSwapOrderActionResMessage;
import xquant.xswap.protocol.XSwapOrderResMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.xswap
 * @author: guanglai.zhou
 * @date: 2018-07-24 16:58:30
 */
public interface XswapOrderService {

	/**
	 * 本方新增利率互换报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean addMessage(XSwapOrderResMessage message);

	/**
	 * 本方撤销利率互换报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean cancelMessage(XSwapOrderActionResMessage message);
}
