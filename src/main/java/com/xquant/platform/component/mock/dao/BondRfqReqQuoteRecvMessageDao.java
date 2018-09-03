package com.xquant.platform.component.mock.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.cfets.MockBondRFQReceiveMessageBody;

@Repository
public interface BondRfqReqQuoteRecvMessageDao {

	/**
	 * 根据消息内容进行消息的入库
	 * 
	 * @param message
	 * @return
	 */
	public int insert(MockBondRFQReceiveMessageBody message);

	/**
	 * 根据报价编号进行查询
	 * 
	 * @param quoteId
	 * @return
	 */
	public MockBondRFQReceiveMessageBody queryByQuoteId(@Param("quoteId") String quoteId);
}
