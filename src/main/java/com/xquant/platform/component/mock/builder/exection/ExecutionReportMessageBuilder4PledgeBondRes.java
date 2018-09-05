package com.xquant.platform.component.mock.builder.exection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeExecutionReportMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeExecutionReportMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.mock.builder.IMockMessageInfoBuilder4ExcutionReport;
import com.xquant.platform.component.mock.dao.BondDlgQuoteResMessageDao;
import com.xquant.platform.component.mock.dto.CfetsMockMessageInfo;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.dto.cfets.MockBondDialogueQuoteResMessageBody;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockGenerateUtil;

@Component
public class ExecutionReportMessageBuilder4PledgeBondRes
		implements IMockMessageInfoBuilder4ExcutionReport<CfetsTradeExecutionReportMessage, CfetsTradeCollateralDialogueQuoteReplyResMessage> {

	@Autowired
	private BondDlgQuoteResMessageDao bondDlgQuoteResMessageDao;

	@Override
	public boolean matches(Class<?> cls, OpTypeEnum optype) {
		return CfetsTradeCollateralDialogueQuoteReplyResMessage.class.equals(cls) && OpTypeEnum.PLEDGEREPO_DIALOGUE_CONFIRM.equals(optype);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MockMessageInfo build(CfetsTradeExecutionReportMessage messageTempldate, CfetsTradeCollateralDialogueQuoteReplyResMessage originMessage,
			Date triggerTime, OpTypeEnum optype) {

		MockBondDialogueQuoteResMessageBody queryByQuoteId = bondDlgQuoteResMessageDao.queryByQuoteId(originMessage.getBody().getQuoteID());
		String transactTime = originMessage.getBody().getTransactTime();
		String[] splitTime = transactTime.split("-");

		CfetsTradeExecutionReportMessage message = new CfetsTradeExecutionReportMessage();
		CfetsTradeExecutionReportMessageBody body = new CfetsTradeExecutionReportMessageBody();
		CfetsTradeMessageHeader header = new CfetsTradeMessageHeader();
		message.setBody(body);
		message.setHeader(header);
		BeanUtils.copyProperties(messageTempldate.getHeader(), header);
		BeanUtils.copyProperties(messageTempldate.getBody(), body);

		header.setSerialNo(MockGenerateUtil.getNextSerialNoByDate());
		header.setVersion(MockVersion.VERSION);
		header.setSendingTime(MockDateTimeUtil.getTransactimeOfNow());
		header.setClientID(queryByQuoteId.getClientId());

		body.setSecuritytype(queryByQuoteId.getSecurityType());
		body.setQid(queryByQuoteId.getQid());
		body.setClientID(queryByQuoteId.getClientId());
		body.setClOrdIDClientID(queryByQuoteId.getClOrdIDClientID());
		body.setExecID(body.getSecurityType() + MockGenerateUtil.getNextExecId());
		body.setTradeDate(splitTime[0]);
		body.setTradeTime(splitTime[1]);
		body.setQuoteID(originMessage.getBody().getQuoteID());
		body.setTransactTime(originMessage.getBody().getTransactTime());
		body.setGrossTradeAmt(queryByQuoteId.getTradeCashAmt());
		body.setSide(queryByQuoteId.getMyside());
		body.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		body.setPrice(new BigDecimal(queryByQuoteId.getPrice()).multiply(new BigDecimal("100")).toString());
		body.setSecurityID(queryByQuoteId.getSecurityID());

		MockMessageInfo messageInfo = new CfetsMockMessageInfo();
		messageInfo.setDelay(0L);
		messageInfo.setMessage(message);
		messageInfo.setOptype(optype);
		messageInfo.setQuoteId(body.getQuoteID());
		messageInfo.setTargetSys(TargetSystemEnum.CFETS);
		messageInfo.setThreadName(Thread.currentThread().getName());
		messageInfo.setTimeUnit(TimeUnit.SECONDS);
		return messageInfo;
	}

}
