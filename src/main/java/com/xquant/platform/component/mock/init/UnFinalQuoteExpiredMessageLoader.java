/**
 * 
 */
package com.xquant.platform.component.mock.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.xquant.platform.component.mock.MessageMockManager;
import com.xquant.platform.component.mock.builder.MessageInfoBuilderManager4Expired;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.dto.cfets.MockBondClickDealQuoteResMessageBody;
import com.xquant.platform.component.mock.dto.cfets.MockBondDialogueQuoteResMessageBody;
import com.xquant.platform.component.mock.dto.cfets.MockBondMarketMakingQuoteResMessageBody;
import com.xquant.platform.component.mock.dto.cfets.MockBondPriceLimitQuoteResMessageBody;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQReplyResMessageBody;
import com.xquant.platform.component.mock.dto.cfets.MockBondRFQResMessageBody;
import com.xquant.platform.component.mock.dto.cfets.MockCollateralDialogueQuoteResMessageBody;
import com.xquant.platform.component.mock.service.bonddlg.BondDlgResMessageService;
import com.xquant.platform.component.mock.service.bondres.BondRFQReplyResMessageService;
import com.xquant.platform.component.mock.service.bondrfq.BondRFQResMessageService;
import com.xquant.platform.component.mock.service.clickdeal.BondClickDealMessageService;
import com.xquant.platform.component.mock.service.clickdeal.BondMarketMakeMessageService;
import com.xquant.platform.component.mock.service.clickdeal.BondPriceLimitMessageService;
import com.xquant.platform.component.mock.service.pledgebond.PledgeBondResMessageService;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.init
 * @author: guanglai.zhou
 * @date: 2018-08-22 20:04:14
 */
@Component
public class UnFinalQuoteExpiredMessageLoader extends MessageMockManager implements SmartLifecycle {

	@Autowired
	private MessageInfoBuilderManager4Expired messageInfoBuilderManager4Expired;

	@SuppressWarnings("rawtypes")
	public void unFinalQuoteScan() {
		if (logger.isInfoEnabled()) {
			logger.info("==========unFinalQuoteScan start======================");
		}

		try {
			List<MockMessageInfo> addMockMessageInfo = addMockMessageInfo();
			for (MockMessageInfo mockMessageInfo : addMockMessageInfo) {
				executeTask(mockMessageInfo);
			}
		} catch (Exception e) {
			logger.error("UnFinalQuoteExpiredMessageLoader error! ", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public List<MockMessageInfo> addMockMessageInfo() {
		List<MockMessageInfo> messageInfoList = new ArrayList<MockMessageInfo>();
		addBondDlgResMessage(messageInfoList);
		addPledgeBondResMessage(messageInfoList);
		addClickDealMessage(messageInfoList);
		addPriceLimitMessage(messageInfoList);
		addMarketMakerMessage(messageInfoList);
		addRFQResMessage(messageInfoList);
		addRFQReplyResMessage(messageInfoList);
		return messageInfoList;
	}

	@Autowired
	private BondDlgResMessageService bondDlgResMessageService;

	@SuppressWarnings("rawtypes")
	private void addBondDlgResMessage(List<MockMessageInfo> messageInfoList) {
		List<MockBondDialogueQuoteResMessageBody> queryUnFinalValidQuote = bondDlgResMessageService
				.queryUnFinalValidQuote();
		if (CollectionUtils.isNotEmpty(queryUnFinalValidQuote)) {
			for (MockBondDialogueQuoteResMessageBody mockBondDialogueQuoteResMessageBody : queryUnFinalValidQuote) {
				MockMessageInfo buildMessageInfo = messageInfoBuilderManager4Expired
						.build(mockBondDialogueQuoteResMessageBody);
				messageInfoList.add(buildMessageInfo);
			}
		}
	}

	@Autowired
	private BondClickDealMessageService bondClickDealMessageService;

	@SuppressWarnings("rawtypes")
	private void addClickDealMessage(List<MockMessageInfo> messageInfoList) {
		List<MockBondClickDealQuoteResMessageBody> queryUnFinalValidQuote = bondClickDealMessageService
				.queryUnFinalValidQuote();
		if (CollectionUtils.isNotEmpty(queryUnFinalValidQuote)) {
			for (MockBondClickDealQuoteResMessageBody mockBondDialogueQuoteResMessageBody : queryUnFinalValidQuote) {
				MockMessageInfo buildMessageInfo = messageInfoBuilderManager4Expired
						.build(mockBondDialogueQuoteResMessageBody);
				messageInfoList.add(buildMessageInfo);
			}
		}
	}

	@Autowired
	private BondMarketMakeMessageService bondMarketMakeMessageService;

	@SuppressWarnings("rawtypes")
	private void addMarketMakerMessage(List<MockMessageInfo> messageInfoList) {
		List<MockBondMarketMakingQuoteResMessageBody> queryUnFinalValidQuote = bondMarketMakeMessageService
				.queryUnFinalValidQuote();
		if (CollectionUtils.isNotEmpty(queryUnFinalValidQuote)) {
			for (MockBondMarketMakingQuoteResMessageBody mockBondDialogueQuoteResMessageBody : queryUnFinalValidQuote) {
				MockMessageInfo buildMessageInfo = messageInfoBuilderManager4Expired
						.build(mockBondDialogueQuoteResMessageBody);
				messageInfoList.add(buildMessageInfo);
			}
		}
	}

	@Autowired
	private BondPriceLimitMessageService bondPriceLimitMessageService;

	@SuppressWarnings("rawtypes")
	private void addPriceLimitMessage(List<MockMessageInfo> messageInfoList) {
		List<MockBondPriceLimitQuoteResMessageBody> queryUnFinalValidQuote = bondPriceLimitMessageService
				.queryUnFinalValidQuote();
		if (CollectionUtils.isNotEmpty(queryUnFinalValidQuote)) {
			for (MockBondPriceLimitQuoteResMessageBody mockBondDialogueQuoteResMessageBody : queryUnFinalValidQuote) {
				MockMessageInfo buildMessageInfo = messageInfoBuilderManager4Expired
						.build(mockBondDialogueQuoteResMessageBody);
				messageInfoList.add(buildMessageInfo);
			}
		}
	}

	@Autowired
	private BondRFQReplyResMessageService bondRFQReplyResMessageService;

	@SuppressWarnings("rawtypes")
	private void addRFQReplyResMessage(List<MockMessageInfo> messageInfoList) {
		List<MockBondRFQReplyResMessageBody> queryUnFinalValidQuote = bondRFQReplyResMessageService
				.queryUnFinalValidQuote();
		if (CollectionUtils.isNotEmpty(queryUnFinalValidQuote)) {
			for (MockBondRFQReplyResMessageBody mockBondDialogueQuoteResMessageBody : queryUnFinalValidQuote) {
				MockMessageInfo buildMessageInfo = messageInfoBuilderManager4Expired
						.build(mockBondDialogueQuoteResMessageBody);
				messageInfoList.add(buildMessageInfo);
			}
		}
	}

	@Autowired
	private BondRFQResMessageService bondRFQResMessageService;

	@SuppressWarnings("rawtypes")
	private void addRFQResMessage(List<MockMessageInfo> messageInfoList) {
		List<MockBondRFQResMessageBody> queryUnFinalValidQuote = bondRFQResMessageService.queryUnFinalValidQuote();
		if (CollectionUtils.isNotEmpty(queryUnFinalValidQuote)) {
			for (MockBondRFQResMessageBody mockBondDialogueQuoteResMessageBody : queryUnFinalValidQuote) {
				MockMessageInfo buildMessageInfo = messageInfoBuilderManager4Expired
						.build(mockBondDialogueQuoteResMessageBody);
				messageInfoList.add(buildMessageInfo);
			}
		}
	}

	@Autowired
	private PledgeBondResMessageService PledgeBondResMessageService;

	@SuppressWarnings("rawtypes")
	private void addPledgeBondResMessage(List<MockMessageInfo> messageInfoList) {
		List<MockCollateralDialogueQuoteResMessageBody> queryUnFinalValidQuote = PledgeBondResMessageService
				.queryUnFinalValidQuote();
		if (CollectionUtils.isNotEmpty(queryUnFinalValidQuote)) {
			for (MockCollateralDialogueQuoteResMessageBody mockBondDialogueQuoteResMessageBody : queryUnFinalValidQuote) {
				MockMessageInfo buildMessageInfo = messageInfoBuilderManager4Expired
						.build(mockBondDialogueQuoteResMessageBody);
				messageInfoList.add(buildMessageInfo);
			}
		}
	}

	private boolean isRunning = false;

	@Override
	public void start() {
		isRunning = true;
		unFinalQuoteScan();
	}

	@Override
	public void stop() {
		isRunning = false;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {

	}

}
