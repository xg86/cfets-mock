/**
 * 
 */
package com.xquant.platform.component.mock.util.mybatis;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-07-22 13:44:21
 */
public class DefaultColumnFiledConcatRule implements IColumnFiledConcatRule {

	@Override
	public String concat(String column, String field) {
		return column+":"+field;
	}
}
