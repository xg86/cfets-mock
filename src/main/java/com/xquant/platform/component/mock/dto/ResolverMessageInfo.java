/**
 * 
 */
package com.xquant.platform.component.mock.dto;

import java.beans.ConstructorProperties;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.dto
 * @author: guanglai.zhou
 * @date: 2018-07-20 11:14:24
 */
public class ResolverMessageInfo {

	public ResolverMessageInfo() {
		super();
	}
	
	/**
	 * @param messageTempldate
	 * @param originMessage
	 * @param optype
	 * @param targetSys
	 */
	@ConstructorProperties({"messageTempldate","originMessage","optype","targetSys"})
	public ResolverMessageInfo(Object messageTempldate, Object originMessage, OpTypeEnum optype,
			TargetSystemEnum targetSys) {
		super();
		this.messageTempldate = messageTempldate;
		this.originMessage = originMessage;
		this.optype = optype;
		this.targetSys = targetSys;
	}

	/**
	 * 协议对象模板
	 */
	private Object messageTempldate;
	/**
	 * 原报价消息
	 */
	private Object originMessage;
	/**
	 * 报价类型
	 */
	private OpTypeEnum optype;

	private TargetSystemEnum targetSys;

	public Object getMessageTempldate() {
		return messageTempldate;
	}

	public void setMessageTempldate(Object messageTempldate) {
		this.messageTempldate = messageTempldate;
	}

	public Object getOriginMessage() {
		return originMessage;
	}

	public void setOriginMessage(Object originMessage) {
		this.originMessage = originMessage;
	}

	public OpTypeEnum getOptype() {
		return optype;
	}

	public void setOptype(OpTypeEnum optype) {
		this.optype = optype;
	}

	public TargetSystemEnum getTargetSys() {
		return targetSys;
	}

	public void setTargetSys(TargetSystemEnum targetSys) {
		this.targetSys = targetSys;
	}
}
