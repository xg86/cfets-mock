package com.xquant.platform.component.mock.resolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;
import com.xquant.platform.component.mock.util.ResourceUtil4Cfets;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-07-20 16:35:00 根据不同报价类型获取一个唯一的key值
 */
@Component("cfetsTradeMessageKeyResolver")
public class CfetsTradeMessageKeyResolver implements IMessageResolver<CfetsTradeMessage>{

	/**
	 * 保存根据quoteTranstype和status生成的key以及对应的报价类型的map
	 */
	private static final Map<String, OpTypeEnum> TRAN_STATU_OPTYPE_MAP = new ConcurrentHashMap<String, OpTypeEnum>();
	/**
	 * 保存根据quoteTranstype和status生成的key以及对应的报价对象消息模板
	 */
	private static final Map<String, CfetsTradeMessage> TRAN_STATU_MESSAGE_MAP = new ConcurrentHashMap<String, CfetsTradeMessage>();
	
	private static final CfetsKeyGeneratorStrategy messageKeyGeneratorStrategy = new CfetsKeyGeneratorStrategy();

	static {
		/**
		 * 本方新增债券对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2000-N-5-0", OpTypeEnum.BOND_DIALOGUE_ADD);
		TRAN_STATU_MESSAGE_MAP.put("2000-N-5-0", ResourceUtil4Cfets.getBondDlgResMessage4Add());
		TRAN_STATU_OPTYPE_MAP.put("2000-N-0-0", OpTypeEnum.BOND_DIALOGUE_ADD);
		TRAN_STATU_MESSAGE_MAP.put("2000-N-0-0", ResourceUtil4Cfets.getBondDlgResMessage4Add());
		/**
		 * 本方修改债券对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2000-R-5-0", OpTypeEnum.BOND_DIALOGUE_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("2000-R-5-0", ResourceUtil4Cfets.getBondDlgResMessage4Update());
		TRAN_STATU_OPTYPE_MAP.put("2000-R-0-0", OpTypeEnum.BOND_DIALOGUE_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("2000-R-0-0", ResourceUtil4Cfets.getBondDlgResMessage4Update());
		/**
		 * 本方撤销债券对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2000-C-5-0", OpTypeEnum.BOND_DIALOGUE_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("2000-C-5-0", ResourceUtil4Cfets.getBondDlgResMessage4Cancel());
		TRAN_STATU_OPTYPE_MAP.put("2000-C-0-0", OpTypeEnum.BOND_DIALOGUE_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("2000-C-0-0", ResourceUtil4Cfets.getBondDlgResMessage4Cancel());

		/**
		 * 本方新增债券报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("2000-0-9-0", OpTypeEnum.BOND_DIALOGUE_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("2000-0-9-0", ResourceUtil4Cfets.getBondDlgResMessage4Expire());
		/**
		 * 对手拒绝本方债券对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2000-0-11-0", OpTypeEnum.BOND_DIALOGUE_REJECT);
		TRAN_STATU_MESSAGE_MAP.put("2000-0-11-0", ResourceUtil4Cfets.getBondDlgRecvMessage4Reject());
		/**
		 * 对手成交本方债券对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2000-0-12-0", OpTypeEnum.BOND_DIALOGUE_CONFIRM);
		TRAN_STATU_MESSAGE_MAP.put("2000-0-12-0", ResourceUtil4Cfets.getBondDlgRecvMessage4Confirm());

		/**
		 * 本方成交对手对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2002-0-5-1", OpTypeEnum.BOND_DIALOGUE_CONFIRM);
		TRAN_STATU_MESSAGE_MAP.put("2002-0-5-1", ResourceUtil4Cfets.getBondDlgResMessage4Confirm());
		TRAN_STATU_OPTYPE_MAP.put("2002-0-0-1", OpTypeEnum.BOND_DIALOGUE_CONFIRM);
		TRAN_STATU_MESSAGE_MAP.put("2002-0-0-1", ResourceUtil4Cfets.getBondDlgResMessage4Confirm());
		/**
		 * 本方拒绝对手对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2002-0-5-7", OpTypeEnum.BOND_DIALOGUE_REJECT);
		TRAN_STATU_MESSAGE_MAP.put("2002-0-5-7", ResourceUtil4Cfets.getBondDlgResMessage4Reject());
		TRAN_STATU_OPTYPE_MAP.put("2002-0-0-7", OpTypeEnum.BOND_DIALOGUE_REJECT);
		TRAN_STATU_MESSAGE_MAP.put("2002-0-0-7", ResourceUtil4Cfets.getBondDlgResMessage4Reject());

		/**
		 * 对手新增债券对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2004-N-5-0", OpTypeEnum.BOND_DIALOGUE_ADD);
		TRAN_STATU_MESSAGE_MAP.put("2004-N-5-0", ResourceUtil4Cfets.getBondDlgRecvMessage4Add());
		/**
		 * 对手新增债券对话报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("2004-0-9-0", OpTypeEnum.BOND_DIALOGUE_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("2004-0-9-0", ResourceUtil4Cfets.getBondDlgRecvMessage4Expire());
		/**
		 * 对手修改对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2004-R-5-0", OpTypeEnum.BOND_DIALOGUE_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("2004-R-5-0", ResourceUtil4Cfets.getBondDlgRecvMessage4Update());

		/**
		 * 对手新增债券报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("2004-C-7-0", OpTypeEnum.BOND_DIALOGUE_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("2004-C-7-0", ResourceUtil4Cfets.getBondDlgRecvMessage4Cancel());

		/**
		 * 对手客户端修改本方报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2004-0-5-0", OpTypeEnum.BOND_DIALOGUE_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("2004-0-5-0", ResourceUtil4Cfets.getBondDlgRecvMessage4CounterClientUpdate());

		/**
		 * 本方新增质押式回购对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2001-N-5-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_ADD);
		TRAN_STATU_MESSAGE_MAP.put("2001-N-5-0", ResourceUtil4Cfets.getPledgeDlgResMessage4Add());
		TRAN_STATU_OPTYPE_MAP.put("2001-N-0-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_ADD);
		TRAN_STATU_MESSAGE_MAP.put("2001-N-0-0", ResourceUtil4Cfets.getPledgeDlgResMessage4Add());
		/**
		 * 本方修改质押式回购对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2001-R-5-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("2001-R-5-0", ResourceUtil4Cfets.getPledgeDlgResMessage4Update());
		TRAN_STATU_OPTYPE_MAP.put("2001-R-0-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("2001-R-0-0", ResourceUtil4Cfets.getPledgeDlgResMessage4Update());
		/**
		 * 本方撤销质押式回购对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2001-C-5-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("2001-C-5-0", ResourceUtil4Cfets.getPledgeDlgResMessage4Cancel());
		TRAN_STATU_OPTYPE_MAP.put("2001-C-0-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("2001-C-0-0", ResourceUtil4Cfets.getPledgeDlgResMessage4Cancel());

		/**
		 * 本方成交对手对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2003-0-5-1", OpTypeEnum.PLEDGEREPO_DIALOGUE_CONFIRM);
		TRAN_STATU_MESSAGE_MAP.put("2003-0-5-1", ResourceUtil4Cfets.getPledgeDlgResMessage4Confirm());
		TRAN_STATU_OPTYPE_MAP.put("2003-0-0-1", OpTypeEnum.PLEDGEREPO_DIALOGUE_CONFIRM);
		TRAN_STATU_MESSAGE_MAP.put("2003-0-0-1", ResourceUtil4Cfets.getPledgeDlgResMessage4Confirm());
		/**
		 * 本方拒绝对手对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2003-0-5-7", OpTypeEnum.PLEDGEREPO_DIALOGUE_REJECT);
		TRAN_STATU_MESSAGE_MAP.put("2003-0-5-7", ResourceUtil4Cfets.getPledgeDlgResMessage4Reject());
		TRAN_STATU_OPTYPE_MAP.put("2003-0-0-7", OpTypeEnum.PLEDGEREPO_DIALOGUE_REJECT);
		TRAN_STATU_MESSAGE_MAP.put("2003-0-0-7", ResourceUtil4Cfets.getPledgeDlgResMessage4Reject());

		/**
		 * 本方新增债券报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("2001-0-9-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("2001-0-9-0", ResourceUtil4Cfets.getPledgeDlgResMessage4Expire());
		/**
		 * 对手拒绝本方质押式回购对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2001-0-12-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_REJECT);
		TRAN_STATU_MESSAGE_MAP.put("2001-0-12-0", ResourceUtil4Cfets.getPledgeDlgRecvMessage4Reject());
		/**
		 * 对手成交本方质押式回购对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2001-0-11-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_CONFIRM);
		TRAN_STATU_MESSAGE_MAP.put("2001-0-11-0", ResourceUtil4Cfets.getPledgeDlgRecvMessage4Confirm());

		/**
		 * 对手新增质押式回购对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2005-N-5-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_ADD);
		TRAN_STATU_MESSAGE_MAP.put("2005-N-5-0", ResourceUtil4Cfets.getPledgeDlgRecvMessage4Add());
		/**
		 * 对手新增质押式回购对话报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("2005-0-9-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("2005-0-9-0", ResourceUtil4Cfets.getPledgeDlgRecvMessage4Expire());
		/**
		 * 对手修改质押式回购对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2005-R-5-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("2005-R-5-0", ResourceUtil4Cfets.getPledgeDlgRecvMessage4Update());

		/**
		 * 对手撤销质押式回购对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2005-C-7-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("2005-C-7-0", ResourceUtil4Cfets.getPledgeDlgRecvMessage4Cancel());

		/**
		 * 对手客户端修改本方质押式回购对话报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("2005-0-5-0", OpTypeEnum.PLEDGEREPO_DIALOGUE_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("2005-0-5-0", ResourceUtil4Cfets.getPledgeDlgRecvMessage4UpdateByCounterClient());

		/**
		 * 本方新增做市报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("3002-N-5-0", OpTypeEnum.BOND_MARKETMAKE_ADD);
		TRAN_STATU_MESSAGE_MAP.put("3002-N-5-0", ResourceUtil4Cfets.getMMMessage4Add());
		TRAN_STATU_OPTYPE_MAP.put("3002-N-0-0", OpTypeEnum.BOND_MARKETMAKE_ADD);
		TRAN_STATU_MESSAGE_MAP.put("3002-N-0-0", ResourceUtil4Cfets.getMMMessage4Add());
		/**
		 * 本方撤销做市报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("3002-C-5-0", OpTypeEnum.BOND_MARKETMAKE_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("3002-C-5-0", ResourceUtil4Cfets.getMMMessage4Cancel());
		TRAN_STATU_OPTYPE_MAP.put("3002-C-0-0", OpTypeEnum.BOND_MARKETMAKE_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("3002-C-0-0", ResourceUtil4Cfets.getMMMessage4Cancel());
		/**
		 * 本方新增债券报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("3002-0-9-0", OpTypeEnum.BOND_MARKETMAKE_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("3002-0-9-0", ResourceUtil4Cfets.getMMMessage4Expire());

		/**
		 * 本方新增限价报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("3000-N-5-0", OpTypeEnum.BOND_PRICELIMIT_ADD);
		TRAN_STATU_MESSAGE_MAP.put("3000-N-5-0", ResourceUtil4Cfets.getPLMessage4Add());
		TRAN_STATU_OPTYPE_MAP.put("3000-N-0-0", OpTypeEnum.BOND_PRICELIMIT_ADD);
		TRAN_STATU_MESSAGE_MAP.put("3000-N-0-0", ResourceUtil4Cfets.getPLMessage4Add());
		/**
		 * 本方撤销限价报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("3000-C-5-0", OpTypeEnum.BOND_PRICELIMIT_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("3000-C-5-0", ResourceUtil4Cfets.getPLMessage4Cancel());
		TRAN_STATU_OPTYPE_MAP.put("3000-C-0-0", OpTypeEnum.BOND_PRICELIMIT_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("3000-C-0-0", ResourceUtil4Cfets.getPLMessage4Cancel());
		/**
		 * 本方新增限价报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("3000-0-9-0", OpTypeEnum.BOND_PRICELIMIT_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("3000-0-9-0", ResourceUtil4Cfets.getPLMessage4Expire());

		/**
		 * 本方新增点击成交
		 */
		TRAN_STATU_OPTYPE_MAP.put("3001-N-5-0", OpTypeEnum.BOND_CLICKDEAL_ADD);
		TRAN_STATU_MESSAGE_MAP.put("3001-N-5-0", ResourceUtil4Cfets.getCDMessage4Add());
		TRAN_STATU_OPTYPE_MAP.put("3001-N-0-0", OpTypeEnum.BOND_CLICKDEAL_ADD);
		TRAN_STATU_MESSAGE_MAP.put("3001-N-0-0", ResourceUtil4Cfets.getCDMessage4Add());
		/**
		 * 本方撤销点击成交
		 */
		TRAN_STATU_OPTYPE_MAP.put("3001-C-5-0", OpTypeEnum.BOND_CLICKDEAL_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("3001-C-5-0", ResourceUtil4Cfets.getCDMessage4Cancel());
		TRAN_STATU_OPTYPE_MAP.put("3001-C-0-0", OpTypeEnum.BOND_CLICKDEAL_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("3001-C-0-0", ResourceUtil4Cfets.getCDMessage4Cancel());
		/**
		 * 本方新增债券报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("3001-0-9-0", OpTypeEnum.BOND_CLICKDEAL_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("3001-0-9-0", ResourceUtil4Cfets.getCDMessage4Expire());

		/**
		 * 本方新增请求报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4000-N-5-0", OpTypeEnum.BOND_RFQREQ_ADD);
		TRAN_STATU_MESSAGE_MAP.put("4000-N-5-0", ResourceUtil4Cfets.getBondRfqResMessage4Add());
		TRAN_STATU_OPTYPE_MAP.put("4000-N-0-0", OpTypeEnum.BOND_RFQREQ_ADD);
		TRAN_STATU_MESSAGE_MAP.put("4000-N-0-0", ResourceUtil4Cfets.getBondRfqResMessage4Add());

		/**
		 * 本方撤销请求报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4000-C-5-0", OpTypeEnum.BOND_RFQREQ_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("4000-C-5-0", ResourceUtil4Cfets.getBondRfqResMessage4Cancel());
		TRAN_STATU_OPTYPE_MAP.put("4000-C-0-0", OpTypeEnum.BOND_RFQREQ_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("4000-C-0-0", ResourceUtil4Cfets.getBondRfqResMessage4Cancel());

		/**
		 * 本方请求报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("4000-0-9-0", OpTypeEnum.BOND_RFQREQ_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("4000-0-9-0", ResourceUtil4Cfets.getBondRfqResMessage4Expire());

		/**
		 * 本方拒绝请求报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4002-0-5-0", OpTypeEnum.BOND_RFQREQ_REJECT);
		TRAN_STATU_MESSAGE_MAP.put("4002-0-5-0", ResourceUtil4Cfets.getBondRfqResMessage4Reject());
		TRAN_STATU_OPTYPE_MAP.put("4002-0-0-0", OpTypeEnum.BOND_RFQREQ_REJECT);
		TRAN_STATU_MESSAGE_MAP.put("4002-0-0-0", ResourceUtil4Cfets.getBondRfqResMessage4Reject());

		/**
		 * 对手新增请求报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4004-0-5-0", OpTypeEnum.BOND_RFQREQ_ADD);
		TRAN_STATU_MESSAGE_MAP.put("4004-0-5-0", ResourceUtil4Cfets.getBondRfqRecvMessage4Add());

		/**
		 * 对手撤销请求报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4004-0-7-0", OpTypeEnum.BOND_RFQREQ_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("4004-0-7-0", ResourceUtil4Cfets.getBondRfqRecvMessage4Cancel());

		/**
		 * 对手新增请求报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("4004-0-9-0", OpTypeEnum.BOND_RFQREQ_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("4004-0-9-0", ResourceUtil4Cfets.getBondRfqRecvMessage4Expired());

		/**
		 * 本方新增债券回复报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4001-N-5-0", OpTypeEnum.BOND_RFQREPLY_ADD);
		TRAN_STATU_MESSAGE_MAP.put("4001-N-5-0", ResourceUtil4Cfets.getBondReplyResMessage4Add());
		TRAN_STATU_OPTYPE_MAP.put("4001-N-0-0", OpTypeEnum.BOND_RFQREPLY_ADD);
		TRAN_STATU_MESSAGE_MAP.put("4001-N-0-0", ResourceUtil4Cfets.getBondReplyResMessage4Add());
		/**
		 * 本方修改债券回复报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4001-R-5-0", OpTypeEnum.BOND_RFQREPLY_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("4001-R-5-0", ResourceUtil4Cfets.getBondReplyResMessage4Update());
		TRAN_STATU_OPTYPE_MAP.put("4001-R-0-0", OpTypeEnum.BOND_RFQREPLY_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("4001-R-0-0", ResourceUtil4Cfets.getBondReplyResMessage4Update());
		/**
		 * 本方撤销债券回复报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4001-C-5-0", OpTypeEnum.BOND_RFQREPLY_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("4001-C-5-0", ResourceUtil4Cfets.getBondReplyResMessage4Cancel());
		TRAN_STATU_OPTYPE_MAP.put("4001-C-0-0", OpTypeEnum.BOND_RFQREPLY_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("4001-C-0-0", ResourceUtil4Cfets.getBondReplyResMessage4Cancel());

		/**
		 * 本方新增债券报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("4001-0-9-0", OpTypeEnum.BOND_RFQREPLY_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("4001-0-9-0", ResourceUtil4Cfets.getBondReplyResMessage4Expire());
		
		/**
		 * 对手成交本方回复报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4001-0-11-0", OpTypeEnum.BOND_RFQREPLY_CONFIRM);
		TRAN_STATU_MESSAGE_MAP.put("4001-0-11-0", ResourceUtil4Cfets.getBondReplyRecvMessage4Confirm());
		
		/**
		 * 本方成交对方债券回复报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4003-0-5-0", OpTypeEnum.BOND_RFQREPLY_CONFIRM);
		TRAN_STATU_MESSAGE_MAP.put("4003-0-5-0", ResourceUtil4Cfets.getBondReplyResMessage4Confirm());
		TRAN_STATU_OPTYPE_MAP.put("4003-0-0-0", OpTypeEnum.BOND_RFQREPLY_CONFIRM);
		TRAN_STATU_MESSAGE_MAP.put("4003-0-0-0", ResourceUtil4Cfets.getBondReplyResMessage4Confirm());

		/**
		 * 对方新增债券回复报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4005-N-5-0", OpTypeEnum.BOND_RFQREPLY_ADD);
		TRAN_STATU_MESSAGE_MAP.put("4005-N-5-0", ResourceUtil4Cfets.getBondReplyRecvMessage4Add());
		
		/**
		 * 对方修改债券回复报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4005-R-5-0", OpTypeEnum.BOND_RFQREPLY_UPDATE);
		TRAN_STATU_MESSAGE_MAP.put("4005-R-5-0", ResourceUtil4Cfets.getBondReplyRecvMessage4Update());

		/**
		 * 对方撤销债券回复报价
		 */
		TRAN_STATU_OPTYPE_MAP.put("4005-0-7-0", OpTypeEnum.BOND_RFQREPLY_CANCEL);
		TRAN_STATU_MESSAGE_MAP.put("4005-0-7-0", ResourceUtil4Cfets.getBondReplyRecvMessage4Cancel());

		/**
		 * 对方新增债券报价过期
		 */
		TRAN_STATU_OPTYPE_MAP.put("4005-0-9-0", OpTypeEnum.BOND_RFQREPLY_EXPIRED);
		TRAN_STATU_MESSAGE_MAP.put("4005-0-9-0", ResourceUtil4Cfets.getBondReplyRecvMessage4Expire());
		
	}

	@Override
	public OpTypeEnum getOptypeWithKey(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return TRAN_STATU_OPTYPE_MAP.get(key);
	}

	@Override
	public CfetsTradeMessage getMessageWithKey(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return TRAN_STATU_MESSAGE_MAP.get(key);
	}

	/**
	 * 根据message对象获取一个唯一的key值 action-quoteTransType-status-resType
	 * 
	 * action 存在则取当前值 否则为0 quoteTransType 存在则取当前值 否则为0 status 存在则取当前值 否则为0 resType
	 * 存在则取当前值 否则为0
	 * 
	 * @param message
	 * @return
	 */
	@Override
	public String generateUniqueKey(CfetsTradeMessage message) {
		return messageKeyGeneratorStrategy.generateKey(message);
	}


	@Override
	public TargetSystemEnum supportSys() {
		return TargetSystemEnum.CFETS;
	}

     
}
