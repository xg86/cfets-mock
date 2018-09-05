package com.xquant.platform.component.mock.listener;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeExecutionReportMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.OpBitEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.common.QuoteBizTypeEnum;
import com.xquant.platform.component.itf.cfets.skeleton.transport.ProtocolEntitySerializeUtil;
import com.xquant.platform.component.mock.MessageMockManager;
import com.xquant.platform.component.mock.builder.MessageInfoBuilderManager4ExecutionReport;
import com.xquant.platform.component.mock.builder.bondres.BondReplyCanceledMessageQueryBuilder4Self;
import com.xquant.platform.component.mock.builder.bondres.BondReplyMessageCanceledQueryBuilder4Counter;
import com.xquant.platform.component.mock.dao.CommonCfetsTradeRfqResMessageDao;
import com.xquant.platform.component.mock.dto.MockMessageInfo;
import com.xquant.platform.component.mock.holder.NettyLongLiveClientHolder;
import com.xquant.platform.component.mock.netty.event.MockMessageBroadCastSuccEvent;
import com.xquant.platform.component.mock.registry.SheduledTaskRegister;
import com.xquant.platform.component.mock.util.CfetsConstantUtil;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.ResourceUtil4Cfets;

import xquant.xswap.protocol.XSwapMessage;

@Component
public class MockMessageBroadCastListener extends MessageMockManager implements ApplicationListener<MockMessageBroadCastSuccEvent> {

	@Autowired
	private CommonCfetsTradeRfqResMessageDao CommonCfetsTradeRfqResMessageDao;

	@Autowired
	private BondReplyMessageCanceledQueryBuilder4Counter bondReplyMessageCanceledQueryBuilder4Counter;

	@Autowired
	private BondReplyCanceledMessageQueryBuilder4Self bondReplyCanceledMessageQueryBuilder4Self;

	@Autowired
	private MessageInfoBuilderManager4ExecutionReport messageInfoBuilderManager4ExecutionReport;
	/**
	 * 以下成功动作导致报价成为终态
	 */
	private static final EnumSet<OpBitEnum> FINAL_STATE_OPBIT = EnumSet.of(OpBitEnum.CANCEL, OpBitEnum.CONFIRM, OpBitEnum.REJECT, OpBitEnum.EXPIRE);

	/**
	 * 具有交易对手的报价类型
	 */
	private static final EnumSet<QuoteBizTypeEnum> HAS_COUNTER_BIZTYPE = EnumSet.of(QuoteBizTypeEnum.BOND_DLG, QuoteBizTypeEnum.PLEDGEREPO_DLG,
			QuoteBizTypeEnum.BOND_RFQ_REQ, QuoteBizTypeEnum.BOND_RFQ_REPLY);

	private Random rand = new Random();

	@Override
	public void onApplicationEvent(MockMessageBroadCastSuccEvent event) {

		@SuppressWarnings("rawtypes")
		MockMessageInfo originMessageInfo = event.getMessageInfo();

		if (logger.isInfoEnabled()) {
			logger.info("send asynchronous message success = " + originMessageInfo.getQuoteId() + ", optype =" + originMessageInfo.getOptype()
					+ ", delay = " + originMessageInfo.getDelay() + ", timeUint = " + originMessageInfo.getTimeUnit().toString());
			logger.info("remote address and port = " + event.getChannel().remoteAddress());
		}

		try {

			// 如果是使用中央控制台转发报价而且有对手的报价需要通知到对手
			if (CfetsConstantUtil.useCenterServer() && hasCounter(originMessageInfo)) {
				NettyLongLiveClientHolder.getClient()
						.sendMessage(ProtocolEntitySerializeUtil.serialize((CfetsTradeMessage) originMessageInfo.getMessage()));
			}

			// 成交报价 进行成交回报的处理
			if (OpBitEnum.CONFIRM.equals(originMessageInfo.getOptype().getOpBit())) {
				threadService.execute(new Runnable() {
					@Override
					public void run() {
						handleConfirmStateMessage(originMessageInfo);
					}
				});
			}

			// 如果为终态报价 清空该报价的所有任务以及缓存等
			if (FINAL_STATE_OPBIT.contains(originMessageInfo.getOptype().getOpBit())) {
				threadService.execute(new Runnable() {
					@Override
					public void run() {
						handleFinalStateMessage(originMessageInfo);
					}
				});
			}

			// 如果是本方请求报价 则根据请求报价构造对手请求回复报价
			if (CfetsConstantUtil.counterRfqMessageAutoReply() && OpTypeEnum.BOND_RFQREQ_ADD.equals(originMessageInfo.getOptype())) {
				threadService.execute(new Runnable() {
					@Override
					public void run() {
						buildReplyMessageIfSelfRfqAdd(originMessageInfo);
					}
				});
			}

			// 如果是本方对话报价,则根据对话报价构造对话报价成交或拒绝报价
			if (CfetsConstantUtil.counterDlgMessageAutoReply() && OpTypeEnum.BOND_DIALOGUE_ADD.equals(originMessageInfo.getOptype())) {
				threadService.execute(new Runnable() {
					@Override
					public void run() {
						buildReplyMessageIfSelfBondDlgAdd(originMessageInfo);
					}
				});
			}

			// 如果是本方质押式回购对话报价,则根据质押式回购对话报价成交或拒绝报价
			if (CfetsConstantUtil.counterDlgMessageAutoReply() && OpTypeEnum.PLEDGEREPO_DIALOGUE_ADD.equals(originMessageInfo.getOptype())) {
				threadService.execute(new Runnable() {
					@Override
					public void run() {
						buildReplyMessageIfSelfBondRepoAdd(originMessageInfo);
					}
				});
			}

			// 新增报价触发过期通知
			if (CfetsTradeMessage.class.isInstance(originMessageInfo.getMessage())
					&& OpBitEnum.ADD.equals(originMessageInfo.getOptype().getOpBit())) {
				threadService.execute(new Runnable() {
					@Override
					public void run() {
						buildExpiredTask(originMessageInfo);
					}
				});
			}

			if (XSwapMessage.class.isInstance(originMessageInfo.getMessage())) {

			}
		} catch (Exception e) {
			logger.error("MockMessageBroadCastListener ERROR", e);
		}

	}

	/**
	 * @param originMessageInfo
	 */
	@SuppressWarnings("rawtypes")
	private void buildExpiredTask(MockMessageInfo originMessageInfo) {
		// 触发过期通知
		CfetsTradeMessage originMessage = (CfetsTradeMessage) originMessageInfo.getMessage();
		CfetsTradeMessage messageTempldate = getExpiredMessageTemplate(originMessageInfo);
		// 根据原报价类型构造IMockMessageInfo对象
		String originOptype = originMessageInfo.getOptype().getValue();
		String expireOptype = originOptype.substring(0, originOptype.length() - 1).concat(OpBitEnum.EXPIRE.getValue());
		MockMessageInfo messageInfo = messageMessageInfoBuilderManager.build(messageTempldate, originMessage, OpTypeEnum.getOpTypeEnum(expireOptype),
				null);
		// 进行消息的处理(入库和发送)
		executeTask(messageInfo);
	}

	/**
	 * @param originMessageInfo
	 */
	@SuppressWarnings("rawtypes")
	private void buildReplyMessageIfSelfBondRepoAdd(MockMessageInfo originMessageInfo) {
		if (CfetsTradeCollateralDialogueQuoteResMessage.class.isInstance(originMessageInfo.getMessage())) {
			CfetsTradeCollateralDialogueQuoteResMessage orignRfqMessage = (CfetsTradeCollateralDialogueQuoteResMessage) originMessageInfo
					.getMessage();
			CfetsTradeCollateralDialogueQuoteResMessage messageTempldate = ResourceUtil4Cfets.getPledgeDlgRecvMessage4Confirm();
			Date triggerTime = MockDateTimeUtil.timeAfterMinute(CfetsConstantUtil.getAutoDlgSentDelay());
			MockMessageInfo messageInfo = null;
			double num = rand.nextDouble();
			if (num < 0.8) {
				messageInfo = messageMessageInfoBuilderManager.build(messageTempldate, orignRfqMessage, OpTypeEnum.PLEDGEREPO_DIALOGUE_CONFIRM,
						triggerTime);
			} else {
				messageInfo = messageMessageInfoBuilderManager.build(messageTempldate, orignRfqMessage, OpTypeEnum.PLEDGEREPO_DIALOGUE_REJECT,
						triggerTime);
			}
			if (messageInfo != null) {
				executeTask(messageInfo);
			}
		}
	}

	/**
	 * @param originMessageInfo
	 */
	@SuppressWarnings("rawtypes")
	private void buildReplyMessageIfSelfBondDlgAdd(MockMessageInfo originMessageInfo) {
		if (CfetsTradeBondDialogueQuoteResMessage.class.isInstance(originMessageInfo.getMessage())) {
			CfetsTradeBondDialogueQuoteResMessage orignRfqMessage = (CfetsTradeBondDialogueQuoteResMessage) originMessageInfo.getMessage();
			CfetsTradeBondDialogueQuoteResMessage messageTempldate = ResourceUtil4Cfets.getBondDlgRecvMessage4Confirm();
			Date triggerTime = MockDateTimeUtil.timeAfterMinute(CfetsConstantUtil.getAutoDlgSentDelay());
			MockMessageInfo messageInfo = null;
			double num = rand.nextDouble();
			if (num < 0.8) {
				messageInfo = messageMessageInfoBuilderManager.build(messageTempldate, orignRfqMessage, OpTypeEnum.BOND_DIALOGUE_CONFIRM,
						triggerTime);
			} else {
				messageInfo = messageMessageInfoBuilderManager.build(messageTempldate, orignRfqMessage, OpTypeEnum.BOND_DIALOGUE_REJECT, triggerTime);
			}
			if (messageInfo != null) {
				executeTask(messageInfo);
			}
		}
	}

	/**
	 * @param originMessageInfo
	 */
	@SuppressWarnings("rawtypes")
	private void buildReplyMessageIfSelfRfqAdd(MockMessageInfo originMessageInfo) {
		if (CfetsTradeBondRFQResMessage.class.isInstance(originMessageInfo.getMessage())) {
			CfetsTradeBondRFQResMessage orignRfqMessage = (CfetsTradeBondRFQResMessage) originMessageInfo.getMessage();
			CfetsTradeBondRFQReplyReceiveMessage messageTempldate = ResourceUtil4Cfets.getBondReplyRecvMessage4Add();
			messageTempldate.getBody().setQuoteReqID(orignRfqMessage.getBody().getQuoteID());
			messageTempldate.getBody().setValiduntilTime(orignRfqMessage.getBody().getValidUntilTime());
			Date triggerTime = MockDateTimeUtil.timeAfterMinute(CfetsConstantUtil.getAutoReplySentDelay());
			MockMessageInfo messageInfo = messageMessageInfoBuilderManager.build(messageTempldate, null, OpTypeEnum.BOND_RFQREPLY_ADD, triggerTime);
			// 进行消息的处理(入库和发送)
			executeTask(messageInfo);
		}
	}

	/**
	 * 成交回报报价
	 * 
	 * @param originMessageInfo
	 */
	@SuppressWarnings("rawtypes")
	private void handleConfirmStateMessage(MockMessageInfo originMessageInfo) {

		CfetsTradeExecutionReportMessage cfetsTradeExecutionReportMessage = TYPENAME_EXECUTIONMESSAGE
				.get(originMessageInfo.getMessage().getClass().getSimpleName());

		if (cfetsTradeExecutionReportMessage != null) {
			MockMessageInfo messageInfo = messageInfoBuilderManager4ExecutionReport.build(cfetsTradeExecutionReportMessage,
					originMessageInfo.getMessage(), originMessageInfo.getOptype(), null);
			// 进行消息的处理(入库和发送)
			executeTask(messageInfo);
		}
	}

	/**
	 * @param originMessageInfo
	 */
	private void handleFinalStateMessage(@SuppressWarnings("rawtypes") MockMessageInfo originMessageInfo) {
		SheduledTaskRegister.removeTask(originMessageInfo.getQuoteId());
		// 请求报价的终态意味着对应的非终态请求回复报价的自动撤销
		if (QuoteBizTypeEnum.BOND_RFQ_REQ.equals(originMessageInfo.getOptype().getQuoteBizType())) {
			if (logger.isInfoEnabled()) {
				logger.info("start remove all reply quote whick quoteReqId = " + originMessageInfo.getQuoteId());
			}
			List<String> relationSets = CommonCfetsTradeRfqResMessageDao.queryValidQuoteIdViaQuoteReqId(originMessageInfo.getQuoteId());
			if (CollectionUtils.isNotEmpty(relationSets)) {
				for (String replyQuoteId : relationSets) {
					SheduledTaskRegister.removeTask(replyQuoteId);
					if (logger.isInfoEnabled()) {
						logger.info("remove reply quote which quoteId = " + replyQuoteId + " and quoteReqId = " + originMessageInfo.getQuoteId());
					}
					@SuppressWarnings("rawtypes")
					MockMessageInfo mockMessageInfo = null;
					// 本方请求报价终态 对手非终态请求回复报价自动撤销
					// 对手请求报价终态 本方非终态请求回复报价自动撤销
					if (CfetsTradeBondRFQResMessage.class.isInstance(originMessageInfo.getMessage())) {
						mockMessageInfo = bondReplyCanceledMessageQueryBuilder4Self.build(replyQuoteId);
					} else if (CfetsTradeBondRFQReceiveMessage.class.isInstance(originMessageInfo.getMessage())) {
						// 对手拒绝本方请求报价 撤销对手请求回复报价
						mockMessageInfo = bondReplyMessageCanceledQueryBuilder4Counter.build(replyQuoteId);
					} else if (CfetsTradeBondRFQRejectResMessage.class.isInstance(originMessageInfo.getMessage())) {
						// 本方拒绝对手请求报价 撤销本方的请求回复报价
						mockMessageInfo = bondReplyCanceledMessageQueryBuilder4Self.build(replyQuoteId);
					}
					// 进行消息的处理(入库和发送)
					if (mockMessageInfo != null) {
						executeTask(mockMessageInfo);
					}
				}
			}
		}
	}

	/**
	 * 该报价是否有对手方
	 * 
	 * @param originMessageInfo
	 * @return
	 */
	private boolean hasCounter(@SuppressWarnings("rawtypes") MockMessageInfo originMessageInfo) {
		return HAS_COUNTER_BIZTYPE.contains(originMessageInfo.getOptype().getQuoteBizType());
	}

	/**
	 * 成交回报的获取
	 */
	public static final Map<String, CfetsTradeExecutionReportMessage> TYPENAME_EXECUTIONMESSAGE = new ConcurrentHashMap<String, CfetsTradeExecutionReportMessage>();

	static {
		// 本方对话报价成交
		TYPENAME_EXECUTIONMESSAGE.put("CfetsTradeBondDialogueQuoteReplyResMessage", ResourceUtil4Cfets.getExcutionReport4BonddlgRes());
		// 对手对话报价成交
		TYPENAME_EXECUTIONMESSAGE.put("CfetsTradeBondDialogueQuoteResMessage", ResourceUtil4Cfets.getExcutionReport4BonddlgRecv());
		// 本方质押式回购对话报价成交
		TYPENAME_EXECUTIONMESSAGE.put("CfetsTradeCollateralDialogueQuoteReplyResMessage", ResourceUtil4Cfets.getExcutionReport4PledgeBondRes());
		// 对手质押式回购对话报价成交
		TYPENAME_EXECUTIONMESSAGE.put("CfetsTradeCollateralDialogueQuoteResMessage", ResourceUtil4Cfets.getExcutionReport4PledgeBondRecv());
		// 本方请求回复报价成交
		TYPENAME_EXECUTIONMESSAGE.put("CfetsTradeBondRFQReplyConfirmResMessage", ResourceUtil4Cfets.getExcutionReport4ReplyRes());
		// 对手请求回复报价成交
		TYPENAME_EXECUTIONMESSAGE.put("CfetsTradeBondRFQReplyResMessage", ResourceUtil4Cfets.getExcutionReport4ReplyRecv());
	}

	/**
	 * 过期报价的获取
	 */
	public static final Map<String, CfetsTradeMessage> TYPENAME_EXPIREDMESSAGE = new ConcurrentHashMap<String, CfetsTradeMessage>();

	static {
		/**
		 * 本方新增对话报价过期
		 */
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeBondDialogueQuoteResMessage", ResourceUtil4Cfets.getBondDlgResMessage4Expire());
		/**
		 * 对手对话报价过期
		 */
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeBondDialogueQuoteReceiveMessage", ResourceUtil4Cfets.getBondDlgRecvMessage4Expire());
		/**
		 * 本方质押式回购过期
		 */
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeCollateralDialogueQuoteResMessage", ResourceUtil4Cfets.getPledgeDlgResMessage4Expire());
		/**
		 * 对手质押式回购过期
		 */
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeCollateralDialogueQuoteReceiveMessage", ResourceUtil4Cfets.getPledgeDlgRecvMessage4Expire());
		/**
		 * 本方请求报价过期
		 */
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeBondRFQResMessage", ResourceUtil4Cfets.getBondRfqResMessage4Expire());
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeBondRFQReceiveMessage", ResourceUtil4Cfets.getBondRfqRecvMessage4Expired());
		/**
		 * 本方请求回复过期
		 */
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeBondRFQReplyResMessage", ResourceUtil4Cfets.getBondReplyResMessage4Expire());
		/**
		 * 对手请求回复过期
		 */
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeBondRFQReplyReceiveMessage", ResourceUtil4Cfets.getBondReplyRecvMessage4Expire());
		/**
		 * 本方做市报价过期
		 */
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeBondMarketMakingQuoteResMessage", ResourceUtil4Cfets.getMMMessage4Expire());
		/**
		 * 本方限价报价过期
		 */
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeBondPriceLimitQuoteResMessage", ResourceUtil4Cfets.getPLMessage4Expire());
		/**
		 * 本方点击成交报价过期
		 */
		TYPENAME_EXPIREDMESSAGE.put("CfetsTradeBondClickDealQuoteResMessage", ResourceUtil4Cfets.getCDMessage4Expire());

	}

	/**
	 * 根据原报价消息获取对应的过期消息
	 * 
	 * @param originMessageInfo
	 * @return 对应报价的过期消息
	 */
	private CfetsTradeMessage getExpiredMessageTemplate(@SuppressWarnings("rawtypes") MockMessageInfo originMessageInfo) {
		String simpleName = originMessageInfo.getMessage().getClass().getSimpleName();
		return TYPENAME_EXPIREDMESSAGE.get(simpleName);
	}

}
