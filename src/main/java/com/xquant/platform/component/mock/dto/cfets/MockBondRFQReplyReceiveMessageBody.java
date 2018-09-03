/**
 * 
 */
package com.xquant.platform.component.mock.dto.cfets;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessageBody;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.mock.dto.IMockCfetsMessageBody;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dto.cfets
 * @author: guanglai.zhou
 * @date: 2018-08-18 23:35:25
 */
@SuppressWarnings("serial")
public class MockBondRFQReplyReceiveMessageBody extends CfetsTradeBondRFQReplyReceiveMessageBody
		implements IMockCfetsMessageBody {

	private String action;
	private String sendOrRecv;
	private String myside;

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

	@Override
	public QuoteBizTypeEnum getQuoteBizType() {
		return QuoteBizTypeEnum.BOND_RFQ_REPLY;
	}

	@Override
	public boolean isSelfQuote() {
		return !getClOrdIDClientID().contains(COUNTER_CLORDID_SIGN);
	}

}
