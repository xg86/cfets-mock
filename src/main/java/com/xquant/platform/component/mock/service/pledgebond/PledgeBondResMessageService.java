package com.xquant.platform.component.mock.service.pledgebond;

import java.util.List;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.platform.component.mock.dto.cfets.MockCollateralDialogueQuoteResMessageBody;

public interface PledgeBondResMessageService {

	/**
	 * 本方新增报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean addMessage(CfetsTradeCollateralDialogueQuoteResMessage message);

	/**
	 * 本方修改报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean updateMessage(CfetsTradeCollateralDialogueQuoteResMessage message);

	/**
	 * 本方撤销报价
	 * 
	 * @param message
	 * @return
	 */
	public boolean cancelMessage(CfetsTradeCollateralDialogueQuoteResMessage message);

	/**
	 * 本方新增报价过期
	 * 
	 * @param message
	 * @return
	 */
	public boolean expireMessage(CfetsTradeCollateralDialogueQuoteResMessage message);

	/**
	 * 对手成交
	 * 
	 * @param message
	 * @return
	 */
	public boolean confirmMessage(CfetsTradeCollateralDialogueQuoteResMessage message);

	/**
	 * 对手拒绝
	 * 
	 * @param message
	 * @return
	 */
	public boolean rejectMessage(CfetsTradeCollateralDialogueQuoteResMessage message);

	/**
	 * 查询有效时间在当前时间之后的未成交的报价
	 *
	 * @return
	 */
	public List<MockCollateralDialogueQuoteResMessageBody> queryUnFinalValidQuote();

}
