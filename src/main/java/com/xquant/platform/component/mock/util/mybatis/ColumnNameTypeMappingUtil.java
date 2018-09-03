/**
 * 
 */
package com.xquant.platform.component.mock.util.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-07-22 13:36:01
 */
public class ColumnNameTypeMappingUtil {

	/**
	 * 查询一个表中所有的字段以及类型
	 * @param tableName
	 * @param jdbcTemplate
	 * @return
	 */
	public static Map<String, String> getColumnNameTypeMap(String tableName, JdbcTemplate jdbcTemplate) {
		Map<String, String> colNameTypeMap = new HashMap<String, String>();
		String sql = "SELECT COLUMN_NAME,DATA_TYPE FROM information_schema.`COLUMNS` WHERE TABLE_NAME = ?";
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql, tableName);
		for (Map<String, Object> map : queryForList) {
			colNameTypeMap.put(map.get("COLUMN_NAME").toString().trim(), map.get("DATA_TYPE").toString().trim().toUpperCase());
		}
		return colNameTypeMap;
	}
}
