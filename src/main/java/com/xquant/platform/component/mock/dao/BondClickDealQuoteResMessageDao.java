package com.xquant.platform.component.mock.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.cfets.MockBondClickDealQuoteResMessageBody;

@Repository
public interface BondClickDealQuoteResMessageDao {

	/**
	 * 根据消息内容进行消息的入库
	 * 
	 * @param message
	 * @return
	 */
	public int insert(MockBondClickDealQuoteResMessageBody message);

	public int updateStatus(@Param("quoteId") String quoteId, @Param("status") Integer status,
			@Param("transactTime") String transactTime, @Param("quoteTransType") String quoteTransType);

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
	public MockBondClickDealQuoteResMessageBody queryByQuoteId(@Param("quoteId") String quoteId);
	
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
	public List<MockBondClickDealQuoteResMessageBody> queryUnFinalQuoteAfterTime(@Param("formatTime") String formatTime);
}
