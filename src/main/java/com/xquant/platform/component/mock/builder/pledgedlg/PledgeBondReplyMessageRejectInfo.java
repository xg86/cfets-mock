package com.xquant.platform.component.mock.builder.pledgedlg;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyResMessageBody;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.skeleton.enums.common.MarketIndicatorEnum;
import com.xquant.platform.component.itf.cfets.skeleton.enums.quote.SecurityTypeEnum;
import com.xquant.platform.component.mock.builder.AbstartCfetsMessageInfoBuilder;
import com.xquant.platform.component.mock.dao.PledgeBondQuoteReplyResMessageDao;
import com.xquant.platform.component.mock.util.PropertiesCopyUtil;

@Component
public class PledgeBondReplyMessageRejectInfo
		extends AbstartCfetsMessageInfoBuilder<CfetsTradeCollateralDialogueQuoteReplyResMessage> {

	@Autowired
	private PledgeBondQuoteReplyResMessageDao pledgeBondQuoteReplyResMessageDao;

	@Override
	public CfetsTradeCollateralDialogueQuoteReplyResMessage getReturnMessage(
			CfetsTradeCollateralDialogueQuoteReplyResMessage messageTempldate,
			CfetsTradeCollateralDialogueQuoteReplyResMessage originMessage) {
		CfetsTradeCollateralDialogueQuoteReplyResMessage message = originMessage;
		CfetsTradeCollateralDialogueQuoteReplyResMessageBody body = message.getBody();
		CfetsTradeCollateralDialogueQuoteReplyResMessageBody queryMessageBody = pledgeBondQuoteReplyResMessageDao
				.queryByQuoteId(body.getQuoteID());
		PropertiesCopyUtil.fieldCopyIfNull(queryMessageBody, body);
		String serialNo = originMessage.getHeader().getSerialNo();
		body.setSerialNo(serialNo);
		body.setStatus(5);
		body.setErrorCode("0");
		body.setSecurityType(SecurityTypeEnum.BOND_PLDGE_REPO.getValue());
		body.setMarketIndicator(MarketIndicatorEnum.BOND_PLEDGE_REPO.getValue().toString());
		return message;
	}

	@Override
	public Date resolveTriggerTime(CfetsTradeCollateralDialogueQuoteReplyResMessage message, Date triggerTime) {
		return new Date();
	}

	@Override
	public String getQuoteId(CfetsTradeCollateralDialogueQuoteReplyResMessage message) {
		return message.getBody().getQuoteID();
	}

	@Override
	public Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit) {
		return 0L;
	}

	@Override
	public void setMessageBodyWithGenerateValue(CfetsTradeCollateralDialogueQuoteReplyResMessage message,
			Date triggerTime) {
		Map<String, String> generateParms = generateParms4OtherOpbit(triggerTime);
		message.getBody().setTransactTime(generateParms.get(TRANSACTTIME));
		message.getBody().setClOrdID(generateParms.get(CLORDID));
	}

	@Override
	public Class<?> getSupprotClass() {
		return CfetsTradeCollateralDialogueQuoteReplyResMessage.class;
	}

	@Override
	public OpTypeEnum getSupprotOptype() {
		return OpTypeEnum.PLEDGEREPO_DIALOGUE_REJECT;
	}

	@Override
	public void fillWithCompute(ComputeService computeService,
			CfetsTradeCollateralDialogueQuoteReplyResMessage message) {

	}

}
