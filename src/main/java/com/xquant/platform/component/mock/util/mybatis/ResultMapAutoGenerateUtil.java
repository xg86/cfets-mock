package com.xquant.platform.component.mock.util.mybatis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xquant.platform.component.mock.dao.DatabaseQueryDao;
import com.xquant.platform.component.mock.dto.DataColumn;

import io.netty.util.CharsetUtil;

public class ResultMapAutoGenerateUtil {

	private static final String xmlDef = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
	private static final String dtdDef = "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >";
	private static final String mapperPrefix = "<mapper namespace=\"";
	private static final String mapperMidPrix = "\">";

	private static final String mapperSubfix = "</mapper>";

	private static final String baseResultMap = "<resultMap id=\"BaseResultMap\" type=\"";
	private static final String typeSubfix = "\">";
	private static final String baseResultMapSubfix = "</resultMap>";

	private static final String baseColumnSqlPrefix = "<sql id=\"BaseColumn\">";
	private static final String baseColumnSqlSubfix = "</sql>";

	private static final String baseValueSqlPrefix = "<sql id=\"baseValue\">";
	private static final String baseValueSqlSubfix = "</sql>";

	private static final String resultMapColumnPrefix = "<result column=\"";
	private static final String resultMapPropertyPrefix = "\" property=\"";
	private static final String resultMapJdbcTypePrefix = "\" jdbcType=\"";
	private static final String resultMapSubfix = "\" />";

	private static final String insertPrefix = "<insert id=\"insert\" parameterType=\"";
	private static final String insertMidfix = "\">\r\n INSERT INTO ";
	private static final String insertEndfix = " (\r\n" + "		<include refid=\"BaseColumn\" />\r\n" + "		)\r\n"
			+ "		VALUES\r\n" + "		(\r\n" + "		<include refid=\"baseValue\" />\r\n" + "		)\r\n"
			+ "	</insert>";

	private static final String baseColumnFix = ",";
	private static final String linefeed = "\n";

	private static final String baseValuePrefix = "#{";
	private static final String baseValueMidFix = ",jdbcType=";
	private static final String baseValueSubFix = "},";

	public static String generate(DatabaseQueryDao databaseQueryDao, String tableName, Class<?> objectType) {
		String nameSpace = "com.xquant.platform.component.mock.dao.BondRfqResQuoteResMessageDao";
		return generate(databaseQueryDao, tableName, objectType, nameSpace);
	}

	/**
	 * 根据数据库表名和映射对象类型自动生成mybatis映射表的映射关系
	 * 
	 * @param databaseQueryDao
	 *            查询基础类
	 * @param tableName
	 *            数据库表名
	 * @param objectType
	 *            映射对象类型
	 * @return 包含resultMap、baseColumn、baseValue等List的map集合
	 */
	public static String generate(DatabaseQueryDao databaseQueryDao, String tableName, Class<?> objectType,
			String nameSpace) {
		List<String> resultMapList = new ArrayList<String>();
		List<String> baseColumnList = new ArrayList<String>();
		List<String> baseValueList = new ArrayList<String>();

		List<DataColumn> columnNameAndTypeOfTable = databaseQueryDao.getColumnNameAndTypeOfTable(tableName);
		Map<String, String> nameTypeMap = new HashMap<String, String>();
		for (DataColumn dataColumn : columnNameAndTypeOfTable) {
			nameTypeMap.put(dataColumn.getColumnName(), dataColumn.getColumnType().toUpperCase());
		}
		String[] columns = databaseQueryDao.getColumnNameOfTable(tableName);
		Map<String, String> nameFieldMap = ColumnFiledMappingUtil.resolveAsMap4Type(columns, objectType);

		for (String columnName : nameFieldMap.keySet()) {
			String columnType = nameTypeMap.get(columnName);
			String filedname = nameFieldMap.get(columnName);
			String resultMapResult = resultMapColumnPrefix + columnName + resultMapPropertyPrefix + filedname
					+ resultMapJdbcTypePrefix + columnType + resultMapSubfix;
			resultMapList.add(resultMapResult);
			baseColumnList.add(columnName + baseColumnFix);
			baseValueList.add(baseValuePrefix + filedname + baseValueMidFix + columnType + baseValueSubFix);
		}
		String resultMapPrfix = xmlDef + dtdDef + mapperPrefix + nameSpace + mapperMidPrix + baseResultMap
				+ objectType.getName() + typeSubfix;

		String resultMapSubFix = baseResultMapSubfix + linefeed;
		StringBuilder stringBuilder = new StringBuilder(resultMapPrfix);

		for (String str : resultMapList) {
			stringBuilder.append(str).append(linefeed);
		}
		stringBuilder.append(resultMapSubFix);
		stringBuilder.append(baseColumnSqlPrefix + linefeed);
		for (String str : baseColumnList) {
			stringBuilder.append(str).append(linefeed);
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 2);
		stringBuilder.append(baseColumnSqlSubfix + linefeed + baseValueSqlPrefix + linefeed);

		for (String str : baseValueList) {
			stringBuilder.append(str).append(linefeed);
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 2);
		stringBuilder.append(baseValueSqlSubfix + linefeed);
		String insertStr = insertPrefix + objectType.getName() + insertMidfix + tableName + insertEndfix;
		stringBuilder.append(insertStr + linefeed);
		stringBuilder.append(mapperSubfix);
		return stringBuilder.toString();
	}

	public static String generateMapperXml(DatabaseQueryDao databaseQueryDao, String fileName, Class<?> mapperType,
			String tableName, Class<?> pojoType) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(fileName), CharsetUtil.UTF_8.name());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String generateXml = ResultMapAutoGenerateUtil.generate(databaseQueryDao, tableName, pojoType,
				mapperType.getName());
		if (pw != null) {
			pw.print(generateXml);
			pw.close();
		}
		return generateXml;
	}

	/**
	 * 自动生成mapper文件
	 * @param databaseQueryDao 进行数据库查询的基础类
	 * @param namespaceType 命名空间类型(数据层接口类型)
	 * @param tableName 数据库表名称 
	 * @param parameterType 对应实体类名称
	 * @return
	 */
	public static String generateMapperXml(DatabaseQueryDao databaseQueryDao, Class<?> namespaceType, String tableName,
			Class<?> parameterType) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(namespaceType.getSimpleName() + ".xml"), CharsetUtil.UTF_8.name());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String generateXml = ResultMapAutoGenerateUtil.generate(databaseQueryDao, tableName, parameterType,
				namespaceType.getName());
		if (pw != null) {
			pw.print(generateXml);
			pw.close();
		}
		return generateXml;
	}

	public static void main(String[] args) {
		System.out.println("\n".length());
	}

}
