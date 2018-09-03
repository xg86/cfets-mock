package com.xquant.platform.component.mock.dto;

import java.util.concurrent.TimeUnit;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;

public abstract class MockMessageInfo<T> {

	/**
	 * 报价编号
	 */
	protected String quoteId;

	/**
	 * 最终实体消息
	 */
	protected T message;

	/**
	 * 延迟时间
	 */
	protected Long delay;

	/**
	 * 延迟时间单位
	 */
	protected TimeUnit timeUnit;

	/**
	 * 操作类型
	 */
	protected OpTypeEnum optype;

	/**
	 * 目标系统类型
	 */
	protected TargetSystemEnum targetSys;
	
	/**
	 * 线程名称
	 */
    protected String threadName;
    
	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public OpTypeEnum getOptype() {
		return optype;
	}

	public void setOptype(OpTypeEnum optype) {
		this.optype = optype;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}

	public Long getDelay() {
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public TargetSystemEnum getTargetSys() {
		return targetSys;
	}

	public void setTargetSys(TargetSystemEnum targetSys) {
		this.targetSys = targetSys;
	}

	@Override
	public String toString() {
		return "MockMessageInfo [quoteId=" + quoteId + ", message=" + message + ", delay=" + delay + ", timeUnit="
				+ timeUnit + ", optype=" + optype + ", targetSys=" + targetSys + ", threadName=" + threadName + "]";
	}
}
