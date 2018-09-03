package com.xquant.platform.component.mock.dto;

import java.util.Date;

import com.xquant.cfets.trade.protocol.message.CfetsTradeMessage;

/**
 * 用于模拟对手的报价消息
 * 
 * @author Administrator
 *
 */
public class CfetsMockMessageInfo extends MockMessageInfo<CfetsTradeMessage> {

	/**
	 * 原消息模板
	 */
	protected CfetsTradeMessage messageTempldate;

	/**
	 * 原报价 在修改 撤销 过期 等操作时必须获取到原报价的信息
	 */
	protected CfetsTradeMessage originMessage;
	/**
	 * 触发时间 新增 由外面传入 不能为null 修改 自动模拟 撤销 自动模拟 过期 自动模拟
	 */
	protected Date triggerTime;

}
