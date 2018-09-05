package com.xquant.platform.component.mock.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondClickDealQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondMarketMakingQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQRejectResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyConfirmResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondRFQResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteReplyResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeCollateralDialogueQuoteResMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeExecutionReportMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.cfets.response
 * @author: guanglai.zhou
 * @date: 2018-05-18 13:12:14
 */
public class ResourceUtil4Cfets {

	public static Map<String, CfetsTradeMessage> typeMessageMap = new ConcurrentHashMap<String, CfetsTradeMessage>();

	/*
	 ****************************** 债券 对话报价*********************************************
	 */

	private final static String BONDDLG_RES_ADD_TYPE = "cfets/res/10102_CfetsTradeBondDialogueQuoteResMessage.xml";
	private final static String BONDDLG_RES_EXPIRE_TYPE = "cfets/res/10103_CfetsTradeBondDialogueQuoteResMessage.xml";
	private final static String BONDDLG_RES_UPDATE_TYPE = "cfets/res/10202_CfetsTradeBondDialogueQuoteResMessage.xml";
	private final static String BONDDLG_RES_CANCEL_TYPE = "cfets/res/10302_CfetsTradeBondDialogueQuoteResMessage.xml";
	private final static String BONDDLG_RES_CONFIRM_TYPE = "cfets/res/10402_CfetsTradeBondDialogueQuoteReplyResMessage.xml";
	private final static String BONDDLG_RES_REJECT_TYPE = "cfets/res/10502_CfetsTradeBondDialogueQuoteReplyResMessage.xml";
	private final static String BONDDLG_RECV_ADD_TYPE = "cfets/recv/11101_CfetsTradeBondDialogueQuoteReceiveMessage.xml";
	private final static String BONDDLG_RECV_EXPIRE_TYPE = "cfets/recv/11102_CfetsTradeBondDialogueQuoteReceiveMessage.xml";
	private final static String BONDDLG_RECV_UPDATE_TYPE = "cfets/recv/11201_CfetsTradeBondDialogueQuoteReceiveMessage.xml";
	private final static String BONDDLG_RECV_CANCEL_TYPE = "cfets/recv/11301_CfetsTradeBondDialogueQuoteReceiveMessage.xml";
	private final static String BONDDLG_RECV_CONFIRM_TYPE = "cfets/recv/11401_CfetsTradeBondDialogueQuoteResMessage.xml";
	private final static String BONDDLG_RECV_REJECT_TYPE = "cfets/recv/11501_CfetsTradeBondDialogueQuoteResMessage.xml";
	private final static String BONDDLG_RECV_UPDATEBYCOUNTER_TYPE = "cfets/recv/11601_CfetsTradeBondDialogueQuoteReceiveMessage.xml";

	/**
	 * 债券对话报价本方API新增异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteResMessage getBondDlgResMessage4Add() {
		CfetsTradeBondDialogueQuoteResMessage message = null;
		String fileName = BONDDLG_RES_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价本方报价过期(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteResMessage getBondDlgResMessage4Expire() {
		CfetsTradeBondDialogueQuoteResMessage message = null;
		String fileName = BONDDLG_RES_EXPIRE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价本方API修改异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteResMessage getBondDlgResMessage4Update() {
		CfetsTradeBondDialogueQuoteResMessage message = null;
		String fileName = BONDDLG_RES_UPDATE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价本方API撤销异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteResMessage getBondDlgResMessage4Cancel() {
		CfetsTradeBondDialogueQuoteResMessage message = null;
		String fileName = BONDDLG_RES_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价本方API成交异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteReplyResMessage getBondDlgResMessage4Confirm() {
		CfetsTradeBondDialogueQuoteReplyResMessage message = null;

		String fileName = BONDDLG_RES_CONFIRM_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteReplyResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteReplyResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价本方API拒绝异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteReplyResMessage getBondDlgResMessage4Reject() {
		CfetsTradeBondDialogueQuoteReplyResMessage message = null;

		String fileName = BONDDLG_RES_REJECT_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteReplyResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteReplyResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价对手新增报价(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteReceiveMessage getBondDlgRecvMessage4Add() {
		CfetsTradeBondDialogueQuoteReceiveMessage message = null;

		String fileName = BONDDLG_RECV_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价对手新增报价过期(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteReceiveMessage getBondDlgRecvMessage4Expire() {
		CfetsTradeBondDialogueQuoteReceiveMessage message = null;

		String fileName = BONDDLG_RECV_EXPIRE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价对手修改报价(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteReceiveMessage getBondDlgRecvMessage4Update() {
		CfetsTradeBondDialogueQuoteReceiveMessage message = null;

		String fileName = BONDDLG_RECV_UPDATE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价对手撤销报价(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteReceiveMessage getBondDlgRecvMessage4Cancel() {
		CfetsTradeBondDialogueQuoteReceiveMessage message = null;

		String fileName = BONDDLG_RECV_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价对手成交报价(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteResMessage getBondDlgRecvMessage4Confirm() {
		CfetsTradeBondDialogueQuoteResMessage message = null;
		String fileName = BONDDLG_RECV_CONFIRM_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价对手拒绝报价(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteResMessage getBondDlgRecvMessage4Reject() {
		CfetsTradeBondDialogueQuoteResMessage message = null;
		String fileName = BONDDLG_RECV_REJECT_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券对话报价对手客户端修改本方报价(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondDialogueQuoteReceiveMessage getBondDlgRecvMessage4CounterClientUpdate() {
		CfetsTradeBondDialogueQuoteReceiveMessage message = null;

		String fileName = BONDDLG_RECV_UPDATEBYCOUNTER_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondDialogueQuoteReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondDialogueQuoteReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	// private final static String BONDDLG_RECV_UPDATEBYCLIENT_TYPE =
	// "cfets/client/12201_CfetsTradeBondDialogueQuoteResMessage.xml";
	//
	// /**
	// * 债券对话报价本方客户端修改本方报价(P)
	// *
	// * @return
	// */
	// public static CfetsTradeBondDialogueQuoteResMessage
	// getBondDlgRecvMessage4SelfClientUpdate() {
	// CfetsTradeBondDialogueQuoteResMessage message = null;
	//
	// String fileName = BONDDLG_RECV_UPDATEBYCLIENT_TYPE;
	// synchronized (typeMessageMap) {
	// if (typeMessageMap.get(fileName) != null) {
	// return (CfetsTradeBondDialogueQuoteResMessage) typeMessageMap.get(fileName);
	// }
	// }
	// message = (CfetsTradeBondDialogueQuoteResMessage)
	// XmlUtil.getInstanceFormXml(fileName);
	// synchronized (typeMessageMap) {
	// typeMessageMap.put(fileName, message);
	// }
	// return message;
	// }

	/*
	 ****************************** 质押式回购对话报价*********************************************
	 */

	private final static String PLEDGE_RES_ADD_TYPE = "cfets/res/20102_CfetsTradeCollateralDialogueQuoteResMessage.xml";
	private final static String PLEDGE_RES_EXPIRE_TYPE = "cfets/res/20103_CfetsTradeCollateralDialogueQuoteResMessage.xml";
	private final static String PLEDGE_RES_UPDATE_TYPE = "cfets/res/20202_CfetsTradeCollateralDialogueQuoteResMessage.xml";
	private final static String PLEDGE_RES_CANCEL_TYPE = "cfets/res/20302_CfetsTradeCollateralDialogueQuoteResMessage.xml";
	private final static String PLEDGE_RES_CONFIRM_TYPE = "cfets/res/20402_CfetsTradeCollateralDialogueQuoteReplyResMessage.xml";
	private final static String PLEDGE_RES_REJECT_TYPE = "cfets/res/20502_CfetsTradeCollateralDialogueQuoteReplyResMessage.xml";
	private final static String PLEDGE_RECV_ADD_TYPE = "cfets/recv/21101_CfetsTradeCollateralDialogueQuoteReceiveMessage.xml";
	private final static String PLEDGE_RECV_EXPIRE_TYPE = "cfets/recv/21102_CfetsTradeCollateralDialogueQuoteReceiveMessage.xml";
	private final static String PLEDGE_RECV_UPDATE_TYPE = "cfets/recv/21201_CfetsTradeCollateralDialogueQuoteReceiveMessage.xml";
	private final static String PLEDGE_RECV_CANCEL_TYPE = "cfets/recv/21301_CfetsTradeCollateralDialogueQuoteReceiveMessage.xml";
	private final static String PLEDGE_RECV_CONFIRM_TYPE = "cfets/recv/21401_CfetsTradeCollateralDialogueQuoteResMessage.xml";
	private final static String PLEDGE_RECV_REJECT_TYPE = "cfets/recv/21501_CfetsTradeCollateralDialogueQuoteResMessage.xml";
	private final static String PLEDGE_RECV_UPDATECOUNTERCLIENT_TYPE = "cfets/recv/21601_CfetsTradeCollateralDialogueQuoteReceiveMessage.xml";
	// private final static String PLEDGE_RECV_UPDATECLIENT_TYPE =
	// "cfets/recv/22201_CfetsTradeCollateralDialogueQuoteResMessage.xml";

	/**
	 * 质押式回购对话报价本方API新增异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteResMessage getPledgeDlgResMessage4Add() {
		CfetsTradeCollateralDialogueQuoteResMessage message = null;
		String fileName = PLEDGE_RES_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价本方API过期异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteResMessage getPledgeDlgResMessage4Expire() {
		CfetsTradeCollateralDialogueQuoteResMessage message = null;

		String fileName = PLEDGE_RES_EXPIRE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价本方API修改异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteResMessage getPledgeDlgResMessage4Update() {
		CfetsTradeCollateralDialogueQuoteResMessage message = null;

		String fileName = PLEDGE_RES_UPDATE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价本方API撤销异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteResMessage getPledgeDlgResMessage4Cancel() {
		CfetsTradeCollateralDialogueQuoteResMessage message = null;

		String fileName = PLEDGE_RES_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价本方API成交异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteReplyResMessage getPledgeDlgResMessage4Confirm() {
		CfetsTradeCollateralDialogueQuoteReplyResMessage message = null;

		String fileName = PLEDGE_RES_CONFIRM_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteReplyResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteReplyResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价本方API拒绝异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteReplyResMessage getPledgeDlgResMessage4Reject() {
		CfetsTradeCollateralDialogueQuoteReplyResMessage message = null;

		String fileName = PLEDGE_RES_REJECT_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteReplyResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteReplyResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价对手新增(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteReceiveMessage getPledgeDlgRecvMessage4Add() {
		CfetsTradeCollateralDialogueQuoteReceiveMessage message = null;

		String fileName = PLEDGE_RECV_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价对手过期(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteReceiveMessage getPledgeDlgRecvMessage4Expire() {
		CfetsTradeCollateralDialogueQuoteReceiveMessage message = null;

		String fileName = PLEDGE_RECV_EXPIRE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价对手修改(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteReceiveMessage getPledgeDlgRecvMessage4Update() {
		CfetsTradeCollateralDialogueQuoteReceiveMessage message = null;

		String fileName = PLEDGE_RECV_UPDATE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价对手撤销(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteReceiveMessage getPledgeDlgRecvMessage4Cancel() {
		CfetsTradeCollateralDialogueQuoteReceiveMessage message = null;

		String fileName = PLEDGE_RECV_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价对手成交(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteResMessage getPledgeDlgRecvMessage4Confirm() {
		CfetsTradeCollateralDialogueQuoteResMessage message = null;

		String fileName = PLEDGE_RECV_CONFIRM_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价对手拒绝(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteResMessage getPledgeDlgRecvMessage4Reject() {
		CfetsTradeCollateralDialogueQuoteResMessage message = null;

		String fileName = PLEDGE_RECV_REJECT_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 质押式回购对话报价对手客户端修改本方报价(P)
	 * 
	 * @return
	 */
	public static CfetsTradeCollateralDialogueQuoteReceiveMessage getPledgeDlgRecvMessage4UpdateByCounterClient() {
		CfetsTradeCollateralDialogueQuoteReceiveMessage message = null;

		String fileName = PLEDGE_RECV_UPDATECOUNTERCLIENT_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeCollateralDialogueQuoteReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeCollateralDialogueQuoteReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	// /**
	// * 本方客户端修改本方报价
	// *
	// * @return @
	// */
	// public static CfetsTradeCollateralDialogueQuoteResMessage
	// getPledgeDlgRecvMessage4UpdateByClient() {
	// CfetsTradeCollateralDialogueQuoteResMessage message = null;
	//
	// String fileName = PLEDGE_RECV_UPDATECLIENT_TYPE;
	// synchronized (typeMessageMap) {
	// if (typeMessageMap.get(fileName) != null) {
	// return (CfetsTradeCollateralDialogueQuoteResMessage)
	// typeMessageMap.get(fileName);
	// }
	// }
	// message = (CfetsTradeCollateralDialogueQuoteResMessage)
	// XmlUtil.getInstanceFormXml(fileName);
	// synchronized (typeMessageMap) {
	// typeMessageMap.put(fileName, message);
	// }
	// return message;
	// }

	/*
	 ****************************** 债券请求报价*********************************************
	 */

	private final static String RFQ_RES_ADD_TYPE = "cfets/res/30102_CfetsTradeBondRFQResMessage.xml";
	private final static String RFQ_RES_EXPIRE_TYPE = "cfets/res/30103_CfetsTradeBondRFQResMessage.xml";
	private final static String RFQ_RES_ALL_DEALED_TYPE = "cfets/res/30103_CfetsTradeBondRFQResMessage.xml";
	private final static String RFQ_RES_CANCEL_TYPE = "cfets/res/30202_CfetsTradeBondRFQResMessage.xml";
	private final static String RFQ_RES_REJECT_TYPE = "cfets/res/30302_CfetsTradeBondRFQRejectResMessage.xml";

	private final static String RFQ_RECV_ADD_TYPE = "cfets/recv/31101_CfetsTradeBondRFQReceiveMessage.xml";
	private final static String RFQ_RECV_EXPIRED_TYPE = "cfets/recv/31102_CfetsTradeBondRFQReceiveMessage.xml";
	private final static String RFQ_RECV_ALL_DEALED_TYPE = "cfets/recv/31103_CfetsTradeBondRFQReceiveMessage.xml";
	private final static String RFQ_RECV_CANCEL_TYPE = "cfets/recv/31201_CfetsTradeBondRFQReceiveMessage.xml";

	/**
	 * 债券请求报价本方API新增异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQResMessage getBondRfqResMessage4Add() {
		CfetsTradeBondRFQResMessage message = null;

		String fileName = RFQ_RES_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券请求报价本方API过期异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQResMessage getBondRfqResMessage4Expire() {
		CfetsTradeBondRFQResMessage message = null;

		String fileName = RFQ_RES_EXPIRE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券请求报价本方报价全部成交(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQResMessage getBondRfqResMessage4AllDealed() {
		CfetsTradeBondRFQResMessage message = null;

		String fileName = RFQ_RES_ALL_DEALED_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券请求报价本方API撤销异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQResMessage getBondRfqResMessage4Cancel() {
		CfetsTradeBondRFQResMessage message = null;

		String fileName = RFQ_RES_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券请求报价本方API拒绝异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQRejectResMessage getBondRfqResMessage4Reject() {

		CfetsTradeBondRFQRejectResMessage message = null;

		String fileName = RFQ_RES_REJECT_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQRejectResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQRejectResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券请求报价对方新增(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReceiveMessage getBondRfqRecvMessage4Add() {

		CfetsTradeBondRFQReceiveMessage message = null;

		String fileName = RFQ_RECV_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券请求报价对方过期(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReceiveMessage getBondRfqRecvMessage4Expired() {
		CfetsTradeBondRFQReceiveMessage message = null;

		String fileName = RFQ_RECV_EXPIRED_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券请求报价对方全部成交(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReceiveMessage getBondRfqRecvMessage4AllDealed() {
		CfetsTradeBondRFQReceiveMessage message = null;

		String fileName = RFQ_RECV_ALL_DEALED_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券请求报价对方撤销(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReceiveMessage getBondRfqRecvMessage4Cancel() {
		CfetsTradeBondRFQReceiveMessage message = null;

		String fileName = RFQ_RECV_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReceiveMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/*
	 ****************************** 债券请求回复报价*********************************************
	 */
	private final static String REPLY_RES_ADD_TYPE = "cfets/res/30402_CfetsTradeBondRFQReplyResMessage.xml";
	private final static String REPLY_RES_EXPIRE_TYPE = "cfets/res/30403_CfetsTradeBondRFQReplyResMessage.xml";
	private final static String REPLY_RES_UPDATE_TYPE = "cfets/res/30502_CfetsTradeBondRFQReplyResMessage.xml";
	private final static String REPLY_RES_CANCEL_TYPE = "cfets/res/30602_CfetsTradeBondRFQReplyResMessage.xml";
	private final static String REPLY_RES_CONFIRM_TYPE = "cfets/res/30702_CfetsTradeBondRFQReplyConfirmResMessage.xml";

	private final static String REPLY_RECV_ADD_TYPE = "cfets/recv/31401_CfetsTradeBondRFQReplyReceiveMessage.xml";
	private final static String REPLY_RECV_EXPIRE_TYPE = "cfets/recv/31402_CfetsTradeBondRFQReplyReceiveMessage.xml";
	private final static String REPLY_RECV_UPDATE_TYPE = "cfets/recv/31501_CfetsTradeBondRFQReplyReceiveMessage.xml";
	private final static String REPLY_RECV_CANCEL_TYPE = "cfets/recv/31601_CfetsTradeBondRFQReplyReceiveMessage.xml";
	private final static String REPLY_RECV_CONFIRM_TYPE = "cfets/recv/31701_CfetsTradeBondRFQReplyResMessage.xml";

	/**
	 * 债券请求回复报价本方API新增异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReplyResMessage getBondReplyResMessage4Add() {

		CfetsTradeBondRFQReplyResMessage message = null;

		String fileName = REPLY_RES_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReplyResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReplyResMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 债券请求回复报价本方API过期异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReplyResMessage getBondReplyResMessage4Expire() {

		CfetsTradeBondRFQReplyResMessage message = null;

		String fileName = REPLY_RES_EXPIRE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReplyResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReplyResMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 债券请求回复报价本方API修改异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReplyResMessage getBondReplyResMessage4Update() {

		CfetsTradeBondRFQReplyResMessage message = null;

		String fileName = REPLY_RES_UPDATE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReplyResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReplyResMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 债券请求回复报价本方API撤销异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReplyResMessage getBondReplyResMessage4Cancel() {

		CfetsTradeBondRFQReplyResMessage message = null;

		String fileName = REPLY_RES_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReplyResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReplyResMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 债券请求回复报价本方API成交异步返回(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReplyConfirmResMessage getBondReplyResMessage4Confirm() {

		CfetsTradeBondRFQReplyConfirmResMessage message = null;

		String fileName = REPLY_RES_CONFIRM_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReplyConfirmResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReplyConfirmResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 债券请求回复报价对手新增(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReplyReceiveMessage getBondReplyRecvMessage4Add() {

		CfetsTradeBondRFQReplyReceiveMessage message = null;

		String fileName = REPLY_RECV_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReplyReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReplyReceiveMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 债券请求回复报价对手过期(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReplyReceiveMessage getBondReplyRecvMessage4Expire() {

		CfetsTradeBondRFQReplyReceiveMessage message = null;

		String fileName = REPLY_RECV_EXPIRE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReplyReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReplyReceiveMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 债券请求回复报价对手修改(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReplyReceiveMessage getBondReplyRecvMessage4Update() {

		CfetsTradeBondRFQReplyReceiveMessage message = null;

		String fileName = REPLY_RECV_UPDATE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReplyReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReplyReceiveMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 债券请求回复报价对手撤销(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReplyReceiveMessage getBondReplyRecvMessage4Cancel() {

		CfetsTradeBondRFQReplyReceiveMessage message = null;

		String fileName = REPLY_RECV_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReplyReceiveMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReplyReceiveMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 债券请求回复报价对手成交(P)
	 * 
	 * @return
	 */
	public static CfetsTradeBondRFQReplyResMessage getBondReplyRecvMessage4Confirm() {

		CfetsTradeBondRFQReplyResMessage message = null;

		String fileName = REPLY_RECV_CONFIRM_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondRFQReplyResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondRFQReplyResMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 本方新增做市报价
	 */
	private final static String MM_RES_ADD_TYPE = "cfets/res/40102_CfetsTradeBondMarketMakingQuoteResMessage.xml";

	/**
	 * 本方做市报价过期
	 */
	private final static String MM_RES_EXPIRE_TYPE = "cfets/res/40103_CfetsTradeBondMarketMakingQuoteResMessage.xml";

	/**
	 * 本方做市报价部分成交
	 */
	private final static String MM_RES_PARTDELA_TYPE = "cfets/res/40104_CfetsTradeBondMarketMakingQuoteResMessage.xml";

	/**
	 * 本方新增做市报价
	 */
	private final static String MM_RES_CANCEL_TYPE = "cfets/res/40202_CfetsTradeBondMarketMakingQuoteResMessage.xml";

	/**
	 * 本方新增限价报价
	 */
	private final static String PL_RES_ADD_TYPE = "cfets/res/50102_CfetsTradeBondPriceLimitQuoteResMessage.xml";

	/**
	 * 本方限价报价过期
	 */
	private final static String PL_RES_EXPIRE_TYPE = "cfets/res/50103_CfetsTradeBondPriceLimitQuoteResMessage.xml";

	/**
	 * 本方新增限价报价
	 */
	private final static String PL_RES_CANCEL_TYPE = "cfets/res/50202_CfetsTradeBondPriceLimitQuoteResMessage.xml";

	/**
	 * 本方新增点击成交
	 */
	private final static String CD_RES_ADD_TYPE = "cfets/res/60102_CfetsTradeBondClickDealQuoteResMessage.xml";

	/**
	 * 本方点击成交过期
	 */
	private final static String CD_RES_EXPIRE_TYPE = "cfets/res/60103_CfetsTradeBondClickDealQuoteResMessage.xml";

	/**
	 * 本方点击成交过期
	 */
	private final static String CD_RES_CANCEL_TYPE = "cfets/res/60202_CfetsTradeBondClickDealQuoteResMessage.xml";

	/**
	 * 做市报价部分成交
	 * 
	 * @return @
	 */
	public static CfetsTradeBondMarketMakingQuoteResMessage getBondMarketMaker4PartDealed() {
		CfetsTradeBondMarketMakingQuoteResMessage message = null;

		String fileName = MM_RES_PARTDELA_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondMarketMakingQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondMarketMakingQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 本方新增做市报价异步返回成功
	 * 
	 * @return @
	 */
	public static CfetsTradeBondMarketMakingQuoteResMessage getMMMessage4Add() {
		CfetsTradeBondMarketMakingQuoteResMessage message = null;

		String fileName = MM_RES_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondMarketMakingQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondMarketMakingQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方新增做市报价过期
	 * 
	 * @return @
	 */
	public static CfetsTradeBondMarketMakingQuoteResMessage getMMMessage4Expire() {
		CfetsTradeBondMarketMakingQuoteResMessage message = null;

		String fileName = MM_RES_EXPIRE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondMarketMakingQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondMarketMakingQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方撤销做市报价异步返回成功
	 * 
	 * @return @
	 */
	public static CfetsTradeBondMarketMakingQuoteResMessage getMMMessage4Cancel() {
		CfetsTradeBondMarketMakingQuoteResMessage message = null;

		String fileName = MM_RES_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondMarketMakingQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondMarketMakingQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方新增限价报价异步返回成功
	 * 
	 * @return @
	 */
	public static CfetsTradeBondPriceLimitQuoteResMessage getPLMessage4Add() {
		CfetsTradeBondPriceLimitQuoteResMessage message = null;

		String fileName = PL_RES_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondPriceLimitQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondPriceLimitQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方新增限价报价过期
	 * 
	 * @return @
	 */
	public static CfetsTradeBondPriceLimitQuoteResMessage getPLMessage4Expire() {
		CfetsTradeBondPriceLimitQuoteResMessage message = null;

		String fileName = PL_RES_EXPIRE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondPriceLimitQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondPriceLimitQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方撤销限价报价异步返回成功
	 * 
	 * @return @
	 */
	public static CfetsTradeBondPriceLimitQuoteResMessage getPLMessage4Cancel() {
		CfetsTradeBondPriceLimitQuoteResMessage message = null;

		String fileName = PL_RES_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondPriceLimitQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondPriceLimitQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方新增点击成交报价异步返回成功
	 * 
	 * @return @
	 */
	public static CfetsTradeBondClickDealQuoteResMessage getCDMessage4Add() {
		CfetsTradeBondClickDealQuoteResMessage message = null;

		String fileName = CD_RES_ADD_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondClickDealQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondClickDealQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方新增点击成交过期
	 * 
	 * @return @
	 */
	public static CfetsTradeBondClickDealQuoteResMessage getCDMessage4Expire() {
		CfetsTradeBondClickDealQuoteResMessage message = null;

		String fileName = CD_RES_EXPIRE_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondClickDealQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondClickDealQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/**
	 * 本方撤销点击成交异步返回成功
	 * 
	 * @return @
	 */
	public static CfetsTradeBondClickDealQuoteResMessage getCDMessage4Cancel() {
		CfetsTradeBondClickDealQuoteResMessage message = null;

		String fileName = CD_RES_CANCEL_TYPE;
		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeBondClickDealQuoteResMessage) typeMessageMap.get(fileName);
			}
		}
		message = (CfetsTradeBondClickDealQuoteResMessage) XmlUtil.getInstanceFormXml(fileName);
		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}
		return message;
	}

	/*
	 * 成交回报
	 */

	private final static String DLG_RES_EXECUTION_REPORT = "cfets/recv/80101_CfetsTradeExecutionReportMessage.xml";
	private final static String PLEDGE_RES_EXECUTION_REPORT = "cfets/recv/80201_CfetsTradeExecutionReportMessage.xml";
	private final static String REPLY_RES_EXECUTION_REPORT = "cfets/recv/80601_CfetsTradeExecutionReportMessage.xml";
	private final static String DLG_RECV_EXECUTION_REPORT = "cfets/recv/80102_CfetsTradeExecutionReportMessage.xml";
	private final static String PLEDGE_RECV_EXECUTION_REPORT = "cfets/recv/80202_CfetsTradeExecutionReportMessage.xml";
	private final static String REPLY_RECV_EXECUTION_REPORT = "cfets/recv/80602_CfetsTradeExecutionReportMessage.xml";

	/**
	 * 本方对话报价成交回报
	 * 
	 * @return
	 */
	public static CfetsTradeExecutionReportMessage getExcutionReport4BonddlgRes() {
		CfetsTradeExecutionReportMessage message = null;

		String fileName = DLG_RES_EXECUTION_REPORT;

		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeExecutionReportMessage) typeMessageMap.get(fileName);
			}
		}

		message = (CfetsTradeExecutionReportMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 本方质押式回购对话报价成交回报
	 * 
	 * @return
	 */
	public static CfetsTradeExecutionReportMessage getExcutionReport4PledgeBondRes() {
		CfetsTradeExecutionReportMessage message = null;

		String fileName = PLEDGE_RES_EXECUTION_REPORT;

		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeExecutionReportMessage) typeMessageMap.get(fileName);
			}
		}

		message = (CfetsTradeExecutionReportMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 本方请求回复报价成交回报
	 * 
	 * @return
	 */
	public static CfetsTradeExecutionReportMessage getExcutionReport4ReplyRes() {
		CfetsTradeExecutionReportMessage message = null;

		String fileName = REPLY_RES_EXECUTION_REPORT;

		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeExecutionReportMessage) typeMessageMap.get(fileName);
			}
		}

		message = (CfetsTradeExecutionReportMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 对手对话报价成交回报
	 * 
	 * @return
	 */
	public static CfetsTradeExecutionReportMessage getExcutionReport4BonddlgRecv() {
		CfetsTradeExecutionReportMessage message = null;

		String fileName = DLG_RECV_EXECUTION_REPORT;

		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeExecutionReportMessage) typeMessageMap.get(fileName);
			}
		}

		message = (CfetsTradeExecutionReportMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 对手质押式回购成交回报
	 * 
	 * @return
	 */
	public static CfetsTradeExecutionReportMessage getExcutionReport4PledgeBondRecv() {
		CfetsTradeExecutionReportMessage message = null;

		String fileName = PLEDGE_RECV_EXECUTION_REPORT;

		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeExecutionReportMessage) typeMessageMap.get(fileName);
			}
		}

		message = (CfetsTradeExecutionReportMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**
	 * 对手请求回复报价成交回报
	 * 
	 * @return
	 */
	public static CfetsTradeExecutionReportMessage getExcutionReport4ReplyRecv() {
		CfetsTradeExecutionReportMessage message = null;

		String fileName = REPLY_RECV_EXECUTION_REPORT;

		synchronized (typeMessageMap) {
			if (typeMessageMap.get(fileName) != null) {
				return (CfetsTradeExecutionReportMessage) typeMessageMap.get(fileName);
			}
		}

		message = (CfetsTradeExecutionReportMessage) XmlUtil.getInstanceFormXml(fileName);

		synchronized (typeMessageMap) {
			typeMessageMap.put(fileName, message);
		}

		return message;
	}

	/**************************************************************************************************/

}
