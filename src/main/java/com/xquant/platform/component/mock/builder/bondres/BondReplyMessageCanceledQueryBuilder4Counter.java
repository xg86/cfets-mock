package com.xquant.platform.component.mock.builder.bondres;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.common.enums.MsgTypeEnum;
import com.xquant.platform.component.itf.cfets.skeleton.enums.quote.QuoteTransTypeEnum;
import com.xquant.platform.component.mock.dao.BondRfqResQuoteRecvMessageDao;
import com.xquant.platform.component.mock.dto.CfetsMockMessageInfo;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQReplyReceiveMessageBody;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockGenerateUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.bondres
 * @author: guanglai.zhou
 * @date: 2018-08-20 19:07:38 对手请求回复报价自动撤销构造方法 通过数据库查询构造对象 对应请求报价终态时触发
 */
@Component
public class BondReplyMessageCanceledQueryBuilder4Counter {

	@Autowired
	private BondRfqResQuoteRecvMessageDao bondRfqResQuoteRecvMessageDao;

	@SuppressWarnings("rawtypes")
	public MockMessageInfo build(String quoteId) {
		MockMessageInfo messageInfo = new CfetsMockMessageInfo();
		MockBondRFQReplyReceiveMessageBody queryMessageBody = bondRfqResQuoteRecvMessageDao.queryByQuoteId(quoteId);
		CfetsTradeBondRFQReplyReceiveMessage message = new CfetsTradeBondRFQReplyReceiveMessage();
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void fillMessageInfo(String quoteId, MockMessageInfo messageInfo,
			CfetsTradeBondRFQReplyReceiveMessage message) {
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
	private CfetsTradeBondRFQReplyReceiveMessageBody buildBody(MockBondRFQReplyReceiveMessageBody queryMessageBody) {
		CfetsTradeBondRFQReplyReceiveMessageBody body = new CfetsTradeBondRFQReplyReceiveMessageBody();
		body.setQid(queryMessageBody.getQid());
		body.setQuoteID(queryMessageBody.getQuoteID());
		body.setTransactTime(MockDateTimeUtil.getTransactimeOfNow());
		body.setStatus(QuoteStatusEnum.CANCELED.getValue());
		body.setClientID(queryMessageBody.getClientID());
		body.setClOrdIDClientID(queryMessageBody.getClOrdIDClientID());
		body.setQuoteTransType(QuoteTransTypeEnum.CANCEL.getValue());
		body.setMarketIndicator(queryMessageBody.getMarketIndicator());
		body.setSecurityType(queryMessageBody.getSecurityType());
		body.setQuoteType(queryMessageBody.getQuoteType());
		body.setSecurityID(queryMessageBody.getSecurityID());
		body.setSide(queryMessageBody.getSide());
		body.setSymbol(queryMessageBody.getSymbol());
		return body;
	}

	private CfetsTradeMessageHeader buildHeader(MockBondRFQReplyReceiveMessageBody queryMessageBody) {
		CfetsTradeMessageHeader header = new CfetsTradeMessageHeader();
		header.setAction("4005");
		header.setClientID(queryMessageBody.getClientID());
		header.setSerialNo(MockGenerateUtil.getNextSerialNoByDate());
		header.setVersion(MockVersion.VERSION);
		header.setMsgType(MsgTypeEnum.PUSH.getValue());
		header.setSendingTime(MockDateTimeUtil.getTransactimeOfNow());
		header.setErrorCode("0");
		return header;
	}
}
