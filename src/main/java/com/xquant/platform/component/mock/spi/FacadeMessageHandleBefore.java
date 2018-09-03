/**
 * 
 */
package com.xquant.platform.component.mock.spi;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;

/**   
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.spi 
 * @author: guanglai.zhou   
 * @date: 2018-07-25 15:43:27
 * 通过实现该类可以在进行消息处理之前做一个工作 比如设置quoteId clordIdClientId serialNo等参数 防止冲突
 */
public interface FacadeMessageHandleBefore {

	  /**
	   * 返回处理前的CfetsTradeMessage对象
	   * @return
	   */
	  public CfetsTradeMessage handleBefore(CfetsTradeMessage message);
}
