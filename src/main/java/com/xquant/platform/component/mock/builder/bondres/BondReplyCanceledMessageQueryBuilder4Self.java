package com.xquant.platform.component.mock.builder.bondres;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.itf.cfets.skeleton.enums.quote.QuoteTransTypeEnum;
import com.xquant.platform.component.mock.dao.BondRfqResQuoteResMessageDao;
import com.xquant.platform.component.mock.dto.CfetsMockMessageInfo;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQReplyResMessageBody;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockGenerateUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.bondres
 * @author: guanglai.zhou
 * @date: 2018-08-20 19:07:38 本方请求回复报价自动撤销构造方法 通过数据库查询构造对象 对应请求报价终态时触发
 */
@Component
public class BondReplyCanceledMessageQueryBuilder4Self {

	@Autowired
	private BondRfqResQuoteResMessageDao bondRfqResQuoteResMessageDao;

	@SuppressWarnings("rawtypes")
	public MockMessageInfo build(String quoteId) {
		MockMessageInfo messageInfo = new CfetsMockMessageInfo();
		MockBondRFQReplyResMessageBody queryMessageBody = bondRfqResQuoteResMessageDao.queryByQuoteId(quoteId);
		CfetsTradeBondRFQReplyResMessage message = new CfetsTradeBondRFQReplyResMessage();
		message.setHeader(buildHeader(queryMessageBody));
		message.setBody(buildBody(queryMessageBody));
		fillMessageInfo(quoteId, messageInfo, message);
		return messageInfo;
	}

	/**
	 * @param quoteId
	 * @param messageInfo
	 * @param message
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fillMessageInfo(String quoteId, MockMessageInfo messageInfo,
			CfetsTradeBondRFQReplyResMessage message) {
		messageInfo.setDelay(0L);
		messageInfo.setMessage(message);
		messageInfo.setOptype(OpTypeEnum.BOND_RFQREPLY_CANCEL);
		messageInfo.setQuoteId(quoteId);
		messageInfo.setTargetSys(TargetSystemEnum.CFETS);
		messageInfo.setTimeUnit(TimeUnit.SECONDS);
	}

	/**
	 * @param queryMessageBody
	 * @return
	 */
	private CfetsTradeBondRFQReplyResMessageBody buildBody(MockBondRFQReplyResMessageBody queryMessageBody) {
		CfetsTradeBondRFQReplyResMessageBody body = new CfetsTradeBondRFQReplyResMessageBody();
		body.setClOrdIDClientID(queryMessageBody.getClOrdIDClientID());
        body.setQuoteReqID(queryMessageBody.getQuoteReqID());
        body.setQuoteID(queryMessageBody.getQuoteID());
        body.setQuoteTransType(QuoteTransTypeEnum.CANCEL.getValue());
        body.setTransactTime(MockDateTimeUtil.getTransactimeOfNow());
        body.setMarketIndicator(queryMessageBody.getMarketIndicator());
        body.setSecurityType(queryMessageBody.getSecurityType());
        body.setQuoteType(queryMessageBody.getQuoteType());
        body.setSecurityID(queryMessageBody.getSecurityID());
        body.setSide(queryMessageBody.getSide());
        body.setQid(queryMessageBody.getQid());
        body.setStatus(QuoteStatusEnum.CANCELED.getValue());
        body.setErrorCode("0");		
		return body;
	}

	private CfetsTradeMessageHeader buildHeader(MockBondRFQReplyResMessageBody queryMessageBody) {
		CfetsTradeMessageHeader header = new CfetsTradeMessageHeader();
		header.setAction("4001");
		header.setClientID(queryMessageBody.getClientId());
		header.setSerialNo(MockGenerateUtil.getNextSerialNoByDate());
		header.setVersion(MockVersion.VERSION);
		header.setMsgType(MsgTypeEnum.PUSH.getValue());
		header.setSendingTime(MockDateTimeUtil.getTransactimeOfNow());
		header.setErrorCode("0");
		return header;
	}

}
