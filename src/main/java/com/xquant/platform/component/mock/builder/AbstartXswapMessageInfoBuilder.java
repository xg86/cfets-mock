/**
 * 
 */
package com.xquant.platform.component.mock.builder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.util.Assert;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.XswapMockMessageInfo;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockGenerateUtil;

import xquant.xswap.protocol.XSwapHeader;
import xquant.xswap.protocol.XSwapMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder
 * @author: guanglai.zhou
 * @triggerTime: 2018-07-20 10:20:35
 */
public abstract class AbstartXswapMessageInfoBuilder<T extends XSwapMessage> implements IMockMessageInfoBuilder4Quote<T> {

	/**
	 * 以下值采取固定值 平台部不从数据库查 外汇交易中心直接返回
	 */
//	protected static final String partyName = "杭州衡泰测试";
//	protected static final String traderName = "a080";
//	protected static final String custodianInstitutionName = "国债登记结算公司";
//	protected static final String cashBankName = "杭州衡泰测试";
//	protected static final String counterPartyName = "恒天基金";

	protected static final String QUOTEID = "quoteId";
	protected static final String TRANSACTTIME = "transactTime";
	protected static final String CLIENTREQID = "clientReqID";
//	protected static final String SERIALNO = "serialNo";
//	protected static final String VALIDUNTILTIME = "validUntilTime";
	protected static final String ORDERREQUESTID = "orderRequestID";
	protected static final String SYSREQID = "sysReqID";
	protected static final String OPERATESEQNUM = "operateSeqNum";
	
	
	public abstract Class<?> getSupprotClass();
	public abstract OpTypeEnum getSupprotOptype();
	public abstract T getReturnMessage(T messageTempldate, T originMessage);
	public abstract Date resolveTriggerTime(T message,Date triggerTime);
	public abstract String getQuoteId(T message);
	public abstract Long resolveDelay(Date currentTime,Date triggerTime,TimeUnit timeUnit);
	public abstract void setMessageBodyWithGenerateValue(T message, Date triggerTime);
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public MockMessageInfo build(T messageTempldate,
			T originMessage, Date triggerTime, OpTypeEnum optype) {
		Assert.notNull(optype, "optype can not be null");
		MockMessageInfo mockMessageInfo = new XswapMockMessageInfo();
		T message = getReturnMessage(messageTempldate, originMessage);
		triggerTime = resolveTriggerTime(message,triggerTime);
		Long delay = resolveDelay(new Date(), triggerTime, TimeUnit.SECONDS);
		setMessageBodyWithGenerateValue(message, new Date());
		setMessageHeader(message, messageTempldate, originMessage, triggerTime);
		fillMessageInfo(optype, mockMessageInfo, message, getQuoteId(message), delay);
		return mockMessageInfo;
	}
	
	public boolean matches(Class<?> cls, OpTypeEnum optype) {
		return getSupprotClass().equals(cls)&&getSupprotOptype().equals(optype);
	}
	
	/**
	 * 构造返回消息的头信息
	 * 
	 * @param message
	 *            返回消息
	 * @param messageTempldate
	 *            消息模板
	 * @param originMessage
	 *            原报价消息
	 */
	protected void setMessageHeader(T message, T messageTempldate, T originMessage, Date triggerTime) {
		message.setHeader(getHeader(messageTempldate, originMessage, triggerTime));
	}
	
	/**
	 * 填充messageInfo消息内容
	 * 
	 * @param optype
	 * @param mockMessageInfo
	 * @param message
	 * @param quoteId
	 * @param delay
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void fillMessageInfo(OpTypeEnum optype, MockMessageInfo mockMessageInfo, T message, String quoteId,
			Long delay) {
		mockMessageInfo.setMessage(message);
		mockMessageInfo.setDelay(delay);
		mockMessageInfo.setOptype(optype);
		mockMessageInfo.setQuoteId(quoteId);
		mockMessageInfo.setTimeUnit(TimeUnit.SECONDS);
		mockMessageInfo.setTargetSys(TargetSystemEnum.XSWAP);
	}

	protected synchronized Map<String, String> generateParms4SelfAdd(Date triggerTime) {
		Map<String, String> cache = new HashMap<String, String>();
		String quoteId = MockGenerateUtil.getNextQuoteIdByDate();
		cache.put(QUOTEID, quoteId);
		cache.put(TRANSACTTIME, new SimpleDateFormat("yyyyMMdd-HH:mm:ss.sss").format(triggerTime));
		cache.put(ORDERREQUESTID, MockGenerateUtil.getNextOrderRequestID());
		cache.put(OPERATESEQNUM, MockGenerateUtil.getNextOperateSeqNum());
		cache.put(SYSREQID, MockGenerateUtil.getNextSysReqID());
		return cache;
	}

	protected synchronized Map<String, String> generateParms4OtherOpbit(Date triggerTime) {
		Map<String, String> cache = new HashMap<String, String>();
		cache.put(TRANSACTTIME, new SimpleDateFormat("yyyyMMdd-HH:mm:ss.sss").format(triggerTime));
		cache.put(ORDERREQUESTID, MockGenerateUtil.getNextOrderRequestID());
		cache.put(SYSREQID, MockGenerateUtil.getNextSysReqID());
		return cache;
	}
	
	
	/**
	 * 获取头信息 根据模板对象重新设置发送时间和序列号
	 * 
	 * @param messageTempldate
	 * @return
	 */
	protected synchronized XSwapHeader getHeader(T messageTempldate, T originMessage, Date triggerTime) {
		XSwapHeader header = messageTempldate.getHeader();
		header.setSendingTime(MockDateTimeUtil.getTransactimeOfDate(triggerTime));
		header.setSerialNo(MockGenerateUtil.getNextSerialNoByDate());
		if (originMessage != null) {
			header.setClientID(originMessage.getHeader().getClientID());
		}
		header.setVersion(MockVersion.VERSION);
		return header;
	}
}
