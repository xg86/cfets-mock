package com.xquant.platform.component.mock.persist;

public interface IMessagePersistServiceManager<T> {

	/**
	 * 根据当前对象进行数据的入库处理
	 * 
	 * @param object
	 */
	boolean handle(T object);

	/**
	 * 支持的对象类型
	 * 
	 * @param object
	 * @return
	 */
	boolean support(Class<?> cls);
}
