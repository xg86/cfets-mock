/**
 * 
 */
package com.xquant.platform.component.mock.dao;

import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.CfetsTradePushMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dao
 * @author: guanglai.zhou
 * @date: 2018-08-17 16:58:24
 */
@Repository
public interface CfetsTradePushMessageDao {

	/**
	 * 增加CfetsTradePushMessage数据
	 * 
	 * @param cfetsTradePushMessage
	 * @return
	 */
	public int insert(CfetsTradePushMessage cfetsTradePushMessage);
}
