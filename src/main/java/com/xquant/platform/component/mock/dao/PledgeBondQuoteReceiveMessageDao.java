package com.xquant.platform.component.mock.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.cfets.MockCollateralDialogueQuoteReceiveMessageBody;

@Repository
public interface PledgeBondQuoteReceiveMessageDao {

	/**
	 * 根据消息内容进行消息的入库
	 * @param message
	 * @return
	 */
	public int insert(MockCollateralDialogueQuoteReceiveMessageBody message);
	
	public MockCollateralDialogueQuoteReceiveMessageBody queryByQuoteId(@Param("quoteId") String quoteId);
	
}
