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
public interface IColumnFiledMatchRule {
    
	/**
	 * 如果当前字符串与字段名匹配成功 则返回true 否则返回false
	 * @param column
	 * @param field
	 * @return
	 */
	 public boolean matches(String column,String field);
}
