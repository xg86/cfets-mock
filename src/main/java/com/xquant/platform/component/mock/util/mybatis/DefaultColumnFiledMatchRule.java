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
public class DefaultColumnFiledMatchRule implements IColumnFiledMatchRule {


	/**
	 * 去掉数据库column name中的_然后进行匹配
	 */
	@Override
	public boolean matches(String column, String field) {
		
		if(column.indexOf("_")!=-1) {
			column = column.replaceAll("_", "");
		}
		if(field.indexOf("_")!=-1) {
			field = field.replaceAll("_", "");
		}
		return column.equalsIgnoreCase(field);
	}

}
