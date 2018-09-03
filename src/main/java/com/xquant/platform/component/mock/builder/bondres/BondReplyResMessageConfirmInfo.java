package com.xquant.platform.component.mock.builder.bondres;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyConfirmResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyConfirmResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.dao.BondRfqResQuoteConfirmMessageDao;

/**
 * 
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder.bondres 
 * @author: guanglai.zhou   
 * @date: 2018-08-20 08:38:38
 * 本方成交请求回复报价
 */
@Component
public class BondReplyResMessageConfirmInfo extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondRFQReplyConfirmResMessage> {

	@Autowired
	private BondRfqResQuoteConfirmMessageDao bondRfqResQuoteConfirmMessageDao;

	@Override
	public CfetsTradeBondRFQReplyConfirmResMessage getReturnMessage(
			CfetsTradeBondRFQReplyConfirmResMessage messageTempldate,
			CfetsTradeBondRFQReplyConfirmResMessage originMessage) {
		CfetsTradeBondRFQReplyConfirmResMessage message = originMessage;
		CfetsTradeBondRFQReplyConfirmResMessageBody body = message.getBody();
		CfetsTradeBondRFQReplyConfirmResMessageBody queryMessageBody = bondRfqResQuoteConfirmMessageDao
				.queryByQuoteId(body.getQuoteID());
		body.setMarketIndicator(queryMessageBody.getMarketIndicator());
		body.setSecurityType(queryMessageBody.getSecurityType());
		body.setQuoteType(queryMessageBody.getQuoteType());
		body.setSecurityID(queryMessageBody.getSecurityID());
		body.setSide(queryMessageBody.getSide());
		body.setMaturityYield(queryMessageBody.getMaturityYield());
		body.setPrice(queryMessageBody.getPrice());
		body.setOrderQty(queryMessageBody.getOrderQty());
		body.setSettlCurrAmt(queryMessageBody.getSettlCurrAmt());
		body.setSettlCurrency(queryMessageBody.getSettlCurrency());
		body.setDeliveryType(queryMessageBody.getDeliveryType());
		body.setClearingMethod(queryMessageBody.getClearingMethod());
		body.setSettlType(queryMessageBody.getSettlType());
		body.setQid(queryMessageBody.getQid());
		body.setStatus(5);
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQReplyConfirmResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(CfetsTradeBondRFQReplyConfirmResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondRFQReplyConfirmResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4OtherOpbit(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondRFQReplyConfirmResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_RFQREPLY_CONFIRM;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondRFQReplyConfirmResMessage message) {
		
	}

}
