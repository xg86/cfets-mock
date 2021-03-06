package com.xquant.platform.component.mock.service.pledgebond;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessage;

public interface PledgeBondReceiveMessageService {

	  /**
	   * 对手新增报价
	   * @param message
	   * @return
	   */
	  public boolean addMessage(CfetsTradeCollateralDialogueQuoteReceiveMessage message);
	  
	  /**
	   * 对手修改报价
	   * @param message
	   * @return
	   */
	  public boolean updateMessage(CfetsTradeCollateralDialogueQuoteReceiveMessage message);
	  
	  /**
	   * 对手撤销报价
	   * @param message
	   * @return
	   */
	  public boolean cancelMessage(CfetsTradeCollateralDialogueQuoteReceiveMessage message);
	  
	  /**
	   * 对手新增报价过期
	   * @param message
	   * @return
	   */
	  public boolean expireMessage(CfetsTradeCollateralDialogueQuoteReceiveMessage message);
	  
	  /**
	   * 对手客户端修改本方报价
	   * @param message
	   * @return
	   */
	  public boolean updateByCounterClient(CfetsTradeCollateralDialogueQuoteReceiveMessage message);
	  
}
