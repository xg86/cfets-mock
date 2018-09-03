package com.xquant.platform.component.mock.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonCfetsTradeRfqMessageDao {

	/**
	 * 更新报价状态
	 * @param quoteId
	 * @param status
	 * @param transactTime
	 * @param quotetranstype
	 * @param sendOrRecvFlag
	 * @return
	 */
	public int updateStatus(@Param("quoteId") String quoteId, @Param("status") Integer status,
			@Param("transactTime") String transactTime, @Param("quotetranstype") String quotetranstype,
			@Param("sendOrRecvFlag") String sendOrRecvFlag);

	/**
	 * 删除报价
	 * 
	 * @param quoteId
	 * @return
	 */
	public int delete(@Param("quoteId") String quoteId);
}
