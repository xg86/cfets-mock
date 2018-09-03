package com.xquant.platform.component.mock.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.MyCfetsTradeCounterParty;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQResMessageBody;

@Repository
public interface BondRfqReqQuoteResMessageDao {

	/**
	 * 根据消息内容进行消息的入库
	 * 
	 * @param message
	 * @return
	 */
	public int insert(MockBondRFQResMessageBody message);

	/**
	 * 增加交易对手
	 * 
	 * @param myCfetsTradeCounterParty
	 * @return
	 */
	public int insertParties(MyCfetsTradeCounterParty myCfetsTradeCounterParty);

	/**
	 * 根据报价编号进行查询
	 * 
	 * @param quoteId
	 * @return
	 */
	public MockBondRFQResMessageBody queryByQuoteId(@Param("quoteId") String quoteId);

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
	public List<MockBondRFQResMessageBody> queryUnFinalQuoteAfterTime(@Param("formatTime") String formatTime);
}
