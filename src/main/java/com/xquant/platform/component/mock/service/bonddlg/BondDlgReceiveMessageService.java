package com.xquant.platform.component.mock.service.bonddlg;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessage;

public interface BondDlgReceiveMessageService {

	  /**
	   * 对手新增报价
	   * @param message
	   * @return
	   */
	  public boolean addMessage(CfetsTradeBondDialogueQuoteReceiveMessage message);
	  
	  /**
	   * 对手修改报价
	   * @param message
	   * @return
	   */
	  public boolean updateMessage(CfetsTradeBondDialogueQuoteReceiveMessage message);
	  
	  /**
	   * 对手撤销报价
	   * @param message
	   * @return
	   */
	  public boolean cancelMessage(CfetsTradeBondDialogueQuoteReceiveMessage message);
	  
	  /**
	   * 对手新增报价过期
	   * @param message
	   * @return
	   */
	  public boolean expireMessage(CfetsTradeBondDialogueQuoteReceiveMessage message);
	  
	  /**
	   * 对手客户端修改本方报价
	   * @param message
	   * @return
	   */
	  public boolean updateByCounterClient(CfetsTradeBondDialogueQuoteReceiveMessage message);
	  
}
