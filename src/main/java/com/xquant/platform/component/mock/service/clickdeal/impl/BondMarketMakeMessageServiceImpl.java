package com.xquant.platform.component.mock.service.clickdeal.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xquant.cfets.trade.protocol.common.CfetsTradeBondLeg;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessage;
import com.xquant.platform.component.commons.ATypeDefines;
import com.xquant.platform.component.commons.MarketTypeEnum;
import com.xquant.platform.component.compute.api.ComputeService;
import com.xquant.platform.component.entity.instrument.InstrumentKey;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.itf.cfets.skeleton.constant.SendOrRecvFlag;
import com.xquant.platform.component.itf.cfets.skeleton.enums.quote.SideEnum;
import com.xquant.platform.component.mock.dao.BondMarketMakeQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dto.cfets.MockBondMarketMakingQuoteResMessageBody;
import com.xquant.platform.component.mock.dto.cfets.MockCfetsTradeBondLeg;
import com.xquant.platform.component.mock.service.clickdeal.BondMarketMakeMessageService;
import com.xquant.platform.component.mock.util.ComputeServiceUtil;
import com.xquant.platform.component.mock.util.MockDatePatternUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MockObjectUtil;

@Service
public class BondMarketMakeMessageServiceImpl implements BondMarketMakeMessageService {

	@Autowired
	private BondMarketMakeQuoteResMessageDao bondMarketMakeQuoteResMessageDao;

	@Autowired
	private DatabaseQueryDao sequenceQueryDao;

	@Autowired
	private ComputeService computeService;

	@Override
	public boolean addMessage(CfetsTradeBondMarketMakingQuoteResMessage message) {
		message.getBody().setQid(sequenceQueryDao.getNextCfetsTradeQid());
		MockObjectUtil.makeStrFieldNullIfBlank(message.getBody());
		return bondMarketMakeQuoteResMessageDao.insert(buildMockMessageBody(message)) == 1;
	}

	@Override
	public boolean cancelMessage(CfetsTradeBondMarketMakingQuoteResMessage message) {
		return bondMarketMakeQuoteResMessageDao.updateStatus(message.getBody().getQuoteID(),
				QuoteStatusEnum.CANCELED.getValue(), message.getBody().getTransactTime(),
				message.getBody().getQuoteTransType()) == 1;
	}

	@Override
	public boolean expireMessage(CfetsTradeBondMarketMakingQuoteResMessage message) {
		return bondMarketMakeQuoteResMessageDao.updateStatus(message.getBody().getQuoteID(),
				QuoteStatusEnum.EXPIRED.getValue(), message.getBody().getTransactTime(),
				message.getBody().getQuoteTransType()) == 1;
	}

	@Override
	public List<MockBondMarketMakingQuoteResMessageBody> queryUnFinalValidQuote() {
		String formatTime = MockDatePatternUtil.DATE_TIME_PATTERN.format(new Date());
		int count = bondMarketMakeQuoteResMessageDao.countUnFinalQuoteAfterTime(formatTime);
		if (count > 0) {
			return bondMarketMakeQuoteResMessageDao.queryUnFinalQuoteAfterTime(formatTime);
		}
		return null;
	}

	private MockBondMarketMakingQuoteResMessageBody buildMockMessageBody(
			CfetsTradeBondMarketMakingQuoteResMessage message) {
		MockBondMarketMakingQuoteResMessageBody mockBody = new MockBondMarketMakingQuoteResMessageBody();
		BeanUtils.copyProperties(message.getBody(), mockBody);
		mockBody.setAction(message.getHeader().getAction());
		mockBody.setClientId(message.getHeader().getClientID());
		mockBody.setSendOrRecv(SendOrRecvFlag.SEND);
		mockBody.setUpdateTime(MockDateTimeUtil.getTransactimeOfNow());
		CfetsTradeBondLeg buyPart = null;
		CfetsTradeBondLeg sellPart = null;
		if (message.getBody().getBondLegList() != null
				&& CollectionUtils.isNotEmpty(message.getBody().getBondLegList().getBondLegList())) {
			ArrayList<CfetsTradeBondLeg> bondLegList = message.getBody().getBondLegList().getBondLegList();
			for (CfetsTradeBondLeg cfetsTradeBondLeg : bondLegList) {
				if (SideEnum.SELL.getValue().equals(cfetsTradeBondLeg.getLegSide())) {
					sellPart = cfetsTradeBondLeg;
				} else {
					buyPart = cfetsTradeBondLeg;
				}
			}
		}
		if (sellPart != null) {
			MockCfetsTradeBondLeg buildMockBondLeg = buildMockBondLeg(sellPart);
			setMockBodySellPart(mockBody, buildMockBondLeg);
		}
		if (buyPart != null) {
			MockCfetsTradeBondLeg buildMockBondLeg = buildMockBondLeg(buyPart);
			setMockBodyBuyPart(mockBody, buildMockBondLeg);
		}
		return mockBody;
	}

	/**
	 * @param mockBody
	 * @param buildMockBondLeg
	 */
	private void setMockBodyBuyPart(MockBondMarketMakingQuoteResMessageBody mockBody,
			MockCfetsTradeBondLeg buildMockBondLeg) {
		mockBody.setBytmyield(buildMockBondLeg.getLegYtmYield());
		mockBody.setBstrikeyield(buildMockBondLeg.getLegStrikeYield());
		mockBody.setBprice(buildMockBondLeg.getLegPrice());
		mockBody.setBdirtyprice(buildMockBondLeg.getLegDirtyprice());
		mockBody.setBaccruedinterestamt(buildMockBondLeg.getLegAccruedinterestamt());
		mockBody.setBorderqty(buildMockBondLeg.getLegOrderQty());
		mockBody.setBtradecashamt(buildMockBondLeg.getLegTradecashamt());
		mockBody.setBaccruedinteresttotalamt(buildMockBondLeg.getLegAccruedinteresttotalamt());
		mockBody.setBsettlcurramt(buildMockBondLeg.getLegSettlcurramt());
		mockBody.setBsettlcurrency(buildMockBondLeg.getLegSettlCurrency());
		mockBody.setBsettlcurrfxrate(buildMockBondLeg.getLegSettlCurrFxRate());
		mockBody.setBdeliverytype(buildMockBondLeg.getLegDeliveryType());
		mockBody.setBclearingmethod(buildMockBondLeg.getLegClearingMethod());
		mockBody.setBsettltype(buildMockBondLeg.getLegSettlType());
		mockBody.setBsettldate(buildMockBondLeg.getLegSettldate());
		mockBody.setBtotalprincipal(buildMockBondLeg.getLegTotalprincipal());
		mockBody.setBleavesqty(buildMockBondLeg.getLegLeavesqty());
	}

	/**
	 * @param mockBody
	 * @param buildMockBondLeg
	 */
	private void setMockBodySellPart(MockBondMarketMakingQuoteResMessageBody mockBody,
			MockCfetsTradeBondLeg buildMockBondLeg) {
		mockBody.setSytmyield(buildMockBondLeg.getLegYtmYield());
		mockBody.setSstrikeyield(buildMockBondLeg.getLegStrikeYield());
		mockBody.setSprice(buildMockBondLeg.getLegPrice());
		mockBody.setSdirtyprice(buildMockBondLeg.getLegDirtyprice());
		mockBody.setSaccruedinterestamt(buildMockBondLeg.getLegAccruedinterestamt());
		mockBody.setSorderqty(buildMockBondLeg.getLegOrderQty());
		mockBody.setStradecashamt(buildMockBondLeg.getLegTradecashamt());
		mockBody.setSaccruedinteresttotalamt(buildMockBondLeg.getLegAccruedinteresttotalamt());
		mockBody.setSsettlcurramt(buildMockBondLeg.getLegSettlcurramt());
		mockBody.setSsettlcurrency(buildMockBondLeg.getLegSettlCurrency());
		mockBody.setSsettlcurrfxrate(buildMockBondLeg.getLegSettlCurrFxRate());
		mockBody.setSdeliverytype(buildMockBondLeg.getLegDeliveryType());
		mockBody.setSclearingmethod(buildMockBondLeg.getLegClearingMethod());
		mockBody.setSsettltype(buildMockBondLeg.getLegSettlType());
		mockBody.setSsettldate(buildMockBondLeg.getLegSettldate());
		mockBody.setStotalprincipal(buildMockBondLeg.getLegTotalprincipal());
		mockBody.setSleavesqty(buildMockBondLeg.getLegLeavesqty());
	}

	private final static int AI_OFF_SET = 0;

	private MockCfetsTradeBondLeg buildMockBondLeg(CfetsTradeBondLeg bondLeg) {
		MockCfetsTradeBondLeg mockLeg = new MockCfetsTradeBondLeg();
		BeanUtils.copyProperties(bondLeg, mockLeg);
		Assert.notNull(bondLeg, "bondLeg can not be null");
		Assert.notNull(bondLeg.getLegSecurityID(), "legSecurityID can not be null");
		Assert.notNull(bondLeg.getLegPrice(), "legPrice can not be null");
		Assert.notNull(bondLeg.getLegOrderQty(), "legOrderQty can not be null");

		BigDecimal volume = new BigDecimal(bondLeg.getLegOrderQty()).divide(new BigDecimal("100"));
		/**
		 * 需要计算值
		 */
		InstrumentKey instrumentKey = ComputeServiceUtil.getInstrumentKey(bondLeg.getLegSecurityID(),
				ATypeDefines.A_TYPE_CODE_SPT_BD, MarketTypeEnum.X_CNBD.getValue());
		String settleDate = MockDatePatternUtil.ISO_DATE_FORMAT.format(new Date());
		// 应计利息
		double ai = ComputeServiceUtil.getai(computeService, instrumentKey, settleDate, AI_OFF_SET);
		BigDecimal accruedinterestamt = new BigDecimal(ai).setScale(5, RoundingMode.HALF_UP);
		BigDecimal accruedinterestTotalamt = new BigDecimal(ai).multiply(volume).setScale(2, RoundingMode.HALF_UP);
		BigDecimal tradeCashAmt = new BigDecimal(bondLeg.getLegPrice()).multiply(volume);
		BigDecimal settlCurrAmt = tradeCashAmt.add(accruedinterestTotalamt);
		BigDecimal dirtyPrice = settlCurrAmt.divide(volume).setScale(4, RoundingMode.HALF_UP);
		mockLeg.setLegAccruedinterestamt(accruedinterestamt.toString());
		mockLeg.setLegAccruedinteresttotalamt(accruedinterestTotalamt.toString());
		mockLeg.setLegTradecashamt(tradeCashAmt.toString());
		mockLeg.setLegSettlcurramt(settlCurrAmt.toString());
		mockLeg.setLegDirtyprice(dirtyPrice.toString());
		mockLeg.setLegSettldate(MockDatePatternUtil.YYYYMMDD.format(new Date()));
		mockLeg.setLegLeavesqty(mockLeg.getLegOrderQty());
		Assert.notNull(mockLeg, "mockLeg can not be null");
		Assert.notNull(mockLeg.getLegSecurityID(), "legSecurityID can not be null");
		Assert.notNull(mockLeg.getLegPrice(), "legPrice can not be null");
		Assert.notNull(mockLeg.getLegOrderQty(), "legOrderQty can not be null");
		Assert.notNull(mockLeg.getLegAccruedinterestamt(), "legAccruedinterestamt can not be null");
		Assert.notNull(mockLeg.getLegAccruedinteresttotalamt(), "legAccruedinteresttotalamt can not be null");
		Assert.notNull(mockLeg.getLegTradecashamt(), "legTradecashamt can not be null");
		Assert.notNull(mockLeg.getLegSettlcurramt(), "legSettlcurramt can not be null");
		Assert.notNull(mockLeg.getLegDirtyprice(), "legDirtyprice can not be null");
		return mockLeg;
	}

}
