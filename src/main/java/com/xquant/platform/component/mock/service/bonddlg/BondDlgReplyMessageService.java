package com.xquant.platform.component.mock.service.bonddlg;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReplyResMessage;

public interface BondDlgReplyMessageService {

	  /**
	   * 本方成交对话报价
	   * @param message
	   * @return
	   */
	  public boolean confirmMessage(CfetsTradeBondDialogueQuoteReplyResMessage message);
	  
	  /**
	   * 本方拒绝对话报价
	   * @param message
	   * @return
	   */
	  public boolean rejectMessage(CfetsTradeBondDialogueQuoteReplyResMessage message);
	    
}
