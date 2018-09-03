package com.xquant.platform.component.mock.util;

import org.apache.commons.lang3.StringUtils;

import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.entity.instrument.InstrumentKey;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-06-18 22:01:07
 */
public class ComputeServiceUtil {

	// 银行间都为0 不记结算日当日的利息 计息偏移
	// 部分交易所使用1 计结算日当日的利息
	private final static int AI_OFF_SET = 0;

	public final static String DOUBLE_PATTERN = "\\d+(.\\d+)?";

	/**
	 * 根据净价计算到期收益率
	 * 
	 * @param computeService
	 *            计算服务
	 * @param instrumentKey
	 *            计算指令
	 * @param settleDate
	 *            结算日期
	 * @param aiOffSet
	 *            应计利息偏移指数
	 * @param netPrice
	 *            净价
	 * @return 到期收益率 单位为 %
	 */
	public static double getYtmViaNetPrice(ComputeService computeService, InstrumentKey instrumentKey,
			String settleDate, int aiOffSet, String netPrice) {

		if (StringUtils.isBlank(netPrice)) {
			throw new RuntimeException(" 该市场行情数据中全价(LastPx)不允许为空或空字符 ！");
		}

		if (!netPrice.matches(DOUBLE_PATTERN)) {
			throw new RuntimeException("该全价不符合数字类型 " + netPrice);
		}

		double netprice = Double.valueOf(netPrice).doubleValue();

		double ai = getai(computeService, instrumentKey, settleDate, aiOffSet);
		double fullPrice = netprice + ai;
		double ytm = computeService.ytm(AI_OFF_SET, fullPrice, instrumentKey, settleDate);
		return ytm * 100;
	}

	/**
	 * 计算债券的应计利息
	 * 
	 * @param computeService
	 *            计算服务
	 * @param instrumentKey
	 *            指令
	 * @param settleDate
	 *            结算日期
	 * @param aiOffSet
	 *            应计利息偏移指数
	 * @return 到期收益率 单位为 %
	 */
	public static double getai(ComputeService computeService, InstrumentKey instrumentKey, String settleDate,
			int aiOffSet) {
		return computeService.ai(aiOffSet, instrumentKey, settleDate);
	}

	/**
	 * 根据全价计算到期收益率
	 * 
	 * @param computeService
	 *            计算服务
	 * @param instrumentKey
	 *            计算指令
	 * @param settleDate
	 *            清算日期
	 * @param aiOffSet
	 *            应计利息偏移指数
	 * @param fullPrice
	 *            全价
	 * @return 到期收益率 单位为%
	 */
	public static double getYtmViaFullPrice(ComputeService computeService, InstrumentKey instrumentKey,
			String settleDate, int aiOffSet, String fullPrice) {

		if (StringUtils.isBlank(fullPrice)) {
			throw new RuntimeException(" 该市场行情数据中全价(mdEntryPx)不允许为空或空字符 ！");
		}

		if (!fullPrice.matches(DOUBLE_PATTERN)) {
			throw new RuntimeException("该全价不符合数字类型 " + fullPrice);
		}

		double npv = Double.valueOf(fullPrice).doubleValue();

		double ytm = computeService.ytm(AI_OFF_SET, npv, instrumentKey, settleDate);

		return ytm * 100;
	}

	/**
	 * 获取金融工具主键
	 * 
	 * @param iCode
	 * @param aType
	 * @param mType
	 * @return
	 */
	public static InstrumentKey getInstrumentKey(String iCode, String aType, String mType) {
		if (StringUtils.isBlank(iCode) || StringUtils.isBlank(aType) || StringUtils.isBlank(mType)) {
			throw new RuntimeException("该市场行情数据中不存在债券(security)或债券资产类型(aType)或市场类型(mType)为空！");
		}

		InstrumentKey instrumentKey = new InstrumentKey();
		instrumentKey.setiCode(iCode);
		instrumentKey.setaType(aType);
		instrumentKey.setmType(mType);

		return instrumentKey;
	}
}
