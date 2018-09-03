/**
 * 
 */
package com.xquant.platform.component.mock.service.bondrfq;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondrfq
 * @author: guanglai.zhou
 * @date: 2018-07-23 17:15:49
 */
public interface BondRFQReceiveMessageService {

	/**
	 * 对手新增请求报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean addMessageByCounter(CfetsTradeBondRFQReceiveMessage message);

	/**
	 * 对手撤销请求报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean cancelMessageByCounter(CfetsTradeBondRFQReceiveMessage message);

	/**
	 * 对手请求报价过期
	 * 
	 * @param message
	 * @return
	 */
	public boolean expiredMessageByCounter(CfetsTradeBondRFQReceiveMessage message);

	/**
	 * 对手请求报价全部被成交
	 * 
	 * @param message
	 * @return
	 */
	public boolean allConfirmedMessage(CfetsTradeBondRFQReceiveMessage message);

	/**
	 * 对手请求报价全部被拒绝
	 * 
	 * @param message
	 * @return
	 */
	public boolean allRejectedMessage(CfetsTradeBondRFQReceiveMessage message);
}
