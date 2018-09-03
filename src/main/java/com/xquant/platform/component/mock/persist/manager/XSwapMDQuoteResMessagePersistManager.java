/**
 * 
 */
package com.xquant.platform.component.mock.persist.manager;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.platform.component.mock.dao.XSwapMDQuoteResMessageDao;
import com.xquant.platform.component.mock.dto.MockXSwapMDQuoteResBody;
import com.xquant.platform.component.mock.persist.IMessagePersistServiceManager;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

import xquant.xswap.protocol.XSwapMDQuoteResMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.persist.manager
 * @author: guanglai.zhou
 * @date: 2018-08-18 15:31:06
 */
@Component
public class XSwapMDQuoteResMessagePersistManager implements IMessagePersistServiceManager<XSwapMDQuoteResMessage> {

	@Autowired
	private XSwapMDQuoteResMessageDao xSwapMDQuoteResMessageDao;

	@Override
	public boolean handle(XSwapMDQuoteResMessage object) {
		
		MockXSwapMDQuoteResBody newBody = new MockXSwapMDQuoteResBody();
		BeanUtils.copyProperties(object.getBody(), newBody);
		newBody.setClientId(object.getHeader().getClientID());
		newBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		return xSwapMDQuoteResMessageDao.insert(newBody) == 1;
	}

	@Override
	public boolean support(Class<?> cls) {
		return XSwapMDQuoteResMessage.class.equals(cls);
	}

}
