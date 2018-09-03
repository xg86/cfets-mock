/**
 * 
 */
package com.xquant.platform.component.mock.service.bondres;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondres
 * @author: guanglai.zhou
 * @date: 2018-07-23 20:00:18
 */
public interface BondRFQReplyReceiveMessageService {

	/**
	 * 对手新增请求回复报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean addByCounter(CfetsTradeBondRFQReplyReceiveMessage message);

	/**
	 * 对手修改请求回复报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean updateByCounter(CfetsTradeBondRFQReplyReceiveMessage message);

	/**
	 * 对手撤销请求回复报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean cancelByCounter(CfetsTradeBondRFQReplyReceiveMessage message);

	/**
	 * 对手请求回复报价过期
	 * 
	 * @param message
	 * @return
	 */
	public boolean expiredByCounter(CfetsTradeBondRFQReplyReceiveMessage message);
}
