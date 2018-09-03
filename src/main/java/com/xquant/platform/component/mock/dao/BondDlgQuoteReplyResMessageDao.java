package com.xquant.platform.component.mock.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReplyResMessageBody;

@Repository
public interface BondDlgQuoteReplyResMessageDao {

	/**
	 * 查询报价
	 * 
	 * @return
	 */
	public CfetsTradeBondDialogueQuoteReplyResMessageBody queryByQuoteId(@Param("quoteId") String quoteId);

}
