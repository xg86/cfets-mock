package com.xquant.platform.component.mock.dto;

import com.xquant.cfets.trade.protocol.common.CfetsTradeBondLeg;

public class MyCfetsTradeBondLeg extends CfetsTradeBondLeg {

	/**
	 * 报价编号
	 */
	private String quoteID;

	public String getQuoteID() {
		return quoteID;
	}

	public void setQuoteID(String quoteID) {
		this.quoteID = quoteID;
	}

}
