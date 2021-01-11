package com.dapeng.reptile.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @ClassName: DateUtil
 * @Description: 日期工具类
 * @author: DaPeng
 * @date: 2017年9月26日 下午3:25:31
 */
@Slf4j
public class DateUtil {

	/**
	 * 日期格式：yyyy-MM-dd HH:mm
	 */
	public static final String DATE_PATTERN_TIME = "yyyy年MM月dd日 HH:mm";
	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式：yyyyMMddHHmmss
	 */
	public static final String DATE_PATTERN_STRING = "yyyyMMddHHmmss";
	/**
	 * 日期格式：【yyyy】年【MM】月【dd】
	 */
	public static final String DATE_PATTERN_DAY_CHINNESS = "【yyyy】年【MM】月【dd】";
	/**
	 * 日期格式：yyyy年MM月dd日
	 */
	public static final String DATE_PATTERN_DAY_CHINNESS_DEFAULT = "yyyy年MM月dd日";
	/**
	 * 日期格式：MM月dd日
	 */
	public static final String DAY_CHINNESS_DEFAULT = "MM月dd日";
	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static final String DATE_PATTERN_DAY = "yyyy-MM-dd";
	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String DATE_PATTERN_NUMBER_DAY = "yyyyMMdd";
	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String DATE_PATTERN_DAY_NUM = "yyyyMMdd";
	/**
	 * 日期格式：yyyy-MM
	 */
	public static final String DATE_PATTERN_MONTH = "yyyy-MM";
	/**
	 * 日期格式：yyyyMM
	 */
	public static final String DATE_PATTERN_YEAR_MONTH = "yyyyMM";
	/**
	 * 日期格式：dd
	 */
	public static final String DATE_PATTERN_JUEST_DAY = "dd";
	/**
	 * 每天的最后时间点： " 23:59:59"
	 */
	public static final String DAY_LAST_TIME = " 23:59:59";
	/**
	 * 每天的最早时间点：" 00:00:00"
	 */
	public static final String DAY_FIRST_TIME = " 00:00:00";
	/**
	 * 一天换算 秒数
	 */
	public static final long ONE_DAY_MILLISECOND = 24 * 60 * 60 * 1000;
	/**
	 * 周数组
	 */
	protected static final String[] weeks = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	/**
	 * 日期格式：yyyyMMddHHmmssSSSS
	 */
	public static final String DATE_TIME_MICRO_SECONDS_PATTERN_STRING = "yyyyMMddHHmmssSSSS";
	/**
	 * 日期格式：yyyy.MM.dd
	 */
	public static final String DATE_SPECIAL_PATTERN = "yyyy.MM.dd";

	/**
	 * 获取当前日期
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取当前时间
	 * @return long 类型
	 */
	public static long getCurrentTime() {
		return getCurrentDate().getTime();
	}

	/**
	 * 日期格式，指定格式
	 * 默认格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 * @date 2016-12-06 09:45:02
	 * @author DaPeng
	 */
	public static String dateToString(Date date, String pattern) {
		if (StringUtils.isBlank(pattern)) {
			pattern = DATE_PATTERN_DEFAULT;
		}
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		return formater.format(date);
	}

	/**
	 * 格式化时间格式
	 * @return
	 * @date 2016-12-06 09:46:49
	 * @author DaPeng
	 */
	public static String formatDate(Date date, String pattern) {
		try {
			return dateToString(date, pattern);
		} catch (Exception e) {
			log.error("格式转换出错", e);
		}
		return null;
	}

	/**
	 * 格式化时间格式
	 * @return
	 * @date 2016-12-06 09:47:06
	 * @author DaPeng
	 */
	public static Date formatDate(String date, String pattern) {
		try {
			return stringToDate(date, pattern);
		} catch (Exception e) {
			log.error("格式转换出错", e);
		}
		return null;
	}


	/**
	 * @Title: dateToDate
	 * @Description: 日期转日期
	 * @param date
	 * @param pattern
	 * @return
	 * @return: Date
	 * @author: DaPeng
	 * @date: 2021年1月5日 下午3:27:27
	 */
	public static Date dateToDateString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		// 开始时间格式化成yyyy-MM-dd格式的时间字符串
		String format = sdf.format(date);
		return stringToDate(format, pattern);
	}

	/**
	 * 将字符串转化为日期,需指定字符串日期格式
	 * @date 2016-12-06 09:47:15
	 * @author DaPeng
	 */
	public static Date stringToDate(String str, String pattern) {
		Date dateTime = null;
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		try {
			dateTime = formater.parse(str);
		} catch (ParseException e) {
			log.error("字符串转化为日期出错", e);
		}
		return dateTime;
	}

	/**
	 * long to date
	 * @date 2017-01-03 15:25:15
	 * @author DaPeng
	 */
	public static Date timeMillisToDate(Long s) {
		if (s == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(s);
		return calendar.getTime();
	}

	/**
	 * 将年月日转换为 时间戳
	 * @date 2017-01-12 10:05:11
	 * @author DaPeng
	 */
	public static Long dateToStamp(String str) {
		DateFormat df = new SimpleDateFormat(DateUtil.DATE_PATTERN_NUMBER_DAY);
		Date date = null;
		try {
			date = df.parse(str);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.getTimeInMillis();
		} catch (ParseException e) {
			log.error("年月日转换为 时间戳出错", e);
		}
		return null;
	}

	/**
	 * 日期转时间戳
	 * @param date
	 * @return
	 */
	public static Long dateTimeToStamp(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getTimeInMillis();
	}

	/**
	 * 取得一个date对象对应的日期的0点0分0秒时刻的Date对象。
	 * @param date
	 * @return
	 */
	public static Date getMinDateOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		return calendar.getTime();
	}

	/**
	 * 取得一个date对象对应的日期的23点59分59秒时刻的Date对象。
	 * @param date
	 * @return
	 */
	public static Date getMaxDateOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 计算两个时间之间相隔几天（两个都会转换成 00：00：00的时间）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getBetweenDay(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			// 进行时间转换
			startDate = getMinDateOfDay(startDate);
			endDate = getMaxDateOfDay(endDate);

			long day = 24L * 60L * 60L * 1000L;
			if (null != startDate && null != endDate) {
				return (int) ((endDate.getTime() - startDate.getTime()) / day);
			}
		}
		return 0;
	}

	/**
	 * @Title: getBetweenDay
	 * @Description: 计算两个时间之间相隔几天, 参数格式为：2019-10-01,2019-10-10
	 * @param dateContent
	 * @return
	 * @return: int
	 * @author: DaPeng
	 * @date: 2021年01月11日 下午4:12:00
	 */
	public static int getBetweenDay(String dateContent) {
		String[] dateArray = dateContent.split(",");
		if (dateArray.length >= 2) {
			return DateUtil.getBetweenDay(dateArray[0], dateArray[1]);
		}
		return 0;
	}

	/**
	 * @Title: getBetweenDay
	 * @Description: 计算两个时间之间相隔几天（两个都会转换成 00：00：00的时间）
	 * @param start  start和end不分大小
	 * @param end  start和end不分大小
	 * @return
	 * @return: int
	 * @author: DaPeng
	 * @date: 2021年01月11日 下午4:12:00
	 */
	public static int getBetweenDay(String startStr, String endStr) {
		if (startStr != null && endStr != null) {
			// 字符串类型转为Date型
			Date startDate = DateUtil.stringToDate(startStr, "yyyy-MM-dd");
			Date endDate = DateUtil.stringToDate(endStr, "yyyy-MM-dd");
			if (startDate != null && endDate != null) {
				// 进行时间转换,取得一个date对象对应的日期的0点0分0秒时刻的Date对象
				startDate = getMinDateOfDay(startDate);
				endDate = getMinDateOfDay(endDate);
				// 获取毫秒数
				long starTime = startDate.getTime();
				long endTime = endDate.getTime();
				// 两个时间的毫秒差
				long time = endTime - starTime;
				// 相差的天数，可能为负数
				int day = (int) (time / ONE_DAY_MILLISECOND);
				// 取绝对值
				day = Math.abs(day);
				return day;
			}
		}
		return 0;
	}

	/**
	 * @Title: getSecondsNextEarlyMorning
	 * @Description: 判断当前时间距离第二天凌晨的秒数
	 * @return 返回值单位为[s:秒]
	 * @return: long
	 * @author: DaPeng
	 * @date: 2021年01月11日 下午4:12:00
	 */
	public static long getSecondsNextEarlyMorning() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		/**
		 * cal.set(Calendar.HOUR, 0);
		 * 如果当前时间超过中午12：00，则返回的结果是到第二天中午12：00的秒数； 
		 * 如果当前时间不超过中午12：00，则返回的结果是到第二天凌晨的秒数.
		 */
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long time = cal.getTimeInMillis() - System.currentTimeMillis();
		return time / 1000;
	}

	/**
	 * @Title: getDayCalculate
	 * @Description: 获取与指定日期相差天数的某个日期
	 * @param date   指定日期
	 * @param i      指定日期的相隔天数，为负数时表示指定日期之前好多天
	 * @param day    设置时间，小时
	 * @return
	 * @return: Date
	 * @author: DaPeng
	 * @date: 2020年5月25日 上午9:11:20 
	 */
	public static Date getDayCalculate(Date date, int i, int day) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.ENGLISH);
		calendar.setTime(date);
		// 设置当前的小时数
		calendar.set(Calendar.HOUR_OF_DAY, day);
		// 设置当前的分钟数
		calendar.set(Calendar.MINUTE, 0);
		// 设置当前的秒数
		calendar.set(Calendar.SECOND, 0);
		// 设置当前的毫秒数
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, i);
		// 昨天
		return calendar.getTime();
	}

	/**
	 * 获取某年第一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 * 获取某年最后一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	/**
	 * @Title: compareTwoTime
	 * @Description: 比较两个时间的大小，第一个大的返回true，否则返回false
	 * @param date1
	 * @param date2
	 * @return
	 * @return: boolean
	 */
	public static boolean compareTwoTime(Date date1, Date date2) {
		return date1.getTime() > date2.getTime();
	}

	/**
	 * LocalDate转换为Date
	 */
	public static Date localDate2Date(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * LocalDateTime转化为Date
	 */
	public static Date localDateTime2Date(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * @Title: getFirstDayOfQuarter
	 * @Description: 获取季度的第一天
	 * @param valueOf
	 * @param valueOf2
	 * @return
	 * @return: Date
	 * @author: DaPeng
	 * @date: 2021年1月18日 下午2:27:42
	 */
	public static String[] getFirstOrLastDayOfQuarter(Integer year, Integer quarter) {
		String[] s = new String[2];
		String str = "";
		// 设置本年的季
		Calendar quarterCalendar = Calendar.getInstance();
		quarterCalendar.set(Calendar.YEAR, year);
		switch (quarter) {
			case 1: // 本年到现在经过了一个季度，在加上前4个季度
				quarterCalendar.set(Calendar.MONTH, 3);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "01-01";
				s[1] = str;
				break;
			case 2: // 本年到现在经过了二个季度，在加上前三个季度
				quarterCalendar.set(Calendar.MONTH, 6);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "04-01";
				s[1] = str;
				break;
			case 3:// 本年到现在经过了三个季度，在加上前二个季度
				quarterCalendar.set(Calendar.MONTH, 9);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "07-01";
				s[1] = str;
				break;
			case 4:// 本年到现在经过了四个季度，在加上前一个季度
				str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "10-01";
				s[1] = str.substring(0, str.length() - 5) + "12-31";
				break;
		}
		return s;
	}

	/**
	 * @Title: getFirstDayOfMonth
	 * @Description: 获取月数的第一天
	 * @param valueOf
	 * @param valueOf2
	 * @return
	 * @return: Date
	 * @author: DaPeng
	 * @date: 2021年1月5日 下午2:28:28
	 */
	public static String[] getFirstOrLastDayOfMonth(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();

		String[] dates = new String[2];
		// 第一天
		// 设置年份
		calendar.set(Calendar.YEAR, year);
		// 设置月份
		calendar.set(Calendar.MONTH, month - 1);
		// 获取某月最小天数
		int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		calendar.set(Calendar.DAY_OF_MONTH, firstDay);
		// 格式化日期
		String firstDayOfMonth = DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd");
		dates[0] = firstDayOfMonth;
		// 最后一天
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
		String lastDayOfMonth = DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd");
		dates[1] = lastDayOfMonth;
		return dates;
	}

}
