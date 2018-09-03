package com.xquant.platform.component.mock.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.cfets.MockBondRFQReplyReceiveMessageBody;

@Repository
public interface BondRfqResQuoteRecvMessageDao {

	/**
	 * 根据消息内容进行消息的入库
	 * 
	 * @param message
	 * @return
	 */
	public int insert(MockBondRFQReplyReceiveMessageBody message);

	/**
	 * 根据报价编号进行查询
	 * 
	 * @param quoteId
	 * @return
	 */
	public MockBondRFQReplyReceiveMessageBody queryByQuoteId(@Param("quoteId") String quoteId);
}
