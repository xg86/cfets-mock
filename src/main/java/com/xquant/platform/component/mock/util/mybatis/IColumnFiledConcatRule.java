/**
 * 
 */
package com.xquant.platform.component.mock.util.mybatis;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-07-22 13:41:55
 */
public interface IColumnFiledConcatRule {

	/**
	 * 数据库column name 与 field name进行组合的方式
	 * 
	 * @param column
	 * @param field
	 * @return
	 */
	public String concat(String column, String field);
}
