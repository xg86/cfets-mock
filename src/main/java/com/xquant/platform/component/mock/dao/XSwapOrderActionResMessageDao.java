package com.xquant.platform.component.mock.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import xquant.xswap.protocol.XSwapOrderActionResBody;

@Repository
public interface XSwapOrderActionResMessageDao {

	/**
	 * 根据消息内容进行消息的入库
	 * 
	 * @param message
	 * @return
	 */
	public int insert(XSwapOrderActionResBody message);

	/**
	 * 更新报价的状态
	 * @param clientId
	 * @param quoteId
	 * @param status
	 * @param transactTime
	 * @return
	 */
	public int updateStatus(@Param("clientId") String clientId, @Param("quoteId") String quoteId,
			@Param("status") Integer status, @Param("transactTime") String transactTime);

	/**
	 * 删除报价
	 * 
	 * @param quoteId
	 * @return
	 */
	public int delete(@Param("quoteId") String quoteId);

	/**
	 * 根据报价编号进行查询
	 * 
	 * @param quoteId
	 * @return
	 */
	public XSwapOrderActionResBody queryByQuoteId(@Param("quoteId") String quoteId);
}
