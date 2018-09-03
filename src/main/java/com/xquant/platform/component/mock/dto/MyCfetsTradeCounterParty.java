/**
 * 
 */
package com.xquant.platform.component.mock.dto;

import com.xquant.cfets.trade.protocol.common.CfetsTradeCounterParty;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dto
 * @author: guanglai.zhou
 * @date: 2018-07-23 18:58:18
 */
public class MyCfetsTradeCounterParty extends CfetsTradeCounterParty {

	private Long qid;
	private String clientId;
	private String clordidClientId;
	private String clordid;
	private String quoteid;

	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClordidClientId() {
		return clordidClientId;
	}

	public void setClordidClientId(String clordidClientId) {
		this.clordidClientId = clordidClientId;
	}

	public String getClordid() {
		return clordid;
	}

	public void setClordid(String clordid) {
		this.clordid = clordid;
	}

	public String getQuoteid() {
		return quoteid;
	}

	public void setQuoteid(String quoteid) {
		this.quoteid = quoteid;
	}

}
