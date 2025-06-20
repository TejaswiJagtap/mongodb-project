/**
 * 
 */
package com.artcode.thirtyfifty.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {

	private static final String DATE_FORMAT = "dd-MM-yyyy";

	public Date addMinToTime(Date date, int min) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, min);
		date = cal.getTime();
		return date;
	}

	public Date addDaysToDate(Date dates, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, days);
		Date date = c.getTime();
		return date;
	}

	public String timeInString(Date date) {
		SimpleDateFormat dateFor = new SimpleDateFormat("HH:mm");
		return dateFor.format(date);
	}

	public String dateInString(Date date) {
		SimpleDateFormat dateFor = new SimpleDateFormat(DATE_FORMAT);
		return dateFor.format(date);
	}

	public int getYearFromDate(LocalDate date) {
		int result = -1;
		if (date != null) {
			result = date.getYear();
		}
		return result;
	}

	public HashMap<String, Date> getMonthStartAndEndDate(Date date) {
		Date begining;
		Date end;
		begining = getMonthStartDate(date);
		end = getMonthEndDate(date);
		HashMap<String, Date> map = new HashMap<String, Date>();
		map.put("startDate", begining);
		map.put("endDate", end);
		return map;
	}

	public Date getMonthStartDate(Date date) {
		Date begining;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		setTimeToBeginningOfDay(calendar);
		begining = calendar.getTime();
		return begining;
	}

	public Date getMonthEndDate(Date date) {
		Date end;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		setTimeToEndofDay(calendar);
		end = calendar.getTime();
		return end;
	}

	public LocalDate getMonthStartDate(LocalDate date) {
		LocalDate startDate = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
		return startDate;
	}

	public LocalDate getMonthEndDate(LocalDate date) {
		YearMonth thisYearMonth = YearMonth.of(date.getYear(), date.getMonthValue());
		LocalDate endDate = thisYearMonth.atEndOfMonth();
		return endDate;
	}

	public LocalDate getWeekStartDate(LocalDate date) {
		LocalDate startDate = date.with(DayOfWeek.MONDAY);
		return startDate;

	}

	public LocalDate getWeekEndDate(LocalDate date) {
		LocalDate endDate = date.with(DayOfWeek.SUNDAY);
		return endDate;

	}

	public void setTimeToBeginningOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	public void setTimeToEndofDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}

	public Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public long getDifferenceInDays(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	public int getMonth(String month) {
		int monthInt = 0;
		switch (month) {
		case "January":
			monthInt = 1;
			break;
		case "February":
			monthInt = 2;
			break;
		case "March":
			monthInt = 3;
			break;
		case "April":
			monthInt = 4;
			break;
		case "May":
			monthInt = 5;
			break;
		case "June":
			monthInt = 6;
			break;
		case "July":
			monthInt = 7;
			break;
		case "August":
			monthInt = 8;
			break;
		case "September":
			monthInt = 9;
			break;
		case "October":
			monthInt = 10;
			break;
		case "November":
			monthInt = 11;
			break;
		case "December":
			monthInt = 12;
			break;

		default:
			break;
		}
		return monthInt;
	}

	public static String convertDate(String pay_dateTime) {

		// String[] date =pay_dateTime.split(" ");
		String startDateString = pay_dateTime;
		DateTimeFormatter dfs;
		LocalDate date1;
		if (startDateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {

			dfs = new DateTimeFormatterBuilder().appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
					.appendOptional(DateTimeFormatter.ofPattern(DATE_FORMAT))
					.appendOptional(DateTimeFormatter.ofPattern("MM-dd-yyyy")).toFormatter();
			// LocalDate d = LocalDate.parse("2014-05-14", dfs); //2014-05-14
			date1 = LocalDate.parse(startDateString, dfs); // 2014-05-14
		} else {
			dfs = new DateTimeFormatterBuilder().appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
					.appendOptional(DateTimeFormatter.ofPattern(DATE_FORMAT))
					.appendOptional(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
					.appendOptional(DateTimeFormatter.ofPattern(DATE_FORMAT)).toFormatter();
			date1 = LocalDate.parse(startDateString, dfs);
		}

		Date date = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

		return dateFormat.format(date);
	}
}
