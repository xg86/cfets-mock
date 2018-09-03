package com.xquant.platform.component.mock.builder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.xquant.cfets.trade.protocol.common.CfetsTradeBondLeg;
import com.xquant.cfets.trade.protocol.common.CfetsTradeCollateralBond;
import com.xquant.cfets.trade.protocol.common.CfetsTradeCollateralBondList;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessageHeader;
import com.xquant.platform.component.commons.ATypeDefines;
import com.xquant.platform.component.commons.MarketTypeEnum;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.entity.instrument.InstrumentKey;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.itf.cfets.skeleton.util.NumberToString;
import com.xquant.platform.component.mock.dto.CfetsMockMessageInfo;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.MockVersion;
import com.xquant.platform.component.mock.util.CfetsConstantUtil;
import com.xquant.platform.component.mock.util.ComputeServiceUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockGenerateUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.builder
 * @author: guanglai.zhou
 * @triggerTime: 2018-07-20 10:20:35
 */
public abstract class AbstartCfetsMessageInfoBuilder<T extends CfetsTradeMessage>
		implements IMockMessageInfoBuilder4Quote<T> {

	@Autowired
	protected ComputeService computeService;

	public Map<String, String> cache = new ConcurrentHashMap<String, String>(1);

	/**
	 * 以下值采取固定值 平台部不从数据库查 外汇交易中心直接返回
	 */
	protected static final String PARTY_NAME = CfetsConstantUtil.getPartyName();
	protected static final String TRADER_NAME = CfetsConstantUtil.getTradeName();
	protected static final String CUSTODIAN_INSTITUTION_NAME = CfetsConstantUtil.getCustodianInstitutionName();
	protected static final String CASH_BANK_NAME = CfetsConstantUtil.getCashBankName();
	protected static final String COUNTER_PARTY_NAME = CfetsConstantUtil.getCounterPartyName();

	protected static final String QUOTEID = "quoteId";
	protected static final String TRANSACTTIME = "transactTime";
	protected static final String CLORDIDCLIENTID = "clOrdIDClientID";
	protected static final String VALIDUNTILTIME = "validUntilTime";
	protected static final String CLORDID = "clOrdID";

	@SuppressWarnings("rawtypes")
	@Override
	public MockMessageInfo build(T messageTempldate, T originMessage, Date triggerTime, OpTypeEnum optype) {
		Assert.notNull(optype, "optype can not be null");
		MockMessageInfo mockMessageInfo = new CfetsMockMessageInfo();
		T message = getReturnMessage(messageTempldate, originMessage);
		triggerTime = resolveTriggerTime(message, triggerTime);
		Long delay = resolveDelay(new Date(), triggerTime, TimeUnit.SECONDS);
		setMessageBodyWithGenerateValue(message, new Date());
		setMessageHeader(message, messageTempldate, originMessage, triggerTime);
		fillWithCompute(computeService, message);

		// CfetsTradeMessageHeader header = messageTempldate.getHeader();
		// header.setSendingTime(MockDateTimeUtil.getTransactimeOfDate(triggerTime));
		// header.setSerialNo(MockGenerateUtil.getNextSerialNoByDate());
		// if (originMessage != null) {
		// header.setClientID(originMessage.getHeader().getClientID());
		// }
		// header.setFrom(Thread.currentThread().getName());
		// header.setVersion(MockVersion.VERSION);
		// message.setHeader(header);

		fillMessageInfo(optype, mockMessageInfo, message, getQuoteId(message), delay);
		return mockMessageInfo;
	}

	public boolean matches(Class<?> cls, OpTypeEnum optype) {
		return getSupprotClass().equals(cls) && getSupprotOptype().equals(optype);
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
	protected synchronized void setMessageHeader(T message, T messageTempldate, T originMessage, Date triggerTime) {
		// 此处不能直接从messageTempldate中获取header
		/*
		 * CfetsTradeMessageHeader header = messageTempldate.getHeader();
		 * 
		 */
		CfetsTradeMessageHeader tempHeader = messageTempldate.getHeader();
		CfetsTradeMessageHeader header = new CfetsTradeMessageHeader();
		BeanUtils.copyProperties(tempHeader, header);
		header.setSendingTime(MockDateTimeUtil.getTransactimeOfDate(triggerTime));
		header.setSerialNo(MockGenerateUtil.getNextSerialNoByDate());
		if (originMessage != null) {
			header.setClientID(originMessage.getHeader().getClientID());
		}
		header.setVersion(MockVersion.VERSION);
		message.setHeader(header);
	}

	public abstract Class<?> getSupprotClass();

	public abstract OpTypeEnum getSupprotOptype();

	public abstract T getReturnMessage(T messageTempldate, T originMessage);

	public abstract Date resolveTriggerTime(T message, Date triggerTime);

	public abstract String getQuoteId(T message);

	public abstract Long resolveDelay(Date currentTime, Date triggerTime, TimeUnit timeUnit);

	public abstract void setMessageBodyWithGenerateValue(T message, Date triggerTime);

	public abstract void fillWithCompute(ComputeService computeService, T message);

	protected synchronized Map<String, String> generateParms4SelfAdd(Date triggerTime) {
		Map<String, String> cache = new HashMap<String, String>();
		String quoteId = MockGenerateUtil.getNextQuoteIdByDate();
		cache.put(QUOTEID, quoteId);
		cache.put(TRANSACTTIME, new SimpleDateFormat("yyyyMMdd-HH:mm:ss.sss").format(triggerTime));
		cache.put(CLORDID, MockGenerateUtil.getNextClOrdID());
		return cache;
	}

	protected synchronized Map<String, String> generateParms4CounterAdd(Date triggerTime) {
		Map<String, String> cache = new HashMap<String, String>();
		String quoteId = MockGenerateUtil.getNextQuoteIdByDate();
		cache.put(QUOTEID, quoteId);
		cache.put(TRANSACTTIME, new SimpleDateFormat("yyyyMMdd-HH:mm:ss.sss").format(triggerTime));
		cache.put(VALIDUNTILTIME, MockDateTimeUtil.timeAfterMinuteWithFormat(triggerTime, Integer.parseInt(CfetsConstantUtil.getValidateTimeDelay())));
		cache.put(CLORDIDCLIENTID, "CFETS-" + quoteId);
		return cache;
	}

	protected synchronized Map<String, String> generateParms4OtherOpbit(Date triggerTime) {
		Map<String, String> cache = new HashMap<String, String>();
		cache.put(TRANSACTTIME, new SimpleDateFormat("yyyyMMdd-HH:mm:ss.sss").format(triggerTime));
		cache.put(CLORDID, MockGenerateUtil.getNextClOrdID());
		return cache;
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
		mockMessageInfo.setTargetSys(TargetSystemEnum.CFETS);
		mockMessageInfo.setThreadName(Thread.currentThread().getName());
	}

	/**
	 * 获取头信息 根据模板对象重新设置发送时间和序列号
	 * 
	 * @param messageTempldate
	 * @return
	 */
//	protected synchronized CfetsTradeMessageHeader getHeader(T messageTempldate, T originMessage, Date triggerTime) {
//		// 此处不能直接从messageTempldate中获取header
//		/*
//		 * CfetsTradeMessageHeader header = messageTempldate.getHeader();
//		 * 
//		 */
//		CfetsTradeMessageHeader tempHeader = messageTempldate.getHeader();
//		CfetsTradeMessageHeader header = new CfetsTradeMessageHeader();
//		BeanUtils.copyProperties(tempHeader, header);
//		header.setSendingTime(MockDateTimeUtil.getTransactimeOfDate(triggerTime));
//		header.setSerialNo(MockGenerateUtil.getNextSerialNoByDate());
//		if (originMessage != null) {
//			header.setClientID(originMessage.getHeader().getClientID());
//		}
//		header.setFrom(Thread.currentThread().getName());
//		header.setVersion(MockVersion.VERSION);
//		return header;
//	}

	private final static int AI_OFF_SET = 0;

	protected void fillWithCompute4ClickDeal(ComputeService computeService,
			CfetsTradeBondClickDealQuoteResMessageBody body) {
		Assert.notNull(body, "body can not be null");
		Assert.notNull(computeService, "computeService can not be null");
		Assert.notNull(body.getSecurityID(), "securityID can not be null");
		Assert.notNull(body.getPrice(), "price can not be null");
		
		Assert.notNull(body.getSettlType(), "settlType can not be null");
		int setDays = Integer.parseInt(body.getSettlType())-1; 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, setDays);
		String settleDate =  new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()); 
		
		InstrumentKey instrumentKey = ComputeServiceUtil.getInstrumentKey(body.getSecurityID(),
				ATypeDefines.A_TYPE_CODE_SPT_BD, MarketTypeEnum.X_CNBD.getValue());
		double ytm = ComputeServiceUtil.getYtmViaNetPrice(computeService, instrumentKey, settleDate, AI_OFF_SET,
				body.getPrice());
		body.setMaturityYield(new BigDecimal(ytm).setScale(4, RoundingMode.HALF_UP).toString());
		Assert.notNull(body.getMaturityYield(), "maturityYield can not be null");
	}

	protected void fillWithCompute4PriceLimit(ComputeService computeService,
			CfetsTradeBondPriceLimitQuoteResMessageBody body) {
		Assert.notNull(body, "body can not be null");
		Assert.notNull(computeService, "computeService can not be null");
		Assert.notNull(body.getSecurityID(), "securityID can not be null");
		Assert.notNull(body.getPrice(), "price can not be null");
		
		Assert.notNull(body.getSettlType(), "settlType can not be null");
		int setDays = Integer.parseInt(body.getSettlType())-1; 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, setDays);
		String settleDate =  new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()); 
		
		InstrumentKey instrumentKey = ComputeServiceUtil.getInstrumentKey(body.getSecurityID(),
				ATypeDefines.A_TYPE_CODE_SPT_BD, MarketTypeEnum.X_CNBD.getValue());
		double ytm = ComputeServiceUtil.getYtmViaNetPrice(computeService, instrumentKey, settleDate, AI_OFF_SET,
				body.getPrice());
		body.setMaturityYield(new BigDecimal(ytm).setScale(4, RoundingMode.HALF_UP).toString());
		Assert.notNull(body.getMaturityYield(), "maturityYield can not be null");
	}

	protected void fillWithCompute4MarketMaker(ComputeService computeService,
			CfetsTradeBondMarketMakingQuoteResMessageBody body) {
		Assert.notNull(body, "body can not be null");
		Assert.notNull(computeService, "computeService can not be null");
				
		if (body.getBondLegList() != null && CollectionUtils.isNotEmpty(body.getBondLegList().getBondLegList())) {
			ArrayList<CfetsTradeBondLeg> bondLegList = body.getBondLegList().getBondLegList();
			for (CfetsTradeBondLeg cfetsTradeBondLeg : bondLegList) {
				Assert.notNull(cfetsTradeBondLeg.getLegSecurityID(), "securityID can not be null");
				Assert.notNull(cfetsTradeBondLeg.getLegPrice(), "price can not be null");
				
				Assert.notNull(cfetsTradeBondLeg.getLegSettlType(), "settlType can not be null");
				int setDays = Integer.parseInt(cfetsTradeBondLeg.getLegSettlType())-1; 
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR, setDays);
				String settleDate =  new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()); 
				
				InstrumentKey instrumentKey = ComputeServiceUtil.getInstrumentKey(cfetsTradeBondLeg.getLegSecurityID(),
						ATypeDefines.A_TYPE_CODE_SPT_BD, MarketTypeEnum.X_CNBD.getValue());
				double ytm = ComputeServiceUtil.getYtmViaNetPrice(computeService, instrumentKey, settleDate, AI_OFF_SET,
						cfetsTradeBondLeg.getLegPrice());
				cfetsTradeBondLeg.setLegYtmYield(new BigDecimal(ytm).setScale(4, RoundingMode.HALF_UP).toString());
				Assert.notNull(cfetsTradeBondLeg.getLegYtmYield(), "legYtmYield can not be null");
			}
		}
	}

	protected void fillWithCompute4RfqReply(ComputeService computeService, CfetsTradeBondRFQReplyResMessageBody body) {
		Assert.notNull(body, "body can not be null");
		Assert.notNull(computeService, "computeService can not be null");
		Assert.notNull(body.getSecurityID(), "securityID can not be null");
		Assert.notNull(body.getPrice(), "price can not be null");
		
		Assert.notNull(body.getSettlType(), "settlType can not be null");
		int setDays = Integer.parseInt(body.getSettlType())-1; 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, setDays);
		String settleDate =  new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()); 
		
		InstrumentKey instrumentKey = ComputeServiceUtil.getInstrumentKey(body.getSecurityID(),
				ATypeDefines.A_TYPE_CODE_SPT_BD, MarketTypeEnum.X_CNBD.getValue());
		double ytm = ComputeServiceUtil.getYtmViaNetPrice(computeService, instrumentKey, settleDate, AI_OFF_SET,
				body.getPrice());
		body.setMaturityYield(new BigDecimal(ytm).setScale(4, RoundingMode.HALF_UP).toString());
		Assert.notNull(body.getMaturityYield(), "maturityYield can not be null");
	}

	/**
	 * 计算应计利息 应计利息总额 全价 结算金额 到期收益率
	 * 
	 * @param computeService
	 * @param body
	 *            计算之前必须存在参数
	 * 
	 *            <li>securityID</li>
	 *            <li>orderQty</li>
	 *            <li>settlDate</li>
	 *            <li>price</li>
	 * 
	 *            计算如下参数
	 *            <li>settlCurrAmt 结算金额 = 应计利息总额 + 交易金额</li>
	 *            <li>accruedInterestAmt 应计利息</li>
	 *            <li>accruedInterestTotalAmt 应计利息总额</li>
	 *            <li>dirtyPrice 全价</li>
	 *            <li>tradeCashAmt 交易金额</li>
	 */
	protected void fillWithCompute4BondDlg(ComputeService computeService,
			CfetsTradeBondDialogueQuoteResMessageBody body) {
		Assert.notNull(body, "body can not be null");
		Assert.notNull(computeService, "computeService can not be null");
		Assert.notNull(body.getSecurityID(), "securityID can not be null");
		Assert.notNull(body.getOrderQty(), "orderQty can not be null");
		Assert.notNull(body.getSettlDate(), "settlDate can not be null");
		Assert.notNull(body.getPrice(), "price can not be null");
		
		Assert.notNull(body.getSettlType(), "settlType can not be null");
		int setDays = Integer.parseInt(body.getSettlType())-1; 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, setDays);
		String settleDate =  new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());  
		
		// 指定key
		InstrumentKey instrumentKey = ComputeServiceUtil.getInstrumentKey(body.getSecurityID(),
				ATypeDefines.A_TYPE_CODE_SPT_BD, MarketTypeEnum.X_CNBD.getValue());

		// 计算到期收益率
		double ytm = ComputeServiceUtil.getYtmViaNetPrice(computeService, instrumentKey, settleDate, AI_OFF_SET,
				body.getPrice());
		body.setMaturityYield(new BigDecimal(ytm).setScale(4, RoundingMode.HALF_UP).toString());
		// 应计利息
		double ai = ComputeServiceUtil.getai(computeService, instrumentKey, settleDate, AI_OFF_SET);
		body.setAccruedInterestAmt(String.valueOf(ai));
		// 应计利息总额 = 应计利息 * 数量
		BigDecimal volume = new BigDecimal(body.getOrderQty()).divide(new BigDecimal("100"));
		BigDecimal totalAi = new BigDecimal(body.getAccruedInterestAmt()).multiply(volume);
		body.setAccruedInterestTotalAmt(NumberToString.toString(totalAi, 2));
		// 交易金额 = 净价 * 数量
		// 结算金额 = 交易金额 + 应计利息总额 = 全价 * 数量
		BigDecimal tradeCashAmt = new BigDecimal(body.getPrice()).multiply(volume);
		body.setTradeCashAmt(NumberToString.toString(tradeCashAmt, 2));
		BigDecimal settlCurrAmt = tradeCashAmt.add(totalAi);
		body.setSettlCurrAmt(NumberToString.toString(settlCurrAmt, 2));
		// 计算全价
		BigDecimal fullPrice = settlCurrAmt.divide(volume);
		body.setDirtyPrice(NumberToString.toString(fullPrice, 4));

		Assert.notNull(body.getSettlCurrAmt(), "settlCurrAmt can not be null");
		Assert.notNull(body.getAccruedInterestAmt(), "accruedInterestAmt can not be null");
		Assert.notNull(body.getAccruedInterestTotalAmt(), "accruedInterestTotalAmt can not be null");
		Assert.notNull(body.getDirtyPrice(), "dirtyPrice can not be null");
		Assert.notNull(body.getTradeCashAmt(), "tradeCashAmt can not be null");
		Assert.notNull(body.getMaturityYield(), "maturityYield can not be null");

	}

	/**
	 * 计算 应计利息总额 到期结算金额
	 * 
	 * @param computeService
	 * @param body
	 *            计算之前必须存在参数
	 * 
	 *            <li>collateralBondList</li>
	 *            <li>tradeLimitDays</li>
	 *            <li>price</li>
	 * 
	 *            计算如下参数
	 *            <li>settlCurrAmt2 到期结算金额 = 应计利息总额 + 交易金额</li>
	 *            <li>accruedInterestTotalAmt 应计利息总额</li>
	 */
	protected void fillWithCompute4PledgeBond(ComputeService computeService,
			CfetsTradeCollateralDialogueQuoteResMessageBody body) {
		Assert.notNull(body, "body can not be null");
		Assert.notNull(computeService, "computeService can not be null");
		Assert.notNull(body.getCollateralBondList(), "collateralBondList can not be null");
		Assert.notNull(body.getPrice(), "price can not be null");
		Assert.notNull(body.getTradeLimitDays(), "tradeLimitDays can not be null");
		// 应计利息系数 回购利率 * 回购期限/365
		BigDecimal ytmRatio = new BigDecimal(body.getPrice()).multiply(new BigDecimal(body.getTradeLimitDays()))
				.divide(new BigDecimal("365"), 2, RoundingMode.HALF_UP);
		BigDecimal settlCurramt2 = new BigDecimal("0");
		BigDecimal accruedInterestAmt = new BigDecimal("0");
		CfetsTradeCollateralBondList collateralBondList = body.getCollateralBondList();
		ArrayList<CfetsTradeCollateralBond> bondList = collateralBondList.getCollateralBondList();
		for (CfetsTradeCollateralBond cfetsTradeCollateralBond : bondList) {
			// 质押式回购计算公式
			// 首期结算金额 = 交易金额 = 券面总额 * 折算比例
			// 应计利息 = 交易金额* 回购利率 * 回购期限/365
			// 到期结算金额 = 交易金额 + 应计利息

			Assert.notNull(cfetsTradeCollateralBond.getUnderlyingQty(), "underlyingQty 券面总额不允许为空");
			Assert.notNull(cfetsTradeCollateralBond.getUnderlyingStipValue(), "underlyingStipValue 折算比例不允许为空");

			BigDecimal singleTradeAmt = new BigDecimal(cfetsTradeCollateralBond.getUnderlyingQty())
					.multiply(new BigDecimal(cfetsTradeCollateralBond.getUnderlyingStipValue()));
			BigDecimal ytm = singleTradeAmt.multiply(ytmRatio);
			// 应计利息总额
			accruedInterestAmt = accruedInterestAmt.add(ytm);
			BigDecimal singleEndsettleAmount = singleTradeAmt.add(ytm);
			// 结算金额总额
			settlCurramt2 = settlCurramt2.add(singleEndsettleAmount);
		}

		body.setAccruedInterestAmt(accruedInterestAmt.divide(new BigDecimal("1"), 5, RoundingMode.HALF_UP).toString());
		body.setSettlCurramt2(settlCurramt2.divide(new BigDecimal("1"), 2, RoundingMode.HALF_UP).toString());

		Assert.notNull(body.getAccruedInterestAmt(), "accruedInterestAmt can not be null");
		Assert.notNull(body.getSettlCurramt2(), "settlCurramt2 can not be null");

	}

	public static void main(String[] args) {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("1", "1");
		Map<String, String> unMap = new HashMap<String, String>(map);
		map.clear();
		System.out.println(unMap);
	}

}
