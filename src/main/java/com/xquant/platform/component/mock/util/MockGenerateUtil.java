package com.xquant.platform.component.mock.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-08-18 12:53:08
 */
public class MockGenerateUtil {

	public static final SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");

	public synchronized static String getNextSerialNoByDate() {
		return date_format.format(new Date()) + MockPropertiesUtil.getValueAndThenIncrease("serialNo");
	}

	public synchronized static String getNextQuoteIdByDate() {
		return date_format.format(new Date()) + MockPropertiesUtil.getValueAndThenIncrease("quoteId");
	}

	public synchronized static String getNextClOrdID() {
		return MockPropertiesUtil.getValueAndThenIncrease("clOrdID");
	}

	public synchronized static String getNextSysReqID() {
		return MockPropertiesUtil.getValueAndThenIncrease("sysReqID");
	}

	public synchronized static String getNextOrderRequestID() {
		return "ORDE" + date_format.format(new Date()) + MockPropertiesUtil.getValueAndThenIncrease("orderRequestID");
	}

	public synchronized static String getNextOperateSeqNum() {
		return "XT" + date_format.format(new Date()) + MockPropertiesUtil.getValueAndThenIncrease("operateSeqNum");
	}
	
	public synchronized static String getNextExecId() {
		return date_format.format(new Date()) + MockPropertiesUtil.getValueAndThenIncrease("execID");
	}

	public static void main(String[] args) {
		System.out.println(MockGenerateUtil.getNextSerialNoByDate());
		System.err.println(MockGenerateUtil.getNextQuoteIdByDate());
		System.err.println(MockGenerateUtil.getNextClOrdID());
	}
}
