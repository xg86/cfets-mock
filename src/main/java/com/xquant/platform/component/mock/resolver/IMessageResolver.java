package com.xquant.platform.component.mock.resolver;

import com.xquant.platform.component.itf.cfets.common.api.enums.OpTypeEnum;
import com.xquant.platform.component.itf.cfets.common.api.enums.TargetSystemEnum;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.resolver
 * @author: guanglai.zhou
 * @date: 2018-07-24 11:52:22
 */
public interface IMessageResolver<T> {

	/**
	 * 根据报价消息类生成唯一的key值
	 * 
	 * @param message
	 * @return
	 */
	String generateUniqueKey(T message);

	/**
	 * 根据key值查找报价的optype
	 * 
	 * @param key
	 * @return
	 */
	OpTypeEnum getOptypeWithKey(String key);

	/**
	 * 根据key值获取报价的模板
	 * 
	 * @param key
	 * @return
	 */
	T getMessageWithKey(String key);

	/**
	 * 支持的类型
	 * 
	 * @param cls
	 * @return
	 */
	public TargetSystemEnum supportSys();
}
