package com.xquant.platform.component.mock.builder.bonddlg;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;

/**
 * 本方新增报价
 * 
 * @author Administrator
 *
 */
@Component
public class BondDlgResMessageConfirmInfoBuilder
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeBondDialogueQuoteResMessage> {

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeBondDialogueQuoteResMessage message, Date triggerTime) {
		Map<String, String> generateParms = generateParms4SelfAdd(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
	}

	@Override
	public CfetsTradeBondDialogueQuoteResMessage getReturnMessage(
			CfetsTradeBondDialogueQuoteResMessage messageTempldate,
			CfetsTradeBondDialogueQuoteResMessage originMessage) {
		CfetsTradeBondDialogueQuoteResMessage message = originMessage;
		CfetsTradeBondDialogueQuoteResMessageBody body = message.getBody();
		body.setQuoteTransType(null);
		body.setStatus(QuoteStatusEnum.FULL_DEALED.getValue());
		body.setSerialNo(null);
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeBondDialogueQuoteResMessage message, Date triggerTime) {
		Assert.notNull(triggerTime, "triggerTime can not be null");
		return triggerTime;
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return MockDateTimeUtil.getDelayBetween(new Date(), triggerTime, timeUnit);
	}

	@Override
	public String getQuoteId(CfetsTradeBondDialogueQuoteResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeBondDialogueQuoteResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.BOND_DIALOGUE_CONFIRM;
	}

	@Override
	public void fillWithCompute(ComputeService computeService, CfetsTradeBondDialogueQuoteResMessage message) {

	}

}
