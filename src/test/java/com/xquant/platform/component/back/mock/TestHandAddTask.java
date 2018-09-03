/**
 * 
 */
package com.xquant.platform.component.back.mock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xquant.cfets.trade.protocol.util.CfetsTradeSerializeUtil;
import com.xquant.platform.component.mock.spi.FacadeMessageFromCounterMock;
import com.xquant.platform.component.mock.util.ResourceUtil4Cfets;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.back.mock
 * @author: guanglai.zhou
 * @date: 2018-07-19 16:49:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicaitonMockContext.xml")
public class TestHandAddTask {

	@Autowired
	private FacadeMessageFromCounterMock facadeMessageFromCounterMock;

	public static final String message1 = CfetsTradeSerializeUtil.toXml(ResourceUtil4Cfets.getBondDlgRecvMessage4Add());
	public static final String message2 = CfetsTradeSerializeUtil
			.toXml(ResourceUtil4Cfets.getPledgeDlgRecvMessage4Add());
	public static final String message3 = CfetsTradeSerializeUtil.toXml(ResourceUtil4Cfets.getBondRfqRecvMessage4Add());
	public static final String message4 = CfetsTradeSerializeUtil
			.toXml(ResourceUtil4Cfets.getBondReplyRecvMessage4Add());

	public static void main(String[] args) {
		// String action = StringUtils.substringBetween("<action>", "</action>");
		String action = StringUtils.substringBetween(message1, "<action>", "</action>");
		System.out.println(action);
	}

	@Test
	public void test() throws Exception {

		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);

		newScheduledThreadPool.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				facadeMessageFromCounterMock.handleMessage(message1);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				facadeMessageFromCounterMock.handleMessage(message2);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				facadeMessageFromCounterMock.handleMessage(message3);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				facadeMessageFromCounterMock.handleMessage(message4);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}, 1, 1, TimeUnit.MINUTES);

		Thread.currentThread().join();

	}
}
