package com.xquant.platform.component.mock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Copyright © 2018 xQuant Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.xquant.platform.component.mock.test
 * @author: guanglai.zhou
 * @date: 2018-05-03 11:20:50
 */
public class MockDateTimeUtil {

	public static final String DATE_TIME = "yyyyMMdd-HH:mm:ss.sss";

	public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	public static String getTransactimeOfNow() {
		return new SimpleDateFormat(DATE_TIME).format(new Date());
	}

	public static String getTransactimeOfDate(Date date) {
		return new SimpleDateFormat(DATE_TIME).format(date);
	}

	/**
	 * 根据日期获取订单有效时间（当天晚上7点半）
	 * 
	 * @return
	 */
	public static String getValidateTime() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-19:30:00.000";
	}

	/**
	 * 将日期转换为固定格式字符串好
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String getFormatTime(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 获取业务时间
	 * 
	 * @return
	 */
	public static String getTransactTime() {
		return getFormatTime(MockDatePatternUtil.DATE_TIME, new Date());
	}

	/**
	 * 根据date时间获取后几分钟、几小时、几天的时间或日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期的格式
	 * @param calendarUnit
	 *            小时、分钟、天等
	 * @param num
	 * @return
	 */
	public static String timeAfter(String date, String pattern, int calendarUnit, int num) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date indate = null;
		try {
			indate = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(indate);
		calendar.set(calendarUnit, calendar.get(calendarUnit) + num);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前时候后固定小时的日期时间
	 * 
	 * @param hour
	 * @return
	 */
	public static String timeAfterHourWithFormat(int hour) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + hour);
		return getFormatTime(MockDatePatternUtil.DATE_TIME, cal.getTime());
	}

	/**
	 * 获取当前时间后固定分钟的日期时间
	 * 
	 * @param mimute
	 * @return
	 */
	public static String timeAfterMinuteWithFormat(int mimute) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + mimute);
		return getFormatTime(MockDatePatternUtil.DATE_TIME, cal.getTime());
	}

	/**
	 * 获取指定时间后固定分钟的日期时间
	 * 
	 * @param mimute
	 * @return
	 */
	public static String timeAfterMinuteWithFormat(Date date, int mimute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + mimute);
		return getFormatTime(MockDatePatternUtil.DATE_TIME, cal.getTime());
	}

	/**
	 * 获取当前时候后固定小时的日期时间
	 * 
	 * @param hour
	 * @return
	 */
	public static Date timeAfterHour(int hour) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + hour);
		return cal.getTime();
	}

	/**
	 * 获取当前时间后固定分钟的日期时间
	 * 
	 * @param mimute
	 * @return
	 */
	public static Date timeAfterMinute(int mimute) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + mimute);
		return cal.getTime();
	}

	/**
	 * 获取当前时间后固定秒钟的日期时间
	 * 
	 * @param mimute
	 * @return
	 */
	public static Date timeAfterSecond(int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + second);
		return cal.getTime();
	}

	/**
	 * 获取指定时间后固定分钟的时间
	 * 
	 * @param date
	 * @param mimute
	 * @return
	 */
	public static Date timeAfterMinute(Date date, int mimute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + mimute);
		return cal.getTime();
	}

	/**
	 * 根据指定单位获取两个时间之差
	 * 
	 * @param startdate
	 * @param endDate
	 * @return
	 */
	public static Long getDelayBetween(Date startdate, Date endDate, TimeUnit timeUnit) {
		if (endDate.after(startdate)) {
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(startdate);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate);
			return timeUnit.convert(endCal.getTimeInMillis() - startCal.getTimeInMillis(), TimeUnit.MILLISECONDS);
		}
		return 0L;
	}

	public static void main(String[] args) {
		// System.out.println(MockDateTimeUtil.timeAfterHourWithFormat(1));
		// System.out.println(MockDateTimeUtil.timeAfterMinuteWithFormat(30));

		// Field[] allFields =
		// FieldUtils.getAllFields(CfetsTradeCollateralDialogueQuoteResMessageBody.class);
		// for (Field field : allFields) {
		// System.out.println(field.getName());
		// }

		// try {
		// Date date1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20180720
		// 10:38:00");
		// Date date2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20180720
		// 10:39:45");
		// System.out.println(getDelayBetween(date1, date2, TimeUnit.SECONDS));
		// System.out.println(timeAfterMinute(date, 10));
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }

		System.out.println(MockDateTimeUtil.timeAfter("20180723", "yyyyMMdd", Calendar.DATE, 3));

	}
}
