/**
 * 
 */
package com.xquant.platform.component.back.test.base;

import java.util.concurrent.TimeUnit;

/**
 * @author guanglai.zhou
 * @date 2018年4月24日
 *       <p>
 *       description
 *       </p>
 */
public class MyTestWaiter {
	
	public static void testWaitBefore() {
		// 休眠两秒钟等待长连接完成再发送报价
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static void testWaitAfter() {
		// 等待外汇交易中心返回信息
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
