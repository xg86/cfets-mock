package com.xquant.platform.component.mock.builder.bondres;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

@Component
public class BondReplyRecvMessageExpireInfo
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondRFQReplyReceiveMessage> {

	@Override
	public CfetsTradeBondRFQReplyReceiveMessage getReturnMessage(CfetsTradeBondRFQReplyReceiveMessage messageTempldate,
			CfetsTradeBondRFQReplyReceiveMessage originMessage) {
		CfetsTradeBondRFQReplyReceiveMessage message = new CfetsTradeBondRFQReplyReceiveMessage();
		CfetsTradeBondRFQReplyReceiveMessageBody body = new CfetsTradeBondRFQReplyReceiveMessageBody();
		body.setQid(originMessage.getBody().getQid());
		body.setQuoteID(originMessage.getBody().getQuoteID());
		body.setTransactTime(originMessage.getBody().getValiduntilTime());
		body.setValiduntilTime(originMessage.getBody().getValiduntilTime());
		body.setStatus(QuoteStatusEnum.EXPIRED.getValue());
		body.setClientID(originMessage.getBody().getClientID());
		body.setClOrdIDClientID(originMessage.getBody().getClOrdIDClientID());
		body.setMarketIndicator(originMessage.getBody().getMarketIndicator());
		body.setSecurityType(originMessage.getBody().getSecurityType());
		body.setContingencyIndicator(originMessage.getBody().getContingencyIndicator());
		body.setQuoteType(originMessage.getBody().getQuoteType());
		body.setSecurityID(originMessage.getBody().getSecurityID());
		body.setSymbol(originMessage.getBody().getSymbol());
		body.setSide(originMessage.getBody().getSide());
		body.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		message.setBody(body);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondRFQReplyReceiveMessage message, Date triggerTime) {
		try {
			return MockDatePatternUtil.DATE_TIME_PATTERN.parse(message.getBody().getValiduntilTime());
		} catch (ParseException e) {
			throw new RuntimeException("time pattern is not match " + message.getBody().getValiduntilTime()
					+ " with pattern" + MockDatePatternUtil.DATE_TIME_PATTERN);
		}
	}

	@Override
	public String getQuoteId(CfetsTradeBondRFQReplyReceiveMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondRFQReplyReceiveMessage message, Date triggerTime) {

	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondRFQReplyReceiveMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_RFQREPLY_EXPIRED;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondRFQReplyReceiveMessage message) {

	}
}
