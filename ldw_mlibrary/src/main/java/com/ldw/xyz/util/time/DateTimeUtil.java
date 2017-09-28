package com.ldw.xyz.util.time;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateTimeUtil {

	private static final String TAG = "DateTimeUtil";

	private static long mCheckedSec = 0;// 时间差（秒）：服务器-本地

	private static long mServerSec = 0;// 服务器时间

	/**
	 * 日期格式化(Date转换成String)
	 * 
	 * @param date
	 *            时期
	 * @param /formater
	 *            格式化形式
	 * @return 格式化结果
	 */
	public static String date2String(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return DateFormat.format(pattern, date).toString();
	}

	/**
	 * 日期格式化(毫秒转换成String)
	 * 
	 * @param timeStamp
	 * @param pattern
	 * @return
	 */
	public static String date2String(long timeStamp, String pattern) {
		return DateFormat.format(pattern, timeStamp).toString();
	}

	/**
	 * 日期格式化(秒转换成String)
	 * 
	 * @param timeStamp
	 * @param pattern
	 * @return
	 */
	public static String second2String(long timeStamp, String pattern) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(timeStamp * 1000);
		return new SimpleDateFormat(pattern).format(new Date(gc
				.getTimeInMillis()));
	}

	/**
	 * 获取服务器校验时间(毫秒)
	 * 
	 * @return
	 */
	public static long getCheckedCurrentTimeMillisecond() {
		return System.currentTimeMillis() + mCheckedSec * 1000;
	}

	/**
	 * 获取服务器校验时间(秒)
	 * 
	 * @return
	 */
	public static long getCheckedCurrentTimeSecond() {
		return System.currentTimeMillis() / 1000 + mCheckedSec;
	}

	/**
	 * 获得校验时间
	 * 
	 * @return
	 */
	public static long getCheckedSec() {
		return mCheckedSec;
	}

	/**
	 * 获得当前时间的String
	 * 
	 * @return
	 */
	public static String getCurrentDateString() {
		return DateTimeUtil.date2String(
				DateTimeUtil.getStandardCurrentTimeMillis(),
				"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 计算两个时期相差的天数dateA-dateB 这个方法比较耗时
	 * 
	 * @param dateA
	 * @param dateB
	 * @return 相隔天数
	 */
	public static int getDayDiff(long dateA, long dateB) {
		long dayNumber = -1;
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String timeA = format.format(new Date(dateA));
		String timeB = format.format(new Date(dateB));
		try {
			long sDateA = format.parse(timeA).getTime();
			long sDateB = format.parse(timeB).getTime();

			long DAY = 86400000L;
			dayNumber = (sDateA - sDateB) / DAY;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) dayNumber;
	}

	public static String getDayofWeek() {

		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };

		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();

		try {
			date = sdfInput.parse(DateTimeUtil.date2String(
					DateTimeUtil.getStandardCurrentTimeMillis(),
					"yyyy-MM-dd hh:mm"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0)
			dayOfWeek = 0;

		return DateTimeUtil.date2String(
				DateTimeUtil.getStandardCurrentTimeMillis(), "yyyy年MM月dd日")
				+ " " + dayNames[dayOfWeek];
	}

	/**
	 * <pre>
	 * 根据指定的日期字符串获取星期几
	 * </pre>
	 * 
	 * @param strDate
	 *            指定的日期字符串(yyyy-MM-dd 或 yyyy/MM/dd)
	 * @return week
	 *         星期几(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY)
	 */
	public static String getWeekByDateStr(String strDate) {

		int year = Integer.parseInt(strDate.substring(0, 4));
		int month = Integer.parseInt(strDate.substring(5, 7));
		int day = Integer.parseInt(strDate.substring(8, 10));

		Calendar c = Calendar.getInstance();

		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);

		String week = "";
		int weekIndex = c.get(Calendar.DAY_OF_WEEK);

		switch (weekIndex) {
		case 1:
			week = "星期日";
			break;
		case 2:
			week = "星期一";
			break;
		case 3:
			week = "星期二";
			break;
		case 4:
			week = "星期三";
			break;
		case 5:
			week = "星期四";
			break;
		case 6:
			week = "星期五";
			break;
		case 7:
			week = "星期六";
			break;
		}
		return week;
	}

	/**
	 * @Title: GetDifferenceTime
	 * @Description: TODO 获取当前时间戳
	 * @param _date
	 *            feed的分享时间 单位：秒
	 * @return
	 * @return: String
	 */
	public static String GetDifferenceTime(long _date) {
		// long ServiceDate = getCheckedCurrentTimeMillisecond();
		long serverMs = getServerSec() * 1000;
		long ServiceDate = (serverMs > 0) ? serverMs
				: getCheckedCurrentTimeMillisecond();
		// Log.e(TAG,"serverMs = " + serverMs);
		// Log.e(TAG,"ServiceDate = " + ServiceDate);

		long AfferentDate = _date * 1000;
		// Log.e(TAG,"AfferentDate = " + AfferentDate);
		// TODO 还需测试细调
		if (getSecondDiff(ServiceDate, AfferentDate) >= 60) { // 小于60秒显示刚刚
			if (getSecondDiff(ServiceDate, AfferentDate) >= 3600) { // 小于60分显示XX分钟前
				if (getHourDiff(ServiceDate, AfferentDate) >= 24) { // 小于24小时显示XX小时前
					if (getHourDiff(ServiceDate, AfferentDate) < 8760) { // 小于一年显示XX-XX
						String pattern = "MM-dd";
						SimpleDateFormat format = new SimpleDateFormat(pattern);
						String time = format.format(new Date(AfferentDate));
						return time;
					} else { // 显示XXXX-XX
						String pattern = "yyyy-MM";
						SimpleDateFormat format = new SimpleDateFormat(pattern);
						String time = format.format(new Date(AfferentDate));
						return time;
					}
				} else {
					return getHourDiff(ServiceDate, AfferentDate) + "小时前";
				}
			} else {
				return getSecondDiff(ServiceDate, AfferentDate) / 60 + "分钟前";
			}
		} else {
			return "刚刚";
		}
	}

	/**
	 * 获取两个时间相差几个小时dateA-dateB
	 * 
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static int getHourDiff(long dateA, long dateB) {
		long hourNumber = 0;
		long hour = 3600000L;
		try {
			hourNumber = (dateA - dateB) / hour;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) hourNumber;
	}

	/**
	 * 获取两个时间相差几秒
	 * 
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static int getSecondDiff(long dateA, long dateB) {
		long second = 0;
		try {
			second = (dateA - dateB) / 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) second;
	}

	public static long getServerSec() {
		return mServerSec;
	}

	/**
	 * 获取标准时间(毫秒)
	 * 
	 * @return
	 */
	public static long getStandardCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取标准时间(秒)
	 * 
	 * @return
	 */
	public static long getStandardCurrentTimeSecond() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 判断现在是否在指定的时间段
	 * 
	 * @param sleepStartSecond
	 *            秒为单位，0点为0秒
	 * @param sleepEndSecond
	 *            秒为单位，0点为0秒
	 * @return
	 */
	public static boolean isInTimePeriod(int sleepStartSecond,
			int sleepEndSecond) {
		Date date = new Date(System.currentTimeMillis());
		int now = date.getHours() * 3600 + date.getMinutes() * 60
				+ date.getSeconds();
		if (now >= sleepStartSecond && now <= sleepEndSecond) {
			return true;
		} else {
			now += 24 * 60 * 60;
			if (now >= sleepStartSecond && now <= sleepEndSecond)
				return true;
			else
				return false;
		}
	}

	/**
	 * 比较两个日期是否同一天
	 * 
	 * @param dateA
	 *            -毫秒
	 * @param dateB
	 *            -毫秒
	 * @return
	 */
	public static boolean isSameDate(long dateA, long dateB) {
		boolean flag = false;
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String timeA = format.format(new Date(dateA));
		String timeB = format.format(new Date(dateB));
		try {
			long sDateA = format.parse(timeA).getTime();
			long sDateB = format.parse(timeB).getTime();
			flag = (sDateA - sDateB == 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 设置校验时间
	 * 
	 * @param /timeDiffSEC
	 */
	public static void setCheckedSec(long checkedSec) {
		DateTimeUtil.mCheckedSec = checkedSec;
	}

	public static void setServerSec(long serverSec) {
		DateTimeUtil.mServerSec = serverSec;
	}

	/**
	 * 把字符串转化为日期
	 * 
	 * @param dateString
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date string2Date(String dateString, String pattern) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		if (dateString != null) {
			try {
				date = dateFormat.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}

	/**
	 * 获取得指定日期字符串的Date
	 */
	public static Date getDateByDateStr(String dateStr, String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取指定long时间的日期字符串
	 */
	public static String getByDateTime(long dateTime, String format) {
		String dateStr = "";
		Date date = new Date(dateTime);
		dateStr = getDateStrByDate(date, format);
		return dateStr;
	}

	/**
	 * 获取当前日的字符串
	 */
	public static String getDateStrByFormat(String format) {
		return getDateStrByDate(new Date(), format);
	}

	/**
	 * 获取指定日的字符串
	 */
	public static String getDateStrByDate(Date date, String format) {
		String str = "";
		str = new SimpleDateFormat(format).format(date);
		return str;
	}

	/**
	 * 获取当前天的前或后多少天, 负数为前多少天,正数为后多少天
	 * 
	 * @param num
	 * @return
	 */
	public static Date getDateBeforeOrAfterNum(int num) {
		// Date date = new Date(new Date().getTime() + num * 86400000);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, num);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 获取当前天的前或后多少天的日期字符串, 负数为前多少天,正数为后多少天
	 * 
	 * @param num
	 * @return
	 */
	public static String getDateStrBeforeOrAfterNum(int num, String pattern) {
		// Date date = new Date(new Date().getTime() + num * 86400000);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, num);
		Date date = calendar.getTime();
		return date2String(date, pattern);
	}

}
