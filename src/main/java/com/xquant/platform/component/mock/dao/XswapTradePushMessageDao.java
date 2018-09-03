package com.xquant.platform.component.mock.dao;

import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.XswapTradePushMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dao
 * @author: guanglai.zhou
 * @date: 2018-08-17 16:58:24
 */
@Repository
public interface XswapTradePushMessageDao {

	public int insert(XswapTradePushMessage xswapTradePushMessage);
}
