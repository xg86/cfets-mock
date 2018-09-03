package com.xquant.platform.component.mock.dao;

import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.MockXSwapMDQuoteResBody;

/**   
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dao 
 * @author: guanglai.zhou   
 * @date: 2018-08-18 15:34:54
 */
@Repository
public interface XSwapMDQuoteResMessageDao {
	
	public int insert(MockXSwapMDQuoteResBody xSwapMDQuoteResBody);
}
