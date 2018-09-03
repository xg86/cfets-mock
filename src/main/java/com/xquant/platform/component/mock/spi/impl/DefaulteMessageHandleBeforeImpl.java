package com.xquant.platform.component.mock.spi.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessage;
import com.xquant.cfets.trade.protocol.message.CfetsTradeBondDialogueQuoteReceiveMessageBody;
import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;
import com.xquant.cfets.trade.protocol.util.CfetsTradeSerializeUtil;
import com.xquant.platform.component.mock.spi.FacadeMessageHandleBefore;
import com.xquant.platform.component.mock.util.MockDateTimeUtil;
import com.xquant.platform.component.mock.util.MyMethodUtils;
import com.xquant.platform.component.mock.util.ResourceUtil4Cfets;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.spi.impl
 * @author: guanglai.zhou
 * @date: 2018-07-25 16:21:31
 */
//@Component
public class DefaulteMessageHandleBeforeImpl implements FacadeMessageHandleBefore {

	/**
	 * 重新生成报价编号 客户端参考编号 发送时间 有效时间 业务时间 报文头序列号
	 */
	@Override
	public CfetsTradeMessage handleBefore(CfetsTradeMessage message) {
		try {
			Object body = MethodUtils.invokeMethod(message, "getBody");
			Map<String, String> generateParms = generateParms(new Date());
			message.getHeader().setSendingTime(generateParms.get(TRANSACTTIME));
			message.getHeader().setSerialNo(generateParms.get(SERIALNO));
			MyMethodUtils.setMethodValueIfExists(body, "setQuoteID", generateParms.get(QUOTEID), String.class);
			MyMethodUtils.setMethodValueIfExists(body, "setClOrdIDClientID", generateParms.get(CLORDIDCLIENTID),
					String.class);
			MyMethodUtils.setMethodValueIfExists(body, "setTransactTime", generateParms.get(TRANSACTTIME),
					String.class);
			MyMethodUtils.setMethodValueIfExists(body, "setValidUntilTime", generateParms.get(VALIDUNTILTIME),
					String.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return message;
	}

	private static final String QUOTEID = "quoteId";
	private static final String TRANSACTTIME = "transactTime";
	private static final String CLORDIDCLIENTID = "clOrdIDClientID";
	private static final String SERIALNO = "serialNo";
	private static final String VALIDUNTILTIME = "validUntilTime";
	private static final String CLORDID = "clOrdID";


	public static Map<String, String> generateParms(Date triggerTime) {
		Random rand = new Random();
		Map<String, String> map = new HashMap<String, String>();
		int suffix = rand.nextInt(9);
		// 填充业务时间
		map.put(TRANSACTTIME, new SimpleDateFormat("yyyyMMdd-HH:mm:ss.sss").format(triggerTime));
		// 生成报价编号
		String quoteId = new SimpleDateFormat("yyyyMMddHHmmsssss").format(triggerTime).concat(String.valueOf(suffix));
		map.put(QUOTEID, quoteId);
		// 生成客户端参考编号
		map.put(CLORDIDCLIENTID, "CFETS-" + quoteId);
		// 生成序列
		map.put(SERIALNO, quoteId);
		// 生成有效时间
		map.put(VALIDUNTILTIME, MockDateTimeUtil.timeAfterMinuteWithFormat(triggerTime, 30));
		// 生成原客户参考编号
		String clOrdID = "ODhzhttrd0" + new SimpleDateFormat("HHmmssss").format(triggerTime)
				.concat(String.valueOf(suffix)).concat(String.valueOf(suffix));
		map.put(CLORDID, clOrdID);
		return map;
	}

	public static void main(String[] args) {
		CfetsTradeBondDialogueQuoteReceiveMessage message = ResourceUtil4Cfets
				.getBondDlgRecvMessage4Add();
		CfetsTradeBondDialogueQuoteReceiveMessageBody body = message.getBody();
		Map<String, String> generateParms = generateParms(new Date());
		message.getHeader().setSendingTime(generateParms.get(TRANSACTTIME));
		message.getHeader().setSerialNo(generateParms.get(SERIALNO));
		MyMethodUtils.setMethodValueIfExists(body, "setQuoteID", generateParms.get(QUOTEID), String.class);
		MyMethodUtils.setMethodValueIfExists(body, "setClOrdIDClientID", generateParms.get(CLORDIDCLIENTID),
				String.class);
		MyMethodUtils.setMethodValueIfExists(body, "setTransactTime", generateParms.get(TRANSACTTIME),
				String.class);
		MyMethodUtils.setMethodValueIfExists(body, "setValidUntilTime", generateParms.get(VALIDUNTILTIME),
				String.class);
		System.out.println(CfetsTradeSerializeUtil.toXml(message));
	}

}
