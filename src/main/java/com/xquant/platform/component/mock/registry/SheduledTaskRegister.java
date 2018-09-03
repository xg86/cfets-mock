package com.xquant.platform.component.mock.registry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 通过map记录对应QuoteId的任务返回结果future,当该报价被撤销或成交等变成终态时需要将该报价的所有任务进行清空
 * 
 * @author Administrator
 *
 */
public class SheduledTaskRegister {

	/**
	 * 主键为message的quoteid 值为定时任务
	 */
	private static Map<String, List<Future<?>>> futureTaskMap = new ConcurrentHashMap<String, List<Future<?>>>();

	private static Logger logger = LoggerFactory.getLogger(SheduledTaskRegister.class);

	/**
	 * 添加定时任务 同一个报价编号可能有多个任务在同时执行 比如新增一笔任务 此时有新增的任务 和 到期的任务 同时产生
	 * 
	 * @param quoteId
	 * @param future
	 */
	public static void registerTask(String quoteId, Future<?> future) {
		Assert.notNull(quoteId, "quoteId can not be null");
		Assert.notNull(future, "future can not be null");

		if (logger.isInfoEnabled()) {
			logger.info("register task which quoteid = " + quoteId);
		}

		synchronized (futureTaskMap) {
			if (futureTaskMap.get(quoteId) == null) {
				futureTaskMap.put(quoteId, new CopyOnWriteArrayList<Future<?>>());
			}
			futureTaskMap.get(quoteId).add(future);
		}
	}

	/**
	 * 删除定时任务
	 * 
	 * @param quoteId
	 */
	public static void removeTask(String quoteId) {
		synchronized (futureTaskMap) {
			if (futureTaskMap.containsKey(quoteId)) {
				List<Future<?>> list = futureTaskMap.get(quoteId);
				// 取消目前QuoteId对应的所有任务
				for (Future<?> future : list) {
					future.cancel(true);
				}
				if (logger.isInfoEnabled()) {
					logger.info("remove all tasks quoteid = " + quoteId);
				}
			}
		}
	}

	/**
	 * 清空
	 */
	public static void removeAllTask() {
		synchronized (futureTaskMap) {
			futureTaskMap.clear();
			if (logger.isInfoEnabled()) {
				logger.info("remove all sheduled tasks");
			}
		}
	}
}
