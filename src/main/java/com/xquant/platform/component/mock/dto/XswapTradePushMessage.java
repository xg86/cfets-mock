package com.xquant.platform.component.mock.dto;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dto
 * @author: guanglai.zhou
 * @date: 2018-08-17 16:54:40
 */
public class XswapTradePushMessage {

	private String pid;
	private String message;
	private String address;
	private String clientId;
	private String updatetime;
	private String action;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "CfetsTradePushMessage [pid=" + pid + ", message=" + message + ", address=" + address + ", clientId="
				+ clientId + ", updatetime=" + updatetime + ", action=" + action + "]";
	}

}
