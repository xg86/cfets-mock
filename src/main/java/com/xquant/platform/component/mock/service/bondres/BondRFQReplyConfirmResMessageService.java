package com.xquant.platform.component.mock.service.bondres;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyConfirmResMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.service.bondres
 * @author: guanglai.zhou
 * @date: 2018-07-23 19:58:37
 */
public interface BondRFQReplyConfirmResMessageService {

	/**
	 * 本方成交请求回复报价
	 * @param message
	 * @return
	 */
	public boolean confirmBySelf(CfetsTradeBondRFQReplyConfirmResMessage message);

}
