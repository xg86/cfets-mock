package com.xquant.platform.component.back.mock;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyConfirmResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.platform.component.back.test.base.MyTestWaiter;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteStatusEnum;
import com.xquant.platform.component.mock.dao.BondClickDealQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.BondDlgQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.BondMarketMakeQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.BondPriceLimitQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.BondRfqReqQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.BondRfqResQuoteResMessageDao;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeDlgMessageDao;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeRfqMessageDao;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeRfqResMessageDao;
import com.xquant.platform.component.mock.dao.PledgeBondQuoteReceiveMessageDao;
import com.xquant.platform.component.mock.dao.XSwapOrderResMessageDao;
import com.xquant.platform.component.mock.dto.cfets.MockCollateralDialogueQuoteReceiveMessageBody;
import com.xquant.platform.component.mock.persist.MessagePersistManagerDelegate;
import com.xquant.platform.component.mock.service.bonddlg.BondDlgResMessageService;
import com.xquant.platform.component.mock.service.bondres.BondRFQReplyResMessageService;
import com.xquant.platform.component.mock.service.bondrfq.BondRFQResMessageService;
import com.xquant.platform.component.mock.service.clickdeal.BondClickDealMessageService;
import com.xquant.platform.component.mock.service.clickdeal.BondMarketMakeMessageService;
import com.xquant.platform.component.mock.service.clickdeal.BondPriceLimitMessageService;
import com.xquant.platform.component.mock.service.pledgebond.PledgeBondResMessageService;
import com.xquant.platform.component.mock.util.ResourceUtil4Cfets;
import com.xquant.platform.component.mock.util.ResourceUtil4Xswap;

import xquant.xswap.protocol.XSwapMDQuoteResMessage;
import xquant.xswap.protocol.XSwapOrderActionResMessage;
import xquant.xswap.protocol.XSwapOrderResMessage;
import xquant.xswap.util.XSwapSerializeUtil;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.back.cfets.notify
 * @author: guanglai.zhou
 * @date: 2018-06-14 20:52:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext-component-mock-service.xml")
public class MessageServiceManagerDelegateTest {

	@Autowired
	private MessagePersistManagerDelegate messageServiceManagerDelegate;

	@Autowired
	private BondDlgQuoteResMessageDao bondDlgQuoteResMessageDao;

	@Autowired
	private CommonCfetsTradeDlgMessageDao commonBondDlgMessageDao;

	@Autowired
	private CommonCfetsTradeRfqResMessageDao commonCfetsTradeRfqResMessageDao;

	@Autowired
	private CommonCfetsTradeRfqMessageDao commonCfetsTradeRfqMessageDao;

	@Test
	public void testBondDlg() {

		MyTestWaiter.testWaitBefore();

		// 本方新增 修改 撤销 过期 对手成交 对手拒绝
		CfetsTradeBondDialogueQuoteResMessage bondDlgResMessage4Add = ResourceUtil4Cfets.getBondDlgResMessage4Add();
		CfetsTradeBondDialogueQuoteResMessage bondDlgResMessage4Update = ResourceUtil4Cfets
				.getBondDlgResMessage4Update();
		CfetsTradeBondDialogueQuoteResMessage bondDlgResMessage4Cancel = ResourceUtil4Cfets
				.getBondDlgResMessage4Cancel();
		CfetsTradeBondDialogueQuoteResMessage bondDlgResMessage4Expire = ResourceUtil4Cfets
				.getBondDlgResMessage4Expire();

		CfetsTradeBondDialogueQuoteResMessage bondDlgRecvMessage4Confirm = ResourceUtil4Cfets
				.getBondDlgRecvMessage4Confirm();
		CfetsTradeBondDialogueQuoteResMessage bondDlgRecvMessage4Reject = ResourceUtil4Cfets
				.getBondDlgRecvMessage4Reject();

		bondDlgResMessage4Expire.getBody().setQuoteID(bondDlgResMessage4Add.getBody().getQuoteID());
		String resQuoteId = bondDlgResMessage4Add.getBody().getQuoteID();
		commonBondDlgMessageDao.delete(resQuoteId);
		System.out.println(resQuoteId);

		messageServiceManagerDelegate.handle(bondDlgResMessage4Add);
		checkStatus(resQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(bondDlgResMessage4Update);
		checkStatus(resQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(bondDlgResMessage4Cancel);
		checkStatus(resQuoteId, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(bondDlgResMessage4Expire);
		checkStatus(resQuoteId, QuoteStatusEnum.EXPIRED);
		messageServiceManagerDelegate.handle(bondDlgRecvMessage4Confirm);
		checkStatus(resQuoteId, QuoteStatusEnum.FULL_DEALED);
		messageServiceManagerDelegate.handle(bondDlgRecvMessage4Reject);
		checkStatus(resQuoteId, QuoteStatusEnum.REJECTED);

		CfetsTradeBondDialogueQuoteReceiveMessage bondDlgRecvMessage4Add = ResourceUtil4Cfets
				.getBondDlgRecvMessage4Add();
		CfetsTradeBondDialogueQuoteReceiveMessage bondDlgRecvMessage4Update = ResourceUtil4Cfets
				.getBondDlgRecvMessage4Update();
		CfetsTradeBondDialogueQuoteReceiveMessage bondDlgRecvMessage4Cancel = ResourceUtil4Cfets
				.getBondDlgRecvMessage4Cancel();
		CfetsTradeBondDialogueQuoteReceiveMessage bondDlgRecvMessage4AExpire = ResourceUtil4Cfets
				.getBondDlgRecvMessage4Expire();
		CfetsTradeBondDialogueQuoteReplyResMessage bondDlgResMessage4Confirm = ResourceUtil4Cfets
				.getBondDlgResMessage4Confirm();
		CfetsTradeBondDialogueQuoteReplyResMessage bondDlgResMessage4Reject = ResourceUtil4Cfets
				.getBondDlgResMessage4Reject();

		bondDlgRecvMessage4AExpire.getBody().setQuoteID(bondDlgRecvMessage4Add.getBody().getQuoteID());
		String recvQuoteId = bondDlgRecvMessage4Add.getBody().getQuoteID();
		commonBondDlgMessageDao.delete(recvQuoteId);
		System.out.println(recvQuoteId);

		messageServiceManagerDelegate.handle(bondDlgRecvMessage4Add);
		checkStatus(recvQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(bondDlgRecvMessage4Update);
		checkStatus(recvQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(bondDlgRecvMessage4Cancel);
		checkStatus(recvQuoteId, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(bondDlgRecvMessage4AExpire);
		checkStatus(recvQuoteId, QuoteStatusEnum.EXPIRED);
		messageServiceManagerDelegate.handle(bondDlgResMessage4Confirm);
		checkStatus(recvQuoteId, QuoteStatusEnum.FULL_DEALED);
		messageServiceManagerDelegate.handle(bondDlgResMessage4Reject);
		checkStatus(recvQuoteId, QuoteStatusEnum.REJECTED);

	}

	@Test
	public void testPledgeBond() {
		CfetsTradeCollateralDialogueQuoteReceiveMessage pledgeDlgRecvMessage4Add = ResourceUtil4Cfets
				.getPledgeDlgRecvMessage4Add();
		CfetsTradeCollateralDialogueQuoteReceiveMessage pledgeDlgRecvMessage4Update = ResourceUtil4Cfets
				.getPledgeDlgRecvMessage4Update();
		CfetsTradeCollateralDialogueQuoteReceiveMessage pledgeDlgRecvMessage4Cancel = ResourceUtil4Cfets
				.getPledgeDlgRecvMessage4Cancel();
		CfetsTradeCollateralDialogueQuoteReceiveMessage pledgeDlgRecvMessage4Expire = ResourceUtil4Cfets
				.getPledgeDlgRecvMessage4Expire();
		CfetsTradeCollateralDialogueQuoteReplyResMessage pledgeDlgResMessage4Confirm = ResourceUtil4Cfets
				.getPledgeDlgResMessage4Confirm();
		CfetsTradeCollateralDialogueQuoteReplyResMessage pledgeDlgResMessage4Reject = ResourceUtil4Cfets
				.getPledgeDlgResMessage4Reject();
		String recvQuoteId = pledgeDlgRecvMessage4Add.getBody().getQuoteID();
		commonBondDlgMessageDao.delete(recvQuoteId);
		System.out.println(recvQuoteId);

		messageServiceManagerDelegate.handle(pledgeDlgRecvMessage4Add);
		checkStatus(recvQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(pledgeDlgRecvMessage4Update);
		checkStatus(recvQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(pledgeDlgRecvMessage4Cancel);
		checkStatus(recvQuoteId, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(pledgeDlgRecvMessage4Expire);
		checkStatus(recvQuoteId, QuoteStatusEnum.EXPIRED);
		messageServiceManagerDelegate.handle(pledgeDlgResMessage4Confirm);
		checkStatus(recvQuoteId, QuoteStatusEnum.FULL_DEALED);
		messageServiceManagerDelegate.handle(pledgeDlgResMessage4Reject);
		checkStatus(recvQuoteId, QuoteStatusEnum.REJECTED);

		CfetsTradeCollateralDialogueQuoteResMessage pledgeDlgResMessage4Add = ResourceUtil4Cfets
				.getPledgeDlgResMessage4Add();
		CfetsTradeCollateralDialogueQuoteResMessage pledgeDlgResMessage4Update = ResourceUtil4Cfets
				.getPledgeDlgResMessage4Update();
		CfetsTradeCollateralDialogueQuoteResMessage pledgeDlgResMessage4Cancel = ResourceUtil4Cfets
				.getPledgeDlgResMessage4Cancel();
		CfetsTradeCollateralDialogueQuoteResMessage pledgeDlgResMessage4Expire = ResourceUtil4Cfets
				.getPledgeDlgResMessage4Expire();
		CfetsTradeCollateralDialogueQuoteResMessage pledgeDlgRecvMessage4Confirm = ResourceUtil4Cfets
				.getPledgeDlgRecvMessage4Confirm();
		CfetsTradeCollateralDialogueQuoteResMessage pledgeDlgRecvMessage4Reject = ResourceUtil4Cfets
				.getPledgeDlgRecvMessage4Reject();
		String resQuoteId = pledgeDlgResMessage4Add.getBody().getQuoteID();
		commonBondDlgMessageDao.delete(resQuoteId);
		System.out.println(resQuoteId);

		messageServiceManagerDelegate.handle(pledgeDlgResMessage4Add);
		checkStatus(resQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(pledgeDlgResMessage4Update);
		checkStatus(resQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(pledgeDlgResMessage4Cancel);
		checkStatus(resQuoteId, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(pledgeDlgResMessage4Expire);
		checkStatus(resQuoteId, QuoteStatusEnum.EXPIRED);
		messageServiceManagerDelegate.handle(pledgeDlgRecvMessage4Confirm);
		checkStatus(resQuoteId, QuoteStatusEnum.FULL_DEALED);
		messageServiceManagerDelegate.handle(pledgeDlgRecvMessage4Reject);
		checkStatus(resQuoteId, QuoteStatusEnum.REJECTED);
	}

	@Autowired
	private BondClickDealQuoteResMessageDao bondClickDealQuoteResMessageDao;
	@Autowired
	private BondMarketMakeQuoteResMessageDao bondMarketMakeQuoteResMessageDao;
	@Autowired
	private BondPriceLimitQuoteResMessageDao bondPriceLimitQuoteResMessageDao;

	@Test
	public void testClickDeal() {
		CfetsTradeBondClickDealQuoteResMessage cdMessage4Add = ResourceUtil4Cfets.getCDMessage4Add();
		CfetsTradeBondClickDealQuoteResMessage cdMessage4Cancel = ResourceUtil4Cfets.getCDMessage4Cancel();
		CfetsTradeBondClickDealQuoteResMessage cdMessage4Expire = ResourceUtil4Cfets.getCDMessage4Expire();
		String cdQuoteId = cdMessage4Add.getBody().getQuoteID();
		bondClickDealQuoteResMessageDao.delete(cdQuoteId);
		messageServiceManagerDelegate.handle(cdMessage4Add);
		checkCDStatus(cdQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(cdMessage4Cancel);
		checkCDStatus(cdQuoteId, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(cdMessage4Expire);
		checkCDStatus(cdQuoteId, QuoteStatusEnum.EXPIRED);

		CfetsTradeBondMarketMakingQuoteResMessage mmMessage4Add = ResourceUtil4Cfets.getMMMessage4Add();
		CfetsTradeBondMarketMakingQuoteResMessage mmMessage4Cancel = ResourceUtil4Cfets.getMMMessage4Cancel();
		CfetsTradeBondMarketMakingQuoteResMessage mmMessage4Expire = ResourceUtil4Cfets.getMMMessage4Expire();
		String mmQuoteId = mmMessage4Add.getBody().getQuoteID();
		bondMarketMakeQuoteResMessageDao.delete(mmQuoteId);
		messageServiceManagerDelegate.handle(mmMessage4Add);
		checkMMStatus(mmQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(mmMessage4Cancel);
		checkMMStatus(mmQuoteId, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(mmMessage4Expire);
		checkMMStatus(mmQuoteId, QuoteStatusEnum.EXPIRED);

		CfetsTradeBondPriceLimitQuoteResMessage plMessage4Add = ResourceUtil4Cfets.getPLMessage4Add();
		CfetsTradeBondPriceLimitQuoteResMessage plMessage4Cancel = ResourceUtil4Cfets.getPLMessage4Cancel();
		CfetsTradeBondPriceLimitQuoteResMessage plMessage4Expire = ResourceUtil4Cfets.getPLMessage4Expire();
		String plQuoteId = plMessage4Add.getBody().getOrderID();
		bondPriceLimitQuoteResMessageDao.delete(plQuoteId);
		messageServiceManagerDelegate.handle(plMessage4Add);
		checkPLStatus(plQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(plMessage4Cancel);
		checkPLStatus(plQuoteId, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(plMessage4Expire);
		checkPLStatus(plQuoteId, QuoteStatusEnum.EXPIRED);
	}

	@Autowired
	private BondRfqReqQuoteResMessageDao bondRfqReqQuoteResMessageDao;

	@Test
	public void testRfqReq() {

		CfetsTradeBondRFQResMessage bondRfqResMessage4Add = ResourceUtil4Cfets.getBondRfqResMessage4Add();
		CfetsTradeBondRFQResMessage bondRfqResMessage4Cancel = ResourceUtil4Cfets.getBondRfqResMessage4Cancel();
		CfetsTradeBondRFQResMessage bondRfqResMessage4Expire = ResourceUtil4Cfets.getBondRfqResMessage4Expire();
		String resQuoteId = bondRfqResMessage4Add.getBody().getQuoteID();
		commonCfetsTradeRfqMessageDao.delete(resQuoteId);
		messageServiceManagerDelegate.handle(bondRfqResMessage4Add);
		checkRfqStatus(resQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(bondRfqResMessage4Cancel);
		checkRfqStatus(resQuoteId, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(bondRfqResMessage4Expire);
		checkRfqStatus(resQuoteId, QuoteStatusEnum.EXPIRED);

		CfetsTradeBondRFQReceiveMessage bondRfqRecvMessage4Add = ResourceUtil4Cfets.getBondRfqRecvMessage4Add();
		CfetsTradeBondRFQReceiveMessage bondRfqRecvMessage4Cancel = ResourceUtil4Cfets.getBondRfqRecvMessage4Cancel();
		CfetsTradeBondRFQReceiveMessage bondRfqRecvMessage4Expired = ResourceUtil4Cfets.getBondRfqRecvMessage4Expired();
		CfetsTradeBondRFQRejectResMessage bondRfqResMessage4Reject = ResourceUtil4Cfets.getBondRfqResMessage4Reject();
		String recvQuoteId = bondRfqRecvMessage4Add.getBody().getQuoteID();
		commonCfetsTradeRfqMessageDao.delete(recvQuoteId);
		messageServiceManagerDelegate.handle(bondRfqRecvMessage4Add);
		checkRfqStatus(recvQuoteId, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(bondRfqRecvMessage4Cancel);
		checkRfqStatus(recvQuoteId, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(bondRfqRecvMessage4Expired);
		checkRfqStatus(recvQuoteId, QuoteStatusEnum.EXPIRED);
		messageServiceManagerDelegate.handle(bondRfqResMessage4Reject);
		checkRfqStatus(recvQuoteId, QuoteStatusEnum.REJECTED);
	}

	@Autowired
	private BondRfqResQuoteResMessageDao bondRfqResQuoteResMessageDao;

	@Test
	public void testRfqReply() {
		CfetsTradeBondRFQReplyResMessage bondReplyResMessage4Add = ResourceUtil4Cfets.getBondReplyResMessage4Add();
		CfetsTradeBondRFQReplyResMessage bondReplyResMessage4Update = ResourceUtil4Cfets
				.getBondReplyResMessage4Update();
		CfetsTradeBondRFQReplyResMessage bondReplyResMessage4Cancel = ResourceUtil4Cfets
				.getBondReplyResMessage4Cancel();
		CfetsTradeBondRFQReplyResMessage bondReplyResMessage4Expire = ResourceUtil4Cfets
				.getBondReplyResMessage4Expire();
		CfetsTradeBondRFQReplyResMessage bondReplyRecvMessage4Confirm = ResourceUtil4Cfets
				.getBondReplyRecvMessage4Confirm();
		String resQuoteid = bondReplyResMessage4Add.getBody().getQuoteID();
		commonCfetsTradeRfqResMessageDao.delete(resQuoteid);
		messageServiceManagerDelegate.handle(bondReplyResMessage4Add);
		checkResStatus(resQuoteid, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(bondReplyResMessage4Update);
		checkResStatus(resQuoteid, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(bondReplyResMessage4Cancel);
		checkResStatus(resQuoteid, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(bondReplyResMessage4Expire);
		checkResStatus(resQuoteid, QuoteStatusEnum.EXPIRED);
		messageServiceManagerDelegate.handle(bondReplyRecvMessage4Confirm);
		checkResStatus(resQuoteid, QuoteStatusEnum.FULL_DEALED);

		CfetsTradeBondRFQReplyReceiveMessage bondReplyRecvMessage4Add = ResourceUtil4Cfets
				.getBondReplyRecvMessage4Add();
		CfetsTradeBondRFQReplyReceiveMessage bondReplyRecvMessage4Update = ResourceUtil4Cfets
				.getBondReplyRecvMessage4Update();
		CfetsTradeBondRFQReplyReceiveMessage bondReplyRecvMessage4Cancel = ResourceUtil4Cfets
				.getBondReplyRecvMessage4Cancel();
		CfetsTradeBondRFQReplyReceiveMessage bondReplyRecvMessage4Expire = ResourceUtil4Cfets
				.getBondReplyRecvMessage4Expire();
		CfetsTradeBondRFQReplyConfirmResMessage bondReplyResMessage4Confirm = ResourceUtil4Cfets
				.getBondReplyResMessage4Confirm();
		String recvQuoteid = bondReplyRecvMessage4Add.getBody().getQuoteID();
		commonCfetsTradeRfqResMessageDao.delete(recvQuoteid);
		messageServiceManagerDelegate.handle(bondReplyRecvMessage4Add);
		checkResStatus(recvQuoteid, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(bondReplyRecvMessage4Update);
		checkResStatus(recvQuoteid, QuoteStatusEnum.CONFIRMED);
		messageServiceManagerDelegate.handle(bondReplyRecvMessage4Cancel);
		checkResStatus(recvQuoteid, QuoteStatusEnum.CANCELED);
		messageServiceManagerDelegate.handle(bondReplyRecvMessage4Expire);
		checkResStatus(recvQuoteid, QuoteStatusEnum.EXPIRED);
		messageServiceManagerDelegate.handle(bondReplyResMessage4Confirm);
		checkResStatus(recvQuoteid, QuoteStatusEnum.FULL_DEALED);
	}

	@Test
	public void testXswapMd() {

		String xmlStr = "<root>\r\n" + "  <header>\r\n" + "    <action>1301</action>\r\n"
				+ "    <clientID>xIR3</clientID>\r\n" + "    <serialNo>20180518012766</serialNo>\r\n"
				+ "    <version>1.0.006</version>\r\n" + "    <msgType>A</msgType>\r\n"
				+ "    <sendingTime>2018-05-18 11:42:15.138</sendingTime>\r\n" + "    <from></from>\r\n"
				+ "    <operator></operator>\r\n" + "    <password></password>\r\n" + "    <errorCode>0</errorCode>\r\n"
				+ "    <errorInfo></errorInfo>\r\n" + "  </header>\r\n" + "  <body>\r\n"
				+ "    <serialNo>20180518003476</serialNo>\r\n" + "    <status>3</status>\r\n"
				+ "    <errorCode></errorCode>\r\n" + "    <errorInfo></errorInfo>\r\n"
				+ "    <sysReqID>1000057</sysReqID>\r\n" + "    <xSwapReqID></xSwapReqID>\r\n"
				+ "    <clientReqID>SCRI20150921AAAA00000001</clientReqID>\r\n"
				+ "    <subscriptionRequestType>1</subscriptionRequestType>\r\n" + "    <mdBookType>1</mdBookType>\r\n"
				+ "    <marketDepth>5</marketDepth>\r\n" + "    <transactTime>20180518-11:42:03.003</transactTime>\r\n"
				+ "    <clearingMethod>6</clearingMethod>\r\n" + "    <securityID>-</securityID>\r\n"
				+ "    <marketIndicator>2</marketIndicator>\r\n" + "    <securityType>IRS</securityType>\r\n"
				+ "    <party>179001533010000401021</party>\r\n" + "    <trader>hzhtmapi</trader>\r\n" + "  </body>\r\n"
				+ "</root>";

		Object obj = XSwapSerializeUtil.fromXml(XSwapMDQuoteResMessage.class, xmlStr);

		messageServiceManagerDelegate.handle(obj);

	}

	@Autowired
	private XSwapOrderResMessageDao xSwapOrderResMessageDao;

	@Test
	public void testXswap() {
		XSwapOrderResMessage xSwapMessage4AddResPush = ResourceUtil4Xswap.getXSwapMessage4AddResPush();
		XSwapOrderActionResMessage xSwapMessage4CancelResPush = ResourceUtil4Xswap.getXSwapMessage4CancelResPush();
		String quoteId = xSwapMessage4AddResPush.getBody().getFb_OrderID();
		xSwapOrderResMessageDao.delete(quoteId);
		messageServiceManagerDelegate.handle(xSwapMessage4AddResPush);
		messageServiceManagerDelegate.handle(xSwapMessage4CancelResPush);
	}

	@Autowired
	private BondDlgResMessageService bondDlgResMessageService;

	@Autowired
	private BondClickDealMessageService bondClickDealMessageService;

	@Autowired
	private BondMarketMakeMessageService bondMarketMakeMessageService;

	@Autowired
	private BondPriceLimitMessageService bondPriceLimitMessageService;

	@Autowired
	private BondRFQReplyResMessageService bondRFQReplyResMessageService;

	@Autowired
	private BondRFQResMessageService bondRFQResMessageService;

	@Autowired
	private PledgeBondResMessageService PledgeBondResMessageService;
	
	@Autowired
	private PledgeBondQuoteReceiveMessageDao pledgeBondQuoteReceiveMessageDao;

	@Test
	public void testUnFinalQuery() {
		bondDlgResMessageService.queryUnFinalValidQuote();
		bondClickDealMessageService.queryUnFinalValidQuote();
		bondMarketMakeMessageService.queryUnFinalValidQuote();
		bondPriceLimitMessageService.queryUnFinalValidQuote();
		bondRFQReplyResMessageService.queryUnFinalValidQuote();
		bondRFQResMessageService.queryUnFinalValidQuote();
		PledgeBondResMessageService.queryUnFinalValidQuote();
		
		MockCollateralDialogueQuoteReceiveMessageBody queryByQuoteId = pledgeBondQuoteReceiveMessageDao.queryByQuoteId("201808210920000010");
		System.out.println(queryByQuoteId.getIsQuoteResponse());
	}
	
	private void checkResStatus(String quoteId, QuoteStatusEnum quoteStaus) {
		CfetsTradeBondRFQReplyResMessageBody queryByQuoteId = bondRfqResQuoteResMessageDao.queryByQuoteId(quoteId);
		System.out.println("=========transactiontime========" + queryByQuoteId.getTransactTime());
		assertEquals(queryByQuoteId.getStatus(), quoteStaus.getValue());
	}

	private void checkRfqStatus(String quoteId, QuoteStatusEnum quoteStaus) {
		CfetsTradeBondRFQResMessageBody queryByQuoteId = bondRfqReqQuoteResMessageDao.queryByQuoteId(quoteId);
		System.out.println("=========transactiontime========" + queryByQuoteId.getTransactTime());
		assertEquals(queryByQuoteId.getStatus(), quoteStaus.getValue());
	}

	private void checkCDStatus(String quoteId, QuoteStatusEnum quoteStaus) {
		CfetsTradeBondClickDealQuoteResMessageBody queryByQuoteId = bondClickDealQuoteResMessageDao
				.queryByQuoteId(quoteId);
		System.out.println("=========transactiontime========" + queryByQuoteId.getTransactTime());
		assertEquals(queryByQuoteId.getStatus(), quoteStaus.getValue());
	}

	private void checkMMStatus(String quoteId, QuoteStatusEnum quoteStaus) {
		CfetsTradeBondMarketMakingQuoteResMessageBody queryByQuoteId = bondMarketMakeQuoteResMessageDao
				.queryByQuoteId(quoteId);
		System.out.println("=========transactiontime========" + queryByQuoteId.getTransactTime());
		assertEquals(queryByQuoteId.getStatus(), quoteStaus.getValue());
	}

	private void checkPLStatus(String quoteId, QuoteStatusEnum quoteStaus) {
		CfetsTradeBondPriceLimitQuoteResMessageBody queryByQuoteId = bondPriceLimitQuoteResMessageDao
				.queryByQuoteId(quoteId);
		System.out.println("=========transactiontime========" + queryByQuoteId.getTransactTime());
		assertEquals(queryByQuoteId.getStatus(), quoteStaus.getValue());
	}

	private void checkStatus(String quoteId, QuoteStatusEnum quoteStaus) {
		CfetsTradeBondDialogueQuoteResMessageBody queryByQuoteId = bondDlgQuoteResMessageDao.queryByQuoteId(quoteId);
		System.out.println("=========transactiontime========" + queryByQuoteId.getTransactTime());
		assertEquals(queryByQuoteId.getStatus(), quoteStaus.getValue());
	}
}
