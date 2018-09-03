/**
 * 
 */
package com.xquant.platform.component.mock.service.bondrfq;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondrfq
 * @author: guanglai.zhou
 * @date: 2018-07-23 17:07:35
 */
public interface BondRFQRejectResMessageService {

	/**
	 * 本方拒绝请求报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean rejectMessageBySelf(CfetsTradeBondRFQRejectResMessage message);

}
