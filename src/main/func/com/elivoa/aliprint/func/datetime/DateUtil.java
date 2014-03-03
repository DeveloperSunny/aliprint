package com.elivoa.aliprint.func.datetime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.elivoa.aliprint.common.dal.QueryTimeParameter;

/**
 * DateUtil
 * 
 * 时间相关工具
 * 
 * @author Bo Gao elivoa[AT]gmail.com, [Dec. 15, 2011] at home. <br>
 *         (Define the class and initialize it.)
 * 
 */
public class DateUtil {

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取指定某年某月的天数
	 * 
	 * @param year
	 *            指定年份
	 * @param month
	 *            指定月份
	 * @return 当月的天数
	 */
	public static int getDaysinMonth(int year, int month) {
		@SuppressWarnings("deprecation")
		Date date = new Date(year, month, 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date parseDate(int year, int month) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(String.format("%04d-%02d-01", year, month));
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date parseDate(int year, int month, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(String.format("%04d-%02d-%02d", year, month, day));
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * @param d
	 * @return
	 */
	public static String formatDate(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(d);
	}

	/**
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String s) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.parse(s);
	}

	/**
	 * @param d
	 * @return
	 */
	public static String formatTime(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
		return sdf.format(d);
	}

	/**
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String s) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
		return sdf.parse(s);
	}

	/**
	 * @return
	 */
	public static List<String> getDateCatalogs() {
		return Arrays.asList("Today", "Yesterday", "Saturday", "Friday", "Thursday", "Wednesday", "Tuesday", "Monday",
				"Sunday", "Previous-Week", "Two-Weeks-Ago", "Three-Weeks-Ago", "Previous-Month", "Two-Months-Ago",
				"Three-Months-Ago");
	}

	/**
	 * @param d
	 * @return
	 */
	public static int getMonth(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * @param d
	 * @return
	 */
	public static int getYear(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * @param d
	 * @return
	 */
	public static int getDay(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param days
	 * @return
	 */
	public static Date afterDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, QueryTimeParameter> generateDateCatalogs() throws ParseException {
		Map<String, QueryTimeParameter> results = new HashMap<String, QueryTimeParameter>();

		Calendar cal = Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(7);

		Calendar calTo = (Calendar) cal.clone();
		results.put("Today", getTimeParameter(calTo.getTime()));

		calTo.add(Calendar.DAY_OF_MONTH, -1);
		results.put("Yesterday", getTimeParameter(calTo.getTime()));

		if (cal.get(Calendar.DAY_OF_WEEK) > 2) {
			while (calTo.get(Calendar.DAY_OF_WEEK) > 1) {
				calTo.add(Calendar.DAY_OF_WEEK, -1);
				results.put(getWeekDay(calTo), getTimeParameter(calTo.getTime()));
			}
		}

		if (cal.get(Calendar.WEEK_OF_MONTH) > 1) {
			calTo = (Calendar) cal.clone();
			int index = 0;
			while (calTo.get(Calendar.WEEK_OF_MONTH) > 0) {
				calTo.add(Calendar.WEEK_OF_MONTH, -1);
				calTo.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
				Date startDate = calTo.getTime();
				calTo.set(Calendar.DAY_OF_WEEK, calTo.getActualMaximum(Calendar.DAY_OF_WEEK));
				Date endDate = calTo.getTime();
				results.put(getWeekOfMonth(index), getTimeParameter(startDate, endDate));
				index += 1;
			}
			calTo.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		}

		int index = 0;
		while (index < 2) {
			calTo.add(Calendar.DAY_OF_MONTH, -1);
			Date endDate = calTo.getTime();
			calTo.set(Calendar.DAY_OF_MONTH, calTo.getActualMinimum(Calendar.DAY_OF_WEEK));
			Date startDate = calTo.getTime();
			results.put(getMonthOfYear(index), getTimeParameter(startDate, endDate));
			index += 1;
		}

		calTo.add(Calendar.DAY_OF_MONTH, -1);
		Date endDate = calTo.getTime();
		SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
		String d = "1970-1-1";

		results.put(getMonthOfYear(index), getTimeParameter(input.parse(d), endDate));

		return results;
	}

	private static QueryTimeParameter getTimeParameter(Date d) throws ParseException {
		SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		String startTimeStr = String.format("%s 00:00:00", input.format(cal.getTime()));
		String endTimeStr = String.format("%s 23:59:59", input.format(cal.getTime()));

		Date startTime = output.parse(startTimeStr);
		Date endTime = output.parse(endTimeStr);

		return new QueryTimeParameter(startTime, endTime);
	}

	private static QueryTimeParameter getTimeParameter(Date startDate, Date endDate) throws ParseException {
		SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		String startTimeStr = String.format("%s 00:00:00", input.format(cal.getTime()));
		cal.setTime(endDate);
		String endTimeStr = String.format("%s 23:59:59", input.format(cal.getTime()));

		Date startTime = output.parse(startTimeStr);
		Date endTime = output.parse(endTimeStr);

		return new QueryTimeParameter(startTime, endTime);
	}

	private static String getWeekDay(Calendar cal) {
		List<String> weekDays = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
				"Saturday");
		return weekDays.get(cal.get(Calendar.DAY_OF_WEEK) - 1);
	}

	private static String getWeekOfMonth(int index) {
		List<String> weekOfMonth = Arrays.asList("Previous-Week", "Two-Weeks-Ago", "Three-Weeks-Ago",
				"Fourth-Weeks-Ago");
		return weekOfMonth.get(index);
	}

	private static String getMonthOfYear(int index) {
		List<String> monthOfYear = Arrays.asList("Previous-Month", "Two-Months-Ago", "Three-Months-Ago");
		return monthOfYear.get(index);
	}

	public static void main(String[] args) {
		try {
			Map<String, QueryTimeParameter> catalogs = DateUtil.generateDateCatalogs();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

			for (String catalog : getDateCatalogs()) {
				if (catalogs.containsKey(catalog)) {
					QueryTimeParameter timeParam = catalogs.get(catalog);
					Date s = timeParam.getStartTime();
					Date e = timeParam.getEndTime();

					StringBuilder sb = new StringBuilder();
					sb.append("From: ");
					sb.append(sdf.format(s));
					sb.append(" To: ");
					sb.append(sdf.format(e));

					System.out.println("##### DateUtil.main ===> catalog: " + catalog + " | " + sb.toString());
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// // Dates.java
	public static SimpleDateFormat ssdf = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat standardDateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);

	public static SimpleDateFormat date_formater = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

	public static SimpleDateFormat time_formater = new SimpleDateFormat("hh_mm_ss", Locale.CHINA);

	public static String now_date() {
		return date_formater.format(new Date());
	}

	public static String now_time() {
		return time_formater.format(new Date());
	}

	public static String now_time(String format) {
		SimpleDateFormat time_formater = new SimpleDateFormat(format, Locale.CHINA);
		return time_formater.format(new Date());
	}

	public static String now() {
		return standardDateFormater.format(new Date());
	}

	public static String format(Date date) {
		return ssdf.format(date);
	}

	public static String format(Date date, SimpleDateFormat formater) {
		return formater.format(date);
	}

	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Date safeParseDate(String strdate, SimpleDateFormat sdf) {
		try {
			return sdf.parse(strdate);
		} catch (Exception e) {
			// System.err.println("DatesException:" + e.getLocalizedMessage());
		}
		return null;
	}

	public static java.sql.Date convertToSqlDate(Date date) {
		return java.sql.Date.valueOf(ssdf.format(date));
	}

	public static int thisyear() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return year;
	}

}
