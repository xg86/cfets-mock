/**
 * 
 */
package com.xquant.platform.component.mock.service.bondres;

import java.util.List;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessage;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQReplyResMessageBody;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondres
 * @author: guanglai.zhou
 * @date: 2018-07-23 19:54:59
 */
public interface BondRFQReplyResMessageService {

	/**
	 * 本方新增请求回复报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean addBySelf(CfetsTradeBondRFQReplyResMessage message);

	/**
	 * 本方修改请求回复报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean updateBySelf(CfetsTradeBondRFQReplyResMessage message);

	/**
	 * 本方撤销请求回复报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean cancelBySelf(CfetsTradeBondRFQReplyResMessage message);

	/**
	 * 本方请求回复报价过期
	 * 
	 * @param message
	 * @return
	 */
	public boolean expiredBySelf(CfetsTradeBondRFQReplyResMessage message);

	/**
	 * 对手成交请求回复报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean confirmByCounter(CfetsTradeBondRFQReplyResMessage message);
	
    /**
     * 查询有效时间在当前时间之后的未成交的报价
     *
     * @return
     */
    public List<MockBondRFQReplyResMessageBody> queryUnFinalValidQuote();
}
