package com.xquant.platform.component.mock.service.clickdeal;

import java.util.List;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessage;
import com.xquant.platform.component.mock.dto.cfets.MockBondMarketMakingQuoteResMessageBody;

public interface BondMarketMakeMessageService {

	/**
	 * 本方新增报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean addMessage(CfetsTradeBondMarketMakingQuoteResMessage message);

	/**
	 * 本方撤销报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean cancelMessage(CfetsTradeBondMarketMakingQuoteResMessage message);

	/**
	 * 本方新增报价过期
	 * 
	 * @param message
	 * @return
	 */
	public boolean expireMessage(CfetsTradeBondMarketMakingQuoteResMessage message);

	/**
	 * 查询有效时间在当前时间之后的未成交的报价
	 *
	 * @return
	 */
	public List<MockBondMarketMakingQuoteResMessageBody> queryUnFinalValidQuote();
}
