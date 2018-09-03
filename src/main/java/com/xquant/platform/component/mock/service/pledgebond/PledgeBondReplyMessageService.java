package com.xquant.platform.component.mock.service.pledgebond;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyResMessage;

public interface PledgeBondReplyMessageService {

	  /**
	   * 本方成交对话报价
	   * @param message
	   * @return
	   */
	  public boolean confirmMessage(CfetsTradeCollateralDialogueQuoteReplyResMessage message);
	  
	  /**
	   * 本方拒绝对话报价
	   * @param message
	   * @return
	   */
	  public boolean rejectMessage(CfetsTradeCollateralDialogueQuoteReplyResMessage message);
	    
}
