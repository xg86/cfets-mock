package com.xquant.platform.component.mock.dao;

import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.cfets.MockBondDialogueQuoteReceiveMessageBody;

@Repository
public interface BondDlgQuoteReceiveMessageDao{

	/**
	 * 根据消息内容进行消息的入库
	 * @param message
	 * @return
	 */
	public int insert(MockBondDialogueQuoteReceiveMessageBody message);
	
}
