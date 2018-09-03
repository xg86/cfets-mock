package com.xquant.platform.component.mock.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyResMessageBody;

@Repository
public interface PledgeBondQuoteReplyResMessageDao {

	/**
	 * 查询报价
	 * 
	 * @return
	 */
	public CfetsTradeCollateralDialogueQuoteReplyResMessageBody queryByQuoteId(@Param("quoteId") String quoteId);

}
