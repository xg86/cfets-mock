package com.xquant.platform.component.mock.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xquant.platform.component.mock.dto.DataColumn;

@Repository
public interface DatabaseQueryDao {

	/**
	 * 查询最新的xswap数据库pid
	 * @return
	 */
	public Long getNextXswapTradePid();

	/**
	 * 查询最新的xswap数据库qid
	 * @return
	 */
	public Long getNextXswapTradeQid();

	/**
	 * 查询最新的cfets数据库pid
	 * @return
	 */
	public Long getNextCfetsTradePid();

	/**
	 * 查询最新的cfets数据库pid
	 * @return
	 */
	public Long getNextCfetsTradeQid();

	/**
	 * 获取一个数据库表的表名和类型的映射
	 * 
	 * @param tableName
	 * @return
	 */
	public List<DataColumn> getColumnNameAndTypeOfTable(@Param("tableName") String tableName);

	/**
	 * 取得数据库中一个表的所有字段名
	 * 
	 * @param tableName
	 * @return
	 */
	public String[] getColumnNameOfTable(@Param("tableName") String tableName);
}
