package com.xquant.platform.component.mock.util.mybatis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.xquant.cfets.trade.protocol.message.CfetsTradeBondPriceLimitQuoteResMessageBody;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.util
 * @author: guanglai.zhou
 * @date: 2018-07-22 13:36:01
 */
public class ColumnFiledMappingUtil {

	private static final IColumnFiledMatchRule columnFiledMatchRule = new DefaultColumnFiledMatchRule();
	private static final IColumnFiledConcatRule columnFiledConcatRule = new DefaultColumnFiledConcatRule();

	/**
	 * 传入表中字段名组成的数组和对象中字段名组成的数组,进行匹配,如果匹配完成则返回一个List,格式如下：
	 * 匹配COLUMNNAME:匹配对象FILEDNAME 组成的数组
	 * 
	 * @param columnNames
	 * @param filedName
	 * @return
	 */
	public static List<String> resolveAsStringList(String[] columnNames, String[] filedNames) {

		List<String> matchingResult = new ArrayList<String>();
		Set<String> resolvedColumns = new HashSet<String>();
		Set<String> unresolvedFileds = new CopyOnWriteArraySet<String>();
		Collections.addAll(unresolvedFileds, filedNames);
		for (String columnName : columnNames) {
			// 只在未匹配的column中进行匹配
			if (!resolvedColumns.contains(columnName)) {
				for (String filedName : unresolvedFileds) {
					if (columnFiledMatchRule.matches(columnName, filedName)) {
						matchingResult.add(columnFiledConcatRule.concat(columnName, filedName));
						resolvedColumns.add(columnName);
						unresolvedFileds.remove(filedName);
						// 一旦匹配上了 跳出这个循环
						break;
					}
				}
			}
		}
		resolvedColumns.clear();
		unresolvedFileds.clear();
		return matchingResult;
	}

	/**
	 * 传入表中字段名组成的数组和对象中字段名组成的数组,进行匹配,如果匹配完成则返回一个Map key 为 columnName value
	 * 为fieldName
	 * 
	 * @param columnNames
	 * @param filedName
	 * @return
	 */
	public static Map<String, String> resolveAsMap(String[] columnNames, String[] filedNames) {

		Map<String, String> resultMap = new HashMap<String, String>();
		Set<String> resolvedColumns = new HashSet<String>();
		Set<String> unresolvedFileds = new CopyOnWriteArraySet<String>();
		Collections.addAll(unresolvedFileds, filedNames);
		for (String columnName : columnNames) {
			// 只在未匹配的column中进行匹配
			if (!resolvedColumns.contains(columnName)) {
				for (String filedName : unresolvedFileds) {
					if (columnFiledMatchRule.matches(columnName, filedName)) {
						resultMap.put(columnName, filedName);
						resolvedColumns.add(columnName);
						unresolvedFileds.remove(filedName);
						// 一旦匹配上了 跳出这个循环
						break;
					}
				}
			}
		}
		resolvedColumns.clear();
		unresolvedFileds.clear();
		return resultMap;
	}

	/**
	 * 处理按照columnName1:columnName2传入的colums
	 * 
	 * @param columns
	 * @param filedNames
	 * @return
	 */
	public static List<String> resolve4ColumnString(String columnNames, String[] filedNames) {
		String regex = ":";
		return resolveAsStringList(columnNames.split(regex), filedNames);
	}

	/**
	 * 处理传入的为对象类型的情况
	 * 
	 * @param columnNames
	 * @param cls
	 * @return
	 */
	public static List<String> resolve4Type(String columnNames, Class<?> cls) {
		Field[] allFields = FieldUtils.getAllFields(cls);
		String[] filedNames = new String[allFields.length];
		int i = 0;
		for (Field field : allFields) {
			filedNames[i++] = field.getName();
		}
		return resolve4ColumnString(columnNames, filedNames);
	}

	public static Map<String, String> resolveAsMap4Type(String[] columnNames, Class<?> cls) {
		Field[] allFields = FieldUtils.getAllFields(cls);
		String[] filedNames = new String[allFields.length];
		int i = 0;
		for (Field field : allFields) {
			filedNames[i++] = field.getName();
		}
		return resolveAsMap(columnNames, filedNames);
	}

	public static void main(String[] args) {
		// String columnNames =
		// "QID:CLIENT_ID:CLORDID_CLIENT_ID:CLORDID:QUOTEID:TRANSACTTIME:QUOTETRANSTYPE:MARKETINDICATOR:ORDERQTY:SECURITYID:SIDE:TEXT:VALIDUNTILTIME:SETTLTYPE:QUOTETYPE:SECURITYTYPE:DELIVERYTYPE:DELIVERYTYPE2:CLEARINGMETHOD:PRICE:MATURITYYIELD:STRIKEYIELD:TRADELIMITDAYS:TRADECASHAMT:PARTYID:TRADERID:PARTYNAME:TRADERNAME:CUSTODIAN_INSTITUTION_NAME:CASH_BANK_NAME:CASH_ACCT_NUMBER:CUSTODIAN_ACCT_NUMBER:COUNTER_PARTYID:COUNTER_PARTYNAME:COUNTER_TRADERNAME:COUNTER_TRADERID:ORIG_CFETS_STATUS:ACCRUEDINTERESTAMT:SETTLDATE:SETTLDATE2:CASHHOLDINGDAYS:SETTLCURRAMT2:SETTLCURRAMT:ACCRUEDINTERESTTOTALAMT:DIRTYPRICE:SETTLCURRENCY:SETTLCURRFXRATE:REPOMETHOD:NEGOTIATIONCOUNT:SYMBOL:QUOTETIME:ACTION:ISQUOTERESPONSE:STATUS:ERRORCODE:ERRORMSG:UPDATETIME:ACT_QID:ACT_STATUS:ACT_ERRORCODE:ACT_ERRORMSG:SEND_RECV_FLAG:ACT_TYPE:PORTFOLIO:MATCH_CUSTOMID:MATCH_STATUS:MATCH_DIFFERENCE:MYSIDE";
		// List<String> resolve = resolve(columnNames,
		// CfetsTradeCollateralDialogueQuoteReceiveMessageBody.class);
		// for (String str : resolve) {
		// System.out.println(str);
		// }
		// String columnNames =
		// "QID:CLIENT_ID:CLORDID_CLIENT_ID:CLORDID:QUOTEID:TRANSACTTIME:QUOTETRANSTYPE:ANONYMOUSINDICATOR:SECURITYTYPE:MARKETINDICATOR:QUOTETYPE:VALIDUNTILTIME:SECURITYID:SIDE:MAXFLOOR:MINTICKSIZE:PRICE:ORDERQTY:SETTLTYPE:CLEARINGMETHOD:MATURITYYIELD:STRIKEYIELD:PARTYID:TRADERID:CASH_ACCT_NUMBER:CUSTODIAN_ACCT_NUMBER:CUSTODIAN_INSTITUTION_NAME:ORIG_CFETS_STATUS:STATUS:ERRORCODE:ERRORMSG:UPDATETIME:LEAVESTOTALQTY:SETTLCURRAMT:ACCRUEDINTERESTAMT:ACCRUEDINTERESTTOTALAMT:DIRTYPRICE:SETTLDATE:TRADECASHAMT:PARTYNAME:SYMBOL:TRADERNAME:SETTLCURRENCY:SETTLCURRFXRATE:REFERENCE:QUOTETIME:CONTINGENCYINDICATOR:LEAVESQTY:PRINCIPAL:TOTALPRINCIPAL:CASH_BANK_NAME:DELIVERYTYPE:ACT_QID:ACTION:ACT_STATUS:ACT_ERRORCODE:ACT_ERRORMSG:SEND_RECV_FLAG:ACT_TYPE:PORTFOLIO:MATCH_CUSTOMID:MATCH_STATUS:MATCH_DIFFERENCE";
		// List<String> resolve = resolve(columnNames,
		// CfetsTradeBondClickDealQuoteResMessageBody.class);
		// for (String str : resolve) {
		// System.out.println(str);
		// }
		String columnNames = "QID:CLIENT_ID:CLORDID_CLIENT_ID:CLORDID:ORDERID:TRANSACTTIME:ORDERQTY:ORDTYPE:PRICE:SECURITYID:SIDE:EXPIRETIME:SETTLTYPE:MINTICKSIZE:MARKETINDICATOR:SECURITYTYPE:CLEARINGMETHOD:SPLITINDICATOR:MATURITYYIELD:STRIKEYIELD:PARTYID:TRADERID:CASH_BANK_NAME:CASH_ACCT_NUMBER:CUSTODIAN_ACCT_NUMBER:CUSTODIAN_INSTITUTION_NAME:ORIG_CFETS_STATUS:STATUS:ERRORCODE:ERRORMSG:UPDATETIME:QUOTETRANSTYPE:SYMBOL:ACCRUEDINTERESTAMT:ACCRUEDINTERESTTOTALAMT:DIRTYPRICE:SETTLCURRAMT:TRADECASHAMT:SETTLDATE:EXECTYPE:QUOTETYPE:VALIDUNTILTIME:PARENTORDERID:CONTINGENCYINDICATOR:LEAVESQTY:PRINCIPAL:TOTALPRINCIPAL:PARTYNAME:TRADERNAME:DELIVERYTYPE:LEAVESTOTALQTY:ACT_QID:ACT_STATUS:ACT_ERRORCODE:ACT_ERRORMSG:SEND_RECV_FLAG:ACT_TYPE:ACTION:PORTFOLIO:MATCH_CUSTOMID:MATCH_STATUS:MATCH_DIFFERENCE";
		List<String> resolve = resolve4Type(columnNames, CfetsTradeBondPriceLimitQuoteResMessageBody.class);
		for (String str : resolve) {
			System.out.println(str);
		}

	}
}
