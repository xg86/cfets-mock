/**
 * 
 */
package com.xquant.platform.component.mock.dto.cfets;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dto.cfets
 * @author: guanglai.zhou
 * @date: 2018-08-18 17:02:11
 */
@SuppressWarnings("serial")
public class MockCollateralDialogueQuoteResMessageBody extends CfetsTradeCollateralDialogueQuoteResMessageBody
		implements IMockCfetsMessageBody {

	private String action;
	private String sendOrRecv;
	private String myside;
	private String clientId;
	private String updateTime;
	private Boolean isQuoteResponse;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSendOrRecv() {
		return sendOrRecv;
	}

	public void setSendOrRecv(String sendOrRecv) {
		this.sendOrRecv = sendOrRecv;
	}

	public String getMyside() {
		return myside;
	}

	public void setMyside(String myside) {
		this.myside = myside;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getIsQuoteResponse() {
		return isQuoteResponse;
	}

	public void setIsQuoteResponse(Boolean isQuoteResponse) {
		this.isQuoteResponse = isQuoteResponse;
	}

	@Override
	public QuoteBizTypeEnum getQuoteBizType() {
		return QuoteBizTypeEnum.PLEDGEREPO_DLG;
	}

	@Override
	public boolean isSelfQuote() {
		if (getIsQuoteResponse() != null && getIsQuoteResponse()) {
			return false;
		}
		return !getClOrdIDClientID().contains(COUNTER_CLORDID_SIGN);
	}

}
