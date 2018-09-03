/**
 * 
 */
package com.xquant.platform.component.mock.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.cfets.MockCfetsTradePledgeBond;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dao
 * @author: guanglai.zhou
 * @date: 2018-08-18 17:16:52
 */
@Repository
public interface MockCfetsTradePledgeBondDao {

	public int insert(MockCfetsTradePledgeBond mockCfetsTradePledgeBond);
	
	public int delete(@Param("quoteId") String quoteId);

	public List<MockCfetsTradePledgeBond> queryByQuoteId(@Param("quoteId") String quoteId);

}
