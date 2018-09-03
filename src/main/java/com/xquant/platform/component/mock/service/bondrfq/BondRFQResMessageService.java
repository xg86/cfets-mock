/**
 * 
 */
package com.xquant.platform.component.mock.service.bondrfq;

import java.util.List;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessage;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQResMessageBody;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondrfq
 * @author: guanglai.zhou
 * @date: 2018-07-23 17:07:35
 */
public interface BondRFQResMessageService {

	/**
	 * 本方新增请求报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean addMessageBySelf(CfetsTradeBondRFQResMessage message);

	/**
	 * 本方撤销请求报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean cancelMessageBySelf(CfetsTradeBondRFQResMessage message);

	/**
	 * 本方请求报价过期
	 * 
	 * @param message
	 * @return
	 */
	public boolean expireMessageBySelf(CfetsTradeBondRFQResMessage message);

	/**
	 * 本方请求报价全部被成交
	 * 
	 * @param message
	 * @return
	 */
	public boolean allConfirmedMessage(CfetsTradeBondRFQResMessage message);

	/**
	 * 本方请求报价全部被拒绝
	 * 
	 * @param message
	 * @return
	 */
	public boolean allRejectedMessage(CfetsTradeBondRFQResMessage message);
	
	
    /**
     * 查询有效时间在当前时间之后的未成交的报价
     *
     * @return
     */
    public List<MockBondRFQResMessageBody> queryUnFinalValidQuote();
}
