package com.xquant.platform.component.mock.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.cfets.MockBondPriceLimitQuoteResMessageBody;

@Repository
public interface BondPriceLimitQuoteResMessageDao {

	/**
	 * 根据消息内容进行消息的入库
	 * 
	 * @param message
	 * @return
	 */
	public int insert(MockBondPriceLimitQuoteResMessageBody message);

	public int updateStatus(@Param("orderID") String orderID, @Param("status") Integer status,
			@Param("transactTime") String transactTime, @Param("quoteTransType") String quoteTransType);
	/**
	 * 删除报价
	 * 
	 * @param orderID
	 * @return
	 */
	public int delete(@Param("orderID") String orderID);

	/**
	 * 根据报价编号进行查询
	 * 
	 * @param orderID
	 * @return
	 */
	public MockBondPriceLimitQuoteResMessageBody queryByQuoteId(@Param("orderID") String orderID);
	
	
	/**
	 * 查询指定时间之后非终态的报价数量
	 *
	 * @param validateTime
	 */
	public int countUnFinalQuoteAfterTime(@Param("formatTime") String formatTime);

	/**
	 * 查询指定时间之后非终态的报价
	 * 
	 * @param validateTime
	 * @return
	 */
	public List<MockBondPriceLimitQuoteResMessageBody> queryUnFinalQuoteAfterTime(@Param("formatTime") String formatTime);
}
